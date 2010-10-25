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
			} else {
				while(iterator.hasNext()) {
					EvaluatedStatement s = iterator.next();
					changes = analysis.evaluate(s, e) || changes;
					e = s.getEvaluation();
					if(s.getChildCount() > 0) {
						iterate(s.getChildren(), e, s.getStatementType());
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

	private boolean handleDo(ListIterator<EvaluatedStatement> iterator, Evaluation e) {
		boolean changes = false;
		while(iterator.hasNext()) {
			EvaluatedStatement es = iterator.next();
			changes = analysis.evaluate(es, e) || changes;
			e = es.getEvaluation();
			if(es.getChildCount() > 0) {
				iterate(es.getChildren(), e, es.getStatementType());
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
}
