package com.github.mithunder.worklist;

import java.util.ArrayList;
import java.util.List;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.statements.AbstractStatement;

public class RoundRobinWorklist extends Worklist {

	List<Object> objs = new ArrayList<Object>();

	@Override
	public void addToList(AbstractStatement s) {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractStatement getNextStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> iterate(Analysis analysis, List<? extends AbstractStatement> s) {
		boolean changes = true;
		while(changes) {
			changes = false;
			for(int i = 0; i < s.size(); i++) {
				AbstractStatement statement = s.get(i);
				List<? extends AbstractStatement> list = statement.getChildren();
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
		return objs;
	}
}
