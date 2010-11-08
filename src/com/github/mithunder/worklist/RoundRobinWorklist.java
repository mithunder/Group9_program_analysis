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

public class RoundRobinWorklist extends Worklist {

	private Analysis analysis;

	@Override
	public EvaluatedStatement run(Analysis ana, CompilationUnit unit){
		Statement root = unit.getRootStatement();
		Evaluation e;
		EvaluatedStatement s;
		this.analysis = ana;
		analysis.startAnalysis(unit);
		e = ana.initEvaluation(root);
		s = new EvaluatedStatement(root, e, iterate(initiate(root.getChildren()), e, root.getStatementType()));
		analysis.finishAnalysis(unit);
		return s;
	}

	private List<EvaluatedStatement> iterate(List<EvaluatedStatement> list, Evaluation e, int statementType) {
		boolean changes = true;
		while(changes) {
			changes = false;
			ListIterator<EvaluatedStatement> iterator = getListIterator(list);
			if(statementType == StatementType.IF) {
				changes = handleIf(list, iterator, e, list.size());
				if(analysis.isForwardAnalysis()) {
					e = findMergedEvaluation(list, StatementType.IF);
				}
			} else {
				while(iterator.hasNext()) {
					EvaluatedStatement es = iterator.next();
					if(es.isKilled()) {
						continue;
					}
					changes = analysis.evaluate(es, e) || changes;
					e = es.getEvaluation();
					if(es.getChildCount() > 0) {
						es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
					}
					iterator.set(es);
				}
				if(statementType == StatementType.DO) {
					e = findMergedEvaluation(list, StatementType.DO);
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

	private boolean handleIf(List<EvaluatedStatement> list, ListIterator<EvaluatedStatement> iterator, Evaluation e, int size) {
		if(analysis.isForwardAnalysis()) {
			return handleIfForward(iterator, e, size);
		} else {
			return handleIfBackward(list, iterator, e, size);
		}
	}

	private boolean handleIfForward(ListIterator<EvaluatedStatement> iterator, Evaluation e, int size) {
		int index = 0;
		boolean changes = false;
		for(index = 0; index < size/2; index++) {
			EvaluatedStatement es = iterator.next();
			if(es.isKilled()) {
				continue;
			}
			changes = analysis.evaluate(es, e) || changes;
			e = es.getEvaluation();
			if(es.getChildCount() > 0) {
				es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
			}
			iterator.set(es);
		}
		for(; index < size; index++) {
			EvaluatedStatement es = iterator.next();
			if(es.isKilled()) {
				continue;
			}
			changes = analysis.evaluate(es, e) || changes;
			if(es.getChildCount() > 0) {
				es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
			}
			iterator.set(es);
		}
		return changes;
	}

	private boolean handleIfBackward(List<EvaluatedStatement> list, ListIterator<EvaluatedStatement> iterator, Evaluation e, int size) {
		int index = 0;
		boolean changes = false;
		for(index = 0; index < size/2; index++) {
			EvaluatedStatement es = iterator.next();
			if(es.isKilled()) {
				continue;
			}
			changes = analysis.evaluate(es, e) || changes;
			if(es.getChildCount() > 0) {
				es.setChildren(iterate(es.getChildren(), e, es.getStatementType()));
			}
			iterator.set(es);
		}
		e = findMergedEvaluation(list, StatementType.IF);
		for(; index < size; index++) {
			EvaluatedStatement es = iterator.next();
			if(es.isKilled()) {
				continue;
			}
			changes = analysis.evaluate(es, e) || changes;
			e = es.getEvaluation();
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

	private ListIterator<EvaluatedStatement> getReversedListIterator(List<EvaluatedStatement> list) {
		if(!analysis.isForwardAnalysis()) {
			return list.listIterator();
		} else {
			return new ReverseListIterator<EvaluatedStatement>(list);
		}
	}

	private Evaluation findMergedEvaluation(List<EvaluatedStatement> list, int statementType) {
		Evaluation toReturn = null;
		if(statementType == StatementType.IF) {
			for(int i = list.size()/2; i < list.size(); i++) {
				EvaluatedStatement es = list.get(i);
				if(es.isKilled()) {
					continue;
				}
				if(es.getChildCount() > 0) {
					toReturn = analysis.merge(toReturn, findMergedEvaluation(es.getChildren(), es.getStatementType()));
				} else {
					toReturn = analysis.merge(toReturn, es.getEvaluation());
				}
			}
		} else {
			ListIterator<EvaluatedStatement> iterator = getReversedListIterator(list);
			while(iterator.hasNext()) {
				EvaluatedStatement es = iterator.next();
				if(es.isKilled()) {
					continue;
				}
				if(es.getChildCount() > 0) {
					toReturn = findMergedEvaluation(es.getChildren(), es.getStatementType());
				} else {
					toReturn = es.getEvaluation();
				}
				break;
			}
		}
		return toReturn;
	}
}
