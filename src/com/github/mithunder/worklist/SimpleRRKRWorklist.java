package com.github.mithunder.worklist;

import static com.github.mithunder.statements.StatementType.ABORT;
import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;
import static com.github.mithunder.statements.StatementType.SKIP;

import java.util.ArrayList;
import java.util.List;

import com.github.mithunder.analysis.Evaluation;
import com.github.mithunder.analysis.KillRepairAnalysis;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;

public class SimpleRRKRWorklist implements KillRepairAnalysisWorklist {

	protected int loopNest = 0;
	protected boolean firstVisit = false;
	protected boolean inGuard = false;
	protected boolean isFwd = false;
	protected KillRepairAnalysis kanalysis;
	protected EvaluatedStatement curST;
	protected EvaluatedStatement curBrC;
	protected EvaluatedStatement curBrG;

	@Override
	public boolean isFirstVisit() {
		return firstVisit;
	}

	@Override
	public boolean isGuard() {
		return inGuard;
	}

	@Override
	public boolean isInsideLoop() {
		return loopNest > 0;
	}

	@Override
	public boolean kill() {
		if(kanalysis == null) {
			throw new IllegalStateException("Analysis not started");
		}
		if(inGuard) {
			throw new IllegalStateException("Cannot kill a single statement inside a branch.");
		}
		curST.killStatement();
		return true;
	}

	@Override
	public boolean killBranch() {
		if(kanalysis == null) {
			throw new IllegalStateException("Analysis not started");
		}
		if(!inGuard) {
			throw new IllegalStateException("Not in the condition of a branch");
		}
		if(kanalysis.canRepair()){
			//FIXME: implement repair iteration.
		}
		curBrG.killStatement();
		curBrC.killStatement();
		return true;
	}

	@Override
	public EvaluatedStatement run(KillRepairAnalysis ana, CompilationUnit unit){
		Statement root = unit.getRootStatement();
		EvaluatedStatement s;
		List<EvaluatedStatement> children;
		kanalysis = ana;
		isFwd = ana.isForwardAnalysis();
		kanalysis.startAnalysis(unit);
		children = initiate(root.getChildren());
		s = new EvaluatedStatement(root, children);
		this.evaluateStatement(s, ana.initEvaluation());
		kanalysis.finishAnalysis(unit);
		return s;
	}


	/**
	 * Wraps all statements in s and their children (via recursion) as EvaluatedStatements.
	 * 
	 * @param s A list of statements to wrap
	 * @return A list of wrapped statements.
	 */
	protected List<EvaluatedStatement> initiate(List<? extends Statement> s) {
		ArrayList<EvaluatedStatement> list = new ArrayList<EvaluatedStatement>();
		if(s == null || s.size() == 0) {
			return list;
		}
		for(Statement a : s) {
			List<EvaluatedStatement> children = initiate(a.getChildren());
			list.add(new EvaluatedStatement(a, children));
		}
		return list;
	}

	protected boolean evaluateStatement(EvaluatedStatement toEval, Evaluation entry){
		final int stype = toEval.getStatementType();
		boolean changed = false;
		if(toEval.isKilled()) {
			return false;
		}
		switch(stype){
		case IF:
			if(isFwd){
				changed = evaluateIfStatementForward(toEval, entry);
			} else {
				changed = evaluateIfStatementBackward(toEval, entry);
			}
			break;
		case DO:
			loopNest++;
			if(isFwd){
				changed = evaluateDoStatementForward(toEval, entry);
			} else {
				changed = evaluateDoStatementBackward(toEval, entry);
			}
			loopNest--;
			break;
		case SKIP:
			/* Does not change the state of the program */
			toEval.setEntryEvaluation(entry);
			toEval.setExitEvaluation(entry);
			break;
		case SCOPE:
			if(isFwd) {
				changed = evaluateScopeStatementForward(toEval, entry);
			} else {
				changed = evaluateScopeStatementBackward(toEval, entry);
			}
			break;
		default:
			changed = evaluateSimpleStatement(toEval, entry);
			if(stype == ABORT){
				toEval.setExitEvaluation(kanalysis.initEvaluation());
			}
			break;
		}
		return changed;
	}

	protected boolean evaluateSimpleStatement(EvaluatedStatement toEval, Evaluation entry){
		boolean changed;
		if(toEval.getEntryEvaluation() == null) {
			firstVisit = true;
		}
		curST = toEval;
		changed = kanalysis.evaluate(toEval, entry, this);
		toEval.setEntryEvaluation(entry);
		return changed;
	}

	/**
	 * Evaluate the guards until they stabilise
	 * @param parent The statement containing the guards.
	 * @param entry The entry evaluation for the guards.
	 * @return true if at least one of the evaluation of one of the guards where changed.
	 */
	protected boolean evaluateGuards(EvaluatedStatement parent, Evaluation entry){
		boolean changed = false;
		boolean gchanged;
		List<EvaluatedStatement> children = parent.getChildren();
		final int size = children.size();
		final int hsize = size / 2;
		Evaluation e = entry;
		inGuard = true;
		/* Iterate over all guards until they stabilise */
		do {
			gchanged = false;
			/*
			 * Analyse all the conditions/guards - ASSUMPTION: conditions/guards cannot
			 * change the state of the program. Therefore the order they are evaluated
			 * in will not affect the end result.
			 */
			for(int i = 0 ; i < hsize ; i++){
				EvaluatedStatement guard = children.get(i);
				EvaluatedStatement cmd = children.get(i + hsize);
				if(guard.isKilled()) {
					continue;
				}
				curBrG = guard;
				curBrC = cmd;
				if(evaluateStatement(guard, e)) {
					gchanged = true;
					changed = true;
				}
				/* Update entry value */
				guard.setEntryEvaluation(e);

				/* notify the analysis we are done with the guard */
				kanalysis.leavingGuard(guard, this);
				if(!guard.isKilled()) {
					/* Use the exit value if has not been killed */
					e = guard.getExitEvaluation();
				}
			}
		} while(gchanged);
		inGuard = false;
		return changed;
	}

	/*
	 * NOTE: Sets the exit value to an initial value if all the guards are killed
	 * The exit value is unaffected by this.
	 */
	protected boolean evaluateIfStatementForward(EvaluatedStatement ifst, Evaluation entry){
		boolean changed = false;
		List<EvaluatedStatement> children = ifst.getChildren();
		final int size = children.size();
		final int hsize = size / 2;
		Evaluation e;
		Evaluation exitValue = null;
		if(evaluateGuards(ifst, entry)){
			changed = true;
		}
		for(int i = hsize ; i < size ; i++){
			/* Fetch the command and the related guard */
			EvaluatedStatement cmd = children.get(i);
			EvaluatedStatement guard = children.get(i - hsize);
			if(cmd.isKilled() || guard.isKilled()) {
				continue;
			}
			/*
			 * Use the exit value of the guard - else the evaluation
			 * will be tainted by the other commands.
			 */
			e = guard.getExitEvaluation();
			if(evaluateStatement(cmd, e)) {
				changed = true;
			}
			/* Update entry value */
			cmd.setEntryEvaluation(e);
			/* do an incremental merge */
			exitValue = kanalysis.merge(exitValue, cmd.getExitEvaluation());
		}
		/* Update the entry value */
		ifst.setEntryEvaluation(entry);
		if(exitValue != null) {
			ifst.setExitEvaluation(exitValue);
		} else {
			/* All branches are dead == abort */
			ifst.setExitEvaluation(kanalysis.initEvaluation());
		}
		return changed;
	}

	/*
	 * NOTE: Sets the exit value to an initial value if all the guards are killed
	 * The exit value is unaffected by this.
	 */
	protected boolean evaluateIfStatementBackward(EvaluatedStatement ifst, Evaluation entry){
		boolean changed = false;
		List<EvaluatedStatement> children = ifst.getChildren();
		final int size = children.size();
		final int hsize = size / 2;
		Evaluation exitValue = null;
		EvaluatedStatement child;
		for(int i = size - 1 ; i >= hsize ; i--){
			EvaluatedStatement cmd = children.get(i);
			if(cmd.isKilled()) {
				continue;
			}
			if(evaluateStatement(cmd, entry)) {
				changed = true;
			}
			/* Update entry value */
			cmd.setEntryEvaluation(entry);
			/* do an incremental merge */
			exitValue = kanalysis.merge(exitValue, cmd.getExitEvaluation());
		}
		if(evaluateGuards(ifst, exitValue)){
			changed = true;
		}
		/* Update the entry value */
		ifst.setEntryEvaluation(entry);
		child = findFirstNonDeadGuard(ifst);
		if(child != null) {
			/*
			 * ASSUMPTION: conditions/guards all have the same value;
			 * see evaluateGuards
			 */
			ifst.setExitEvaluation(child.getExitEvaluation());
		} else {
			/* All branches are dead == abort */
			ifst.setExitEvaluation(kanalysis.initEvaluation());
		}
		return changed;
	}

	/**
	 * @note Assumes parent is either {@link StatementType#IF} or a {@link StatementType#DO}
	 * @param parent The parent containing guards.
	 * @return The first non-dead guard OR <code>null</code> if all the guards are dead.
	 */
	protected EvaluatedStatement findFirstNonDeadGuard(EvaluatedStatement parent){
		List<EvaluatedStatement> children = parent.getChildren();
		final int hsize = children.size() / 2;
		for(int i = 0 ; i < hsize ; i++){
			EvaluatedStatement child = children.get(i);
			if(!child.isKilled()) {
				return child;
			}
		}
		/* They are all dead */
		return null;
	}

	/*
	 * NOTE: Sets the exit value to the entry value if all the guards are killed
	 * The exit value is unaffected by this.
	 */
	protected boolean evaluateDoStatementForward(EvaluatedStatement dost, Evaluation entry){
		boolean changed = false;
		boolean hasChanged;
		List<EvaluatedStatement> children = dost.getChildren();
		final int size = children.size();
		final int hsize = size / 2;
		Evaluation e = entry;
		Evaluation exitValue;
		EvaluatedStatement fchild = findFirstNonDeadGuard(dost);
		if(fchild == null) {
			/* Short cut exit. */
			dost.setExitEvaluation(entry);
			return false;
		}
		do {
			hasChanged = false;
			exitValue = entry;
			if(evaluateGuards(dost, e)){
				hasChanged = true;
				changed = true;
			}
			for(int i = hsize ; i < size ; i++){
				/* Fetch the command and the related guard */
				EvaluatedStatement cmd = children.get(i);
				EvaluatedStatement guard = children.get(i - hsize);
				if(cmd.isKilled() || guard.isKilled()) {
					continue;
				}
				/*
				 * Use the exit value of the guard - else the evaluation
				 * will be tainted by the other commands.
				 */
				e = guard.getExitEvaluation();
				if(evaluateStatement(cmd, e)) {
					changed = true;
					hasChanged = true;
				}
				/* Update entry value */
				cmd.setEntryEvaluation(e);
				/* do an incremental merge */
				exitValue = kanalysis.merge(exitValue, cmd.getExitEvaluation());
			}
			/* Update the current evaluation */
			e = exitValue;
		} while(hasChanged);
		/* Update the entry value */
		dost.setEntryEvaluation(entry);
		if(fchild.isKilled()) {
			/* The analysis managed to kill the cached guard */
			fchild = findFirstNonDeadGuard(dost);
			if(fchild == null) {
				/* In fact it killed the last guard => dost == SKIP */
				exitValue = null;
			}
		}
		if(exitValue != null) {
			dost.setExitEvaluation(exitValue);
		} else {
			/*
			 * All branches are dead == skip => the DO will not change the state of the program
			 * and thus the analysis will not be affected by it.
			 */
			dost.setExitEvaluation(entry);
		}
		return changed;
	}


	/*
	 * NOTE: Sets the exit value to the entry value if all the guards are killed
	 * The exit value is unaffected by this.
	 */
	protected boolean evaluateDoStatementBackward(EvaluatedStatement dost, Evaluation entry){
		boolean changed = false;
		boolean hasChanged;
		List<EvaluatedStatement> children = dost.getChildren();
		final int size = children.size();
		final int hsize = size / 2;
		Evaluation e = entry;
		Evaluation exitValue = entry;
		EvaluatedStatement fchild = findFirstNonDeadGuard(dost);
		if(fchild == null) {
			/* Short cut exit. */
			dost.setExitEvaluation(entry);
			return false;
		}
		do {
			hasChanged = false;
			for(int i = hsize ; i < size ; i++){
				/* Fetch the command and the related guard */
				EvaluatedStatement cmd = children.get(i);
				EvaluatedStatement guard = children.get(i - hsize);
				if(cmd.isKilled() || guard.isKilled()) {
					continue;
				}

				if(evaluateStatement(cmd, e)) {
					changed = true;
					hasChanged = true;
				}
				/* Update entry value */
				cmd.setEntryEvaluation(e);
				/* do an incremental merge */
				exitValue = kanalysis.merge(exitValue, cmd.getExitEvaluation());
			}
			if(evaluateGuards(dost, exitValue)) {
				hasChanged = true;
				changed = true;
			}
			if(fchild.isKilled()){
				/* The analysis has been able to kill the cached guard */
				fchild = findFirstNonDeadGuard(dost);
				if(fchild == null){
					/* All branches are now dead */
					exitValue = null;
					break;
				}
			}

			/* ASSUMPTION: all guards have the same exit evaluation */
			exitValue = kanalysis.merge(exitValue, fchild.getExitEvaluation());
			/*
			 * Be sure to include the entry value - this is trivially satisfied
			 * in the first iteration earlier, but not necessarily in following
			 * iterations.
			 */
			exitValue = kanalysis.merge(exitValue, entry);
			e = exitValue;
		} while(hasChanged);
		/* Update the entry value */
		dost.setEntryEvaluation(entry);
		if(exitValue != null) {
			dost.setExitEvaluation(exitValue);
		} else {
			/*
			 * All branches are dead == skip => the DO will not change the state of the program
			 * and thus the analysis will not be affected by it.
			 */
			dost.setExitEvaluation(entry);
		}
		return changed;
	}

	protected boolean evaluateScopeStatementForward(EvaluatedStatement scope, Evaluation entry){
		boolean changed = false;
		List<EvaluatedStatement> children = scope.getChildren();
		final int size = children.size();
		int dead = 0;
		Evaluation e = entry;
		for(int i = 0 ; i < size ; i++){
			EvaluatedStatement st = children.get(i);
			if(st.isKilled()) {
				dead++;
				continue;
			}
			if(evaluateStatement(st, e)){
				changed = true;
			}
			st.setEntryEvaluation(e);
			e = st.getExitEvaluation();
		}
		if(dead == size) {
			scope.killStatement();
		} else {
			scope.setEntryEvaluation(entry);
			scope.setExitEvaluation(e);
		}
		return changed;
	}

	protected boolean evaluateScopeStatementBackward(EvaluatedStatement scope, Evaluation entry){
		boolean changed = false;
		List<EvaluatedStatement> children = scope.getChildren();
		final int size = children.size();
		int dead = 0;
		Evaluation e = entry;
		for(int i = size - 1 ; i >= 0 ; i--){
			EvaluatedStatement st = children.get(i);
			if(st.isKilled()) {
				dead++;
				continue;
			}
			if(evaluateStatement(st, e)){
				changed = true;
			}
			st.setEntryEvaluation(e);
			e = st.getExitEvaluation();
		}
		if(dead == size) {
			scope.killStatement();
		} else {
			scope.setEntryEvaluation(entry);
			scope.setExitEvaluation(e);
		}
		return changed;
	}

}
