package com.github.mithunder.chains;

<<<<<<< Updated upstream
import com.github.mithunder.analysis.KillRepairAnalysis;
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

>>>>>>> Stashed changes
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class DefineUseChain extends Chain {

<<<<<<< Updated upstream
	@Override
	public void createChain(Statement root, KillRepairAnalysis ana) {
=======
	//TODO: do and if might be a problem, in map, it might have to be a set/array of IndexWrappers and not just a single element
	//If that is the case, the algorithm have to know when it is inside a do/if (perhaps look at KRA worklist)

	private Map<IndexWrapper, Set<Statement>> duChain;
	private Map<Variable, ArrayList<IndexWrapper>> map;
	private VariableTable table;

	public void createChain(Statement root, VariableTable table) {
		this.table = table;
		duChain = new HashMap<IndexWrapper, Set<Statement>>();
		map = new HashMap<Variable, ArrayList<IndexWrapper>>();
		iterate(root);
		print();
	}

	private void print() {
		Set<Entry<IndexWrapper, Set<Statement>>> set = duChain.entrySet();
		Iterator<Entry<IndexWrapper, Set<Statement>>> iterator = set.iterator();
		while(iterator.hasNext()) {
			Entry<IndexWrapper, Set<Statement>> a = iterator.next();
			IndexWrapper iw = a.getKey();
			Set<Statement> ss = a.getValue();
			System.out.print(iw.getStatement().getCodeLocation().getLineNumber() + ": " + table.getVariableName(iw.getVariable()) + " -> [ ");
			Iterator<Statement> ite = ss.iterator();
			while(ite.hasNext()) {
				Statement b = ite.next();
				System.out.print(b.getCodeLocation().getLineNumber() + " ");
			}
			System.out.println("]");
		}
	}

	private void iterate(Statement root) {
		List<? extends Statement> list = root.getChildren();
		for(int i = 0; i < list.size(); i++) {
			Statement s = list.get(i);
			Variable assign = s.getAssign();
			Value[] values = s.getValues();
			if(values != null) {
				for(int j = 0; j < values.length; j++) {
					if(!values[j].isConstant()) {
						ArrayList<IndexWrapper> iw = map.get(values[j]);
						if(iw == null) {
							throw new AssertionError("Variable " + table.getVariableName((Variable) values[j]) + " is used before it is assigned");
						}
						for(int n = 0; n < iw.size(); n++) {
							duChain.get(iw.get(n)).add(s);
						}
					}
				}
			}
			if(assign != null) {
				IndexWrapper iw = new IndexWrapper(assign, System.identityHashCode(s), s);
				duChain.put(iw, new TreeSet<Statement>());
				map.remove(assign);
				ArrayList<IndexWrapper> array = new ArrayList<IndexWrapper>();
				array.add(iw);
				map.put(assign, array);
			}
			if(s.getChildCount() > 0) {
				List<? extends Statement> children = s.getChildren();
				for(int j = 0; j < children.size(); j++) {
					iterate(children.get(j));
				}
			}
		}
	}

	private static class IndexWrapper {

		final Variable variable;
		final int label;
		final Statement statement;

		public IndexWrapper(Variable v, int l, Statement s) {
			variable = v;
			label = l;
			statement = s;
		}

		public Statement getStatement() {
			return statement;
		}

		public Variable getVariable() {
			return variable;
		}
>>>>>>> Stashed changes

		public int getLabel() {
			return label;
		}
	}
}
