package com.github.mithunder.worklist;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.analysis.Evaluation;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;

public class RoundRobinWorklist extends Worklist {

	//TODO: Handle dead statements
	//TODO: Perhaps remove handleDo?
	//TODO: Iterate through if and do statements to find which evaluations should be merged for further use
	//TODO: In handleIf, if a reverse iterator is used, the conditions is first ....

	private Analysis analysis;

	@Override
	public List<EvaluatedStatement> run(Analysis analysis, List<? extends Statement> ss, int statementType) {
		this.analysis = analysis;
		return iterate(initiate(ss), null, statementType);
	}

	private List<EvaluatedStatement> iterate(List<EvaluatedStatement> list, Evaluation e, int statementType) {
		boolean changes = true;
		while(changes) {
			changes = false;
			ListIterator<EvaluatedStatement> iterator = getListIterator(list);
			if(statementType == StatementType.DO) {
				changes = handleDo(iterator, e);
			} else if(statementType == StatementType.IF) {
				changes = handleIf(iterator, e, list.size());
			} else {
				while(iterator.hasNext()) {
					EvaluatedStatement s = iterator.next();
					changes = analysis.evaluate(s, e) || changes;
					e = s.getEvaluation();
					if(s.getChildCount() > 0) {
						s.setChildren(iterate(s.getChildren(), e, s.getStatementType()));
					}
					iterator.set(s);
				}
			}
		}
		return list;
	}

	private List<EvaluatedStatement> initiate(List<? extends Statement> s) {
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

	//FIXME: Unless how do should be handled is changed, this could be deleted (same procedure as simple statements)
	private boolean handleDo(ListIterator<EvaluatedStatement> iterator, Evaluation e) {
		boolean changes = false;
		while(iterator.hasNext()) {
			EvaluatedStatement es = iterator.next();
			changes = analysis.evaluate(es, e) || changes;
			e = es.getEvaluation();
			if(es.getChildCount() > 0) {
				es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
			}
			iterator.set(es);
		}
		return changes;
	}

	private boolean handleIf(ListIterator<EvaluatedStatement> iterator, Evaluation e, int size) {
		int index = 0;
		boolean changes = false;
		for(index = 0; index < size/2; index++) {
			EvaluatedStatement es = iterator.next();
			changes = analysis.evaluate(es, e) || changes;
			e = es.getEvaluation();
			if(es.getChildCount() > 0) {
				es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
			}
			iterator.set(es);
		}
		for(; index < size; index++) {
			EvaluatedStatement es = iterator.next();
			changes = analysis.evaluate(es, e) || changes;
			if(es.getChildCount() > 0) {
				es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
			}
			iterator.set(es);
		}
		return changes;
	}

	private ListIterator<EvaluatedStatement> getListIterator(List<EvaluatedStatement> list) {
		if(analysis.isForwardAnalysis()) {
			return list.listIterator();
		} else {
			return new ReverseListIterator<EvaluatedStatement>(list);
		}
	}

	private Evaluation findMergedEvaluation(List<EvaluatedStatement> list, int statementType) {
		Evaluation toReturn = null;
		if(statementType == StatementType.DO) {
			for(int i = 1; i < list.size(); i = i + 2) {
				EvaluatedStatement es = list.get(i);
				if(es.getChildCount() > 0) {
					toReturn = analysis.merge(toReturn, findMergedEvaluation(es.getChildren(), es.getStatementType()));
				} else {
					toReturn = analysis.merge(toReturn, es.getEvaluation());
				}
			}
		} else if(statementType == StatementType.IF) {
			for(int i = list.size()/2; i < list.size(); i++) {
				EvaluatedStatement es = list.get(i);
				if(es.getChildCount() > 0) {
					toReturn = analysis.merge(toReturn, findMergedEvaluation(es.getChildren(), es.getStatementType()));
				} else {
					toReturn = analysis.merge(toReturn, es.getEvaluation());
				}
			}
		} else {
			//TODO: only select the last one?
			for(int i = 0; i < list.size(); i++) {
				EvaluatedStatement es = list.get(i);
				if(es.getChildCount() > 0) {
					toReturn = findMergedEvaluation(es.getChildren(), es.getStatementType());
				} else {
					toReturn = es.getEvaluation();
				}
			}
		}
		return toReturn;
	}
}
