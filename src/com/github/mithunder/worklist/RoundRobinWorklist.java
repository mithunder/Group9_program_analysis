package com.github.mithunder.worklist;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.analysis.Evaluation;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;

public class RoundRobinWorklist implements Worklist {

	protected Analysis analysis;
	protected boolean forward;

	@Override
	public EvaluatedStatement run(Analysis ana, CompilationUnit unit){
		Statement root = unit.getRootStatement();
		Evaluation e;
		EvaluatedStatement s;
		List<EvaluatedStatement> children;
		this.analysis = ana;
		analysis.startAnalysis(unit);
		forward = analysis.isForwardAnalysis();
		e = ana.initEvaluation(root);
		children = initiate(root.getChildren());
		s = new EvaluatedStatement(root, e, children);
		iterate(s, e, root.getStatementType());
		analysis.finishAnalysis(unit);
		return s;
	}

	private boolean iterate(EvaluatedStatement parent, Evaluation org, int statementType) {
		boolean changes = true;
		Evaluation e = org;
		List<EvaluatedStatement> list = parent.getChildren();
		while(changes) {
			changes = false;
			ListIterator<EvaluatedStatement> iterator = getListIterator(list);
			if(statementType == StatementType.IF) {
				e = org;
				changes = handleIf(list, iterator, e, list.size());
				if(analysis.isForwardAnalysis()) {
					e = findMergedEvaluation(list, statementType);
				} else {
					EvaluatedStatement child = getFirstChild(list);
					e = child.getEvaluation();
				}
				break;
			} else {
				if(statementType != StatementType.DO) {
					e = org;
				}
				while(iterator.hasNext()) {
					EvaluatedStatement es = iterator.next();
					if(es.isKilled()) {
						continue;
					}
					if(statementType == StatementType.DO) {
						int currentIndex = iterator.nextIndex();
						if(analysis.isForwardAnalysis()) {
							currentIndex--;
						} else {
							currentIndex++;
						}
						if(currentIndex < (list.size()/2)) {
							if(es.getChildCount() > 0) {
								changes = iterate(es, e, es.getStatementType());
							}
							changes = analysis.evaluateCondition(es, list.get(currentIndex + list.size()/2), e) || changes;
						} else {
							changes = analysis.evaluate(es, e) || changes;
						}
					} else {
						if(es.getChildCount() > 0) {
							changes = iterate(es, e, es.getStatementType()) || changes;
						} else {
							changes = analysis.evaluate(es, e) || changes;
						}
					}
					e = es.getEvaluation();
				}
				if(statementType == StatementType.DO) {
					e = analysis.merge(org, findMergedEvaluation(list, StatementType.DO));
				} else {
					break;
				}
			}
		}
		parent.setEvaluation(e);
		return changes;
	}

	protected List<EvaluatedStatement> initiate(List<? extends Statement> s) {
		ArrayList<EvaluatedStatement> list = new ArrayList<EvaluatedStatement>();
		if(s == null || s.size() == 0) {
			return list;
		}
		for(Statement a : s) {
			List<EvaluatedStatement> children = initiate(a.getChildren());
			list.add(new EvaluatedStatement(a, analysis.initEvaluation(a), children));
		}
		return list;
	}

	protected boolean handleIf(List<EvaluatedStatement> list, ListIterator<EvaluatedStatement> iterator, Evaluation e, int size) {
		if(analysis.isForwardAnalysis()) {
			return handleIfForward(list, e, size);
		} else {
			return handleIfBackward(list, iterator, e, size);
		}
	}


	protected boolean handleIfForward(List<EvaluatedStatement> list, Evaluation e, int size) {
		int index = 0;
		boolean changes = false;
		final int noConds = size/2;
		for(index = 0; index < noConds; index++) {
			EvaluatedStatement es = list.get(index);
			if(es.isKilled()) {
				continue;
			}
			if(es.getChildCount() > 0) {
				changes = iterate(es, e, es.getStatementType()) || changes;
			} else {
				changes = evaluate(es, e) || changes;
			}
			e = es.getEvaluation();
		}
		for(; index < size; index++) {
			EvaluatedStatement es = list.get(index);
			if(es.isKilled()) {
				continue;
			}
			if(es.getChildCount() > 0) {
				changes = iterate(es, e, es.getStatementType()) || changes;
			} else {
				changes = evaluateCondition(list.get(index - noConds), es, e) || changes;
			}
		}
		return changes;
	}

	protected boolean handleIfBackward(List<EvaluatedStatement> list, ListIterator<EvaluatedStatement> iterator, Evaluation e, int size) {
		int index = 0;
		boolean changes = false;
		final int noConds = size/2;
		for(index = 0; index < noConds; index++) {
			EvaluatedStatement es = iterator.next();
			if(es.isKilled()) {
				continue;
			}
			if(es.getChildCount() > 0) {
				changes = iterate(es, e, es.getStatementType()) || changes;
			} else {
				changes = evaluateCondition(list.get(noConds - index), es, e) || changes;
			}
		}
		e = findMergedEvaluation(list, StatementType.IF);
		for(; index < size; index++) {
			EvaluatedStatement es = iterator.next();
			if(es.isKilled()) {
				continue;
			}
			if(es.getChildCount() > 0) {
				changes = iterate(es, e, es.getStatementType()) || changes;
			} else {
				changes = evaluate(es, e) || changes;
			}
			e = es.getEvaluation();
		}
		return changes;
	}

	protected ListIterator<EvaluatedStatement> getListIterator(List<EvaluatedStatement> list) {
		if(analysis.isForwardAnalysis()) {
			return list.listIterator();
		} else {
			return new ReverseListIterator<EvaluatedStatement>(list);
		}
	}

	protected ListIterator<EvaluatedStatement> getReversedListIterator(List<EvaluatedStatement> list) {
		if(!analysis.isForwardAnalysis()) {
			return list.listIterator();
		} else {
			return new ReverseListIterator<EvaluatedStatement>(list);
		}
	}

	protected Evaluation merge(EvaluatedStatement es, Evaluation e){
		Evaluation toReturn;
		if(es.getChildCount() > 0) {
			List<EvaluatedStatement> children = es.getChildren();
			EvaluatedStatement c = getFirstChild(children);
			toReturn = analysis.merge(e, c.getEvaluation());
		} else {
			toReturn = analysis.merge(e, es.getEvaluation());
		}
		return toReturn;
	}

	protected Evaluation findMergedEvaluation(List<EvaluatedStatement> list, int statementType) {
		Evaluation toReturn = null;
		if(statementType == StatementType.IF) {
			for(int i = list.size()/2; i < list.size(); i++) {
				EvaluatedStatement es = list.get(i);
				if(es.isKilled()) {
					continue;
				}
				toReturn = merge(es, toReturn);
			}
		} else {
			EvaluatedStatement es = getFirstChild(list);
			toReturn = merge(es, toReturn);
		}
		return toReturn;
	}

	protected EvaluatedStatement getFirstChild(List<EvaluatedStatement> list) {
		ListIterator<EvaluatedStatement> iterator = getReversedListIterator(list);
		while(iterator.hasNext()) {
			EvaluatedStatement es = iterator.next();
			if(!es.isKilled()) {
				return es;
			}
		}
		throw new AssertionError();
	}

	protected boolean evaluate(EvaluatedStatement s, Evaluation prev){
		return analysis.evaluate(s, prev);
	}

	protected boolean evaluateCondition(EvaluatedStatement g, EvaluatedStatement s, Evaluation prev){
		return analysis.evaluateCondition(g, s, prev);
	}

}
