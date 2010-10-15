package com.github.mithunder.worklist;

import java.util.ArrayList;
import java.util.List;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;

public class RoundRobinWorklist extends Worklist {

	List<Object> objs = new ArrayList<Object>();

	@Override
	public void addToList(Statement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public Statement getNextStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EvaluatedStatement> iterate(Analysis analysis, List<EvaluatedStatement> es) {
		boolean changes = true;
		while(changes) {
			changes = false;

		}
		return es;
		/*boolean changes = true;
		while(changes) {
			changes = false;
			for(int i = 0; i < s.size(); i++) {
				Statement statement = s.get(i);
				List<? extends Statement> list = statement.getChildren();
				if(list.size() != 0) {
					iterate(analysis, list);
				}
				Object o = analysis.evaluate(statement,objs);
				Object inList;
				try {
					inList = objs.get(i);
				} catch (ArrayIndexOutOfBoundsException e) {
					objs.add(analysis.init(new Object()));
					inList = objs.get(i);
				}
				if(analysis.compare(o,objs.get(i)) != 0) {
					changes = true;
					objs.set(i, analysis.merge(inList,o));
				}
			}
		}
		return objs;*/
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
