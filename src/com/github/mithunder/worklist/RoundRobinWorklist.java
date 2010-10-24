package com.github.mithunder.worklist;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.analysis.Evaluation;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;

public class RoundRobinWorklist extends Worklist {

	@Override
	public List<EvaluatedStatement> run(Analysis analysis, List<? extends Statement> ss) {
		List<EvaluatedStatement> list = initiate(analysis, ss);
		return iterate(analysis, list, true);
	}

	private List<EvaluatedStatement> iterate(Analysis analysis, List<EvaluatedStatement> es, boolean firstEvaluation) {
		boolean changes = true;
		while(changes) {
			changes = false;
			ListIterator<EvaluatedStatement> iterator;
			if(analysis.isForwardAnalysis()) {
				iterator = es.listIterator();
			} else {
				iterator = new ReverseListIterator<EvaluatedStatement>(es);
			}
			Evaluation e = null;
			while(iterator.hasNext()) {
				EvaluatedStatement s = iterator.next();
				if(firstEvaluation) {
					e = analysis.initEvaluation(s.getStatement());
					changes = true;
				} else {
					if(analysis.evaluate(s, e)) {
						changes = true;
					}
					e = s.getEvaluation();
				}
				firstEvaluation = false;
				iterator.set(s);
			}
		}
		return es;
	}

	private List<EvaluatedStatement> initiate(Analysis analysis, List<? extends Statement> s) {
		ArrayList<EvaluatedStatement> list = new ArrayList<EvaluatedStatement>();
		if(s == null || s.size() == 0) {
			return list;
		}
		for(Statement a : s) {
			List<EvaluatedStatement> children = initiate(analysis, a.getChildren());
			list.add(new EvaluatedStatement(a, analysis.initEvaluation(a), children));
		}
		return list;
	}
}
