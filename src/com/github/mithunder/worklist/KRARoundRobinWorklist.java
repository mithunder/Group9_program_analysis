package com.github.mithunder.worklist;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.analysis.Evaluation;
import com.github.mithunder.analysis.KillRepairAnalysis;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;

public class KRARoundRobinWorklist extends RoundRobinWorklist implements KillRepairAnalysisWorklist {

	protected KillRepairAnalysis kanalysis;

	protected int loopNest = 0;
	protected boolean firstVisit = false;
	protected boolean inGuard = false;
	protected EvaluatedStatement curST;
	protected EvaluatedStatement curBrG;
	protected EvaluatedStatement curBrC;

	@Override
	public EvaluatedStatement run(Analysis ana, CompilationUnit unit){
		Statement root = unit.getRootStatement();
		Evaluation e;
		EvaluatedStatement s;
		List<EvaluatedStatement> children;
		this.analysis = ana;
		forward = analysis.isForwardAnalysis();
		if(ana instanceof KillRepairAnalysis) {
			kanalysis = (KillRepairAnalysis)ana;
		}
		analysis.startAnalysis(unit);
		e = ana.initEvaluation(root);
		children = initiate(root.getChildren());
		s = new EvaluatedStatement(root, e, children);
		analyse(s, e);
		analysis.finishAnalysis(unit);
		return s;
	}

	@Override
	protected boolean evaluate(EvaluatedStatement s, Evaluation prev){
		boolean changed;
		curST = s;
		firstVisit = s.getEvaluation() == null;
		if(kanalysis != null) {
			changed = kanalysis.evaluate(s, prev, this);
		} else {
			changed = analysis.evaluate(s, prev);
		}
		return changed;
	}

	@Override
	protected boolean evaluateCondition(EvaluatedStatement g, EvaluatedStatement s, Evaluation prev){
		boolean changed;
		curST = s;
		firstVisit = s.getEvaluation() == null;
		if(kanalysis != null) {
			changed = kanalysis.evaluateCondition(g, s, prev, this);
		} else {
			changed = analysis.evaluateCondition(g, s, prev);
		}
		return changed;
	}


	@Override
	protected List<EvaluatedStatement> initiate(List<? extends Statement> s) {
		ArrayList<EvaluatedStatement> list = new ArrayList<EvaluatedStatement>();
		if(s == null || s.size() == 0) {
			return list;
		}
		for(Statement a : s) {
			List<EvaluatedStatement> children = initiate(a.getChildren());
			list.add(new EvaluatedStatement(a, null, children));
		}
		return list;
	}

	private boolean analyse(EvaluatedStatement parent, Evaluation prev) {
		boolean changes = true;
		Evaluation e = prev;
		List<EvaluatedStatement> list = parent.getChildren();
		int statementType = parent.getStatementType();
		if(statementType == StatementType.DO) {
			loopNest++;
		}
		while(changes) {
			changes = false;
			ListIterator<EvaluatedStatement> iterator = getListIterator(list);
			if(statementType == StatementType.IF) {
				e = prev;
				changes = handleIf(list, iterator, e, list.size());
				if(analysis.isForwardAnalysis()) {
					e = findMergedEvaluation(list, StatementType.IF);
				} else {
					EvaluatedStatement child = getFirstChild(list);
					e = child.getEvaluation();
				}
				break;
			} else {
				final int cch = list.size()/2;
				int i = 0;
				if(statementType != StatementType.DO) {
					e = prev;
				}
				while(iterator.hasNext()) {
					//FIXME: i is inverted for backwards.
					EvaluatedStatement es = iterator.next();
					if(es.isKilled()) {
						i++;
						continue;
					}
					if(statementType == StatementType.DO) {
						inGuard = i < cch;
						if(inGuard) {
							curBrG = es;
							curBrC = list.get(i + cch - 1);
						} else {
							curBrC = null;
							changes = evaluateCondition(list.get(i - cch), es, prev);
						}
					}
					if(es.getChildCount() > 0) {
						changes = analyse(es, e) || changes;
					} else {
						changes = evaluate(es, e) || changes;
					}
					e = es.getEvaluation();
					if(statementType == StatementType.DO) {
						i++;
						if(kanalysis != null && i < cch) {
							kanalysis.leavingGuard(es, this);
						}
					}
				}
				if(statementType == StatementType.DO) {
					e = analysis.merge(prev, findMergedEvaluation(list, StatementType.DO));
				} else {
					break;
				}
			}
		}
		if(statementType == StatementType.DO) {
			loopNest--;
		}
		parent.setEvaluation(e);
		return changes;
	}

	@Override
	protected boolean handleIfForward(List<EvaluatedStatement> list, Evaluation e, final int size) {
		int index = 0;
		final int cch = size / 2;
		boolean changes = false;
		inGuard = true;
		for(index = 0; index < cch; index++) {
			EvaluatedStatement es = list.get(index);
			if(es.isKilled()) {
				continue;
			}
			curBrC = list.get(index + cch);
			curBrG = es;
			if(es.getChildCount() > 0) {
				changes = analyse(es, e) || changes;
			} else {
				changes = evaluate(es, e) || changes;
			}
			e = es.getEvaluation();
			if(kanalysis != null) {
				kanalysis.leavingGuard(es, this);
			}
		}
		inGuard = false;
		curBrC = null;
		curBrG = null;
		for(; index < size; index++) {
			EvaluatedStatement es = list.get(index);
			if(es.isKilled()) {
				continue;
			}
			if(es.getChildCount() > 0) {
				changes = analyse(es, e) || changes;
			} else {
				changes = evaluateCondition(list.get(index - cch - 1), es, e) || changes;
			}
		}
		return changes;
	}

	@Override
	protected boolean handleIfBackward(List<EvaluatedStatement> list, ListIterator<EvaluatedStatement> iterator, Evaluation e, final int size) {
		final int cch = size / 2;
		int index = 0;
		boolean changes = false;
		for(index = 0; index < cch; index++) {
			EvaluatedStatement es = list.get(size - index - 1);
			if(es.isKilled()) {
				continue;
			}
			if(es.getChildCount() > 0) {
				changes = analyse(es, e) || changes;
			} else {
				changes = evaluateCondition(list.get(index - cch - 1), es, e) || changes;
			}
		}
		e = findMergedEvaluation(list, StatementType.IF);
		inGuard = true;
		for(; index < size; index++) {
			EvaluatedStatement es = list.get(size - index - 1);
			if(es.isKilled()) {
				continue;
			}
			curBrG = es;
			curBrC = list.get(index);
			if(es.getChildCount() > 0) {
				changes = analyse(es, e) || changes;
			} else {
				changes = evaluate(es, e) || changes;
			}
			e = es.getEvaluation();
			if(kanalysis != null) {
				kanalysis.leavingGuard(es, this);
			}
		}
		inGuard = false;
		curBrC = null;
		curBrG = null;
		return changes;
	}

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
	public void kill() {
		if(inGuard) {
			throw new IllegalStateException("Cannot kill a single statement inside a branch.");
		}
		curST.killStatement();
	}

	@Override
	public void killBranch() {
		if(!inGuard) {
			throw new IllegalStateException("Not in the condition of a branch");
		}
		if(kanalysis.canRepair()){
			//FIXME: implement repair iteration.
		}
		System.err.println("Killing branch: " + Integer.toHexString(System.identityHashCode(curBrC.getStatement())));
		curBrG.killStatement();
		curBrC.killStatement();
	}


}
