package com.github.mithunder.analysis;

import java.util.ListIterator;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;
import com.github.mithunder.worklist.ReverseListIterator;

public class ConstantPropagationBranchKiller extends KillRepairAnalysis {

	protected ConstantPropagationAnalysis cp = new ConstantPropagationAnalysis();


	@Override
	public void finishAnalysis(CompilationUnit unit) {
		cp.finishAnalysis(unit);
	}

	@Override
	public Evaluation initEvaluation() {
		return cp.initEvaluation();
	}

	@Override
	public boolean isForwardAnalysis() {
		return cp.isForwardAnalysis();
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		return cp.merge(e1, e2);
	}

	@Override
	public void startAnalysis(CompilationUnit unit) {
		cp.startAnalysis(unit);
	}

	@Override
	public boolean canRepair() {
		return false;
	}

	@Override
	public boolean evaluate(EvaluatedStatement s, Evaluation e, KillRepairAnalysisWorklist w) {
		return cp.evaluate(s, e, w);
	}

	@Override
	public Evaluation repairAnalysis(EvaluatedStatement killed, Evaluation e) {
		throw new UnsupportedOperationException("Cannot repair the analysis.");
	}

	@Override
	public void leavingGuard(EvaluatedStatement guard, KillRepairAnalysisWorklist w) {
		ListIterator<EvaluatedStatement> it;
		EvaluatedStatement last;
		ConstantPropagationAnalysis.CPAEvaluation eval;
		ConstantValue con;
		if(w.isInsideLoop()) {
			return;
		}
		it = new ReverseListIterator<EvaluatedStatement>(guard.getChildren());
		if(!it.hasNext()){
			return;
		}
		last = it.next();
		eval = (ConstantPropagationAnalysis.CPAEvaluation)last.getExitEvaluation();
		con = eval.getConstant(last.getAssign());
		if(con != null) {
			System.err.println("Leaving guard with const value: " + con.getValue());
		}
		System.err.println("Leaving statement: " + last.getStatementType());
		if(con != null && con.getValue() == 0){
			/* Not inside a loop; last statement evaluates to 0 or false - we will never enter the branch */
			w.killBranch();
		}
	}


}
