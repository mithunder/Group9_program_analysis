package com.github.mithunder.analysis;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class LiveVariableAnalysis extends Analysis {

	private VariableTable table;

	@Override
	public boolean evaluate(EvaluatedStatement statement, Evaluation e) {
		LiveVariableEvaluation olve = (LiveVariableEvaluation) e;
		LiveVariableEvaluation lve = (LiveVariableEvaluation)statement.getEvaluation();
		boolean changed = false;
		if(lve == null) {
			lve = new LiveVariableEvaluation(olve.set, table);
			statement.setEvaluation(lve);
			changed = true;
		} else {
			changed = lve.merge(olve);
		}
		if((statement.getStatementType() == StatementType.WRITE)
				|| lve.remove(statement.getAssign())
				|| StatementType.isComparison(statement.getStatementType())) {
			Value[] values = statement.getValues();
			boolean returnNow = true;
			if(StatementType.isComparison(statement.getStatementType())) {
				for(int i = 0; i < values.length; i++) {
					if(!values[i].isConstant() && lve.contains((Variable) values[i])) {
						returnNow = false;
						break;
					}
				}
			} else {
				returnNow = false;
			}
			if(!returnNow) {
				for(int i = 0; i < values.length; i++) {
					if(!values[i].isConstant()) {
						Variable v = (Variable) values[i];
						if(statement.getAssign() != null && statement.getAssign().equals(v)) {
							lve.add(v);
						} else {
							changed = lve.add(v) || changed;
						}
					}
				}
			}
		}
		return changed;
	}

	@Override
	public boolean evaluateCondition(EvaluatedStatement condition, EvaluatedStatement statement, Evaluation e) {
		System.out.println("evaluateCondition start");
		if(condition.getStatementType() != StatementType.SCOPE) {
			throw new AssertionError();
		}
		boolean changed = false;
		boolean condInE = false;
		LiveVariableEvaluation olve = (LiveVariableEvaluation) e;
		System.out.println("evaluation: " + olve);
		List<EvaluatedStatement> list = condition.getChildren();
		for(int i = 0; i < list.size(); i++) {
			EvaluatedStatement es = list.get(i);
			Value[] values = es.getValues();
			if(StatementType.isComparison(es.getStatementType())) {
				for(int j = 0; j < values.length; j++) {
					if(!values[j].isConstant()) {
						System.out.println("checking: " + table.getVariableName((Variable) values[j]));
					}
					if(!values[j].isConstant() && olve.contains((Variable) values[j])) {
						condInE = true;
						System.out.println("contains variable " + table.getVariableName((Variable) values[j]));
						break;
					}
				}
				if(condInE) {
					break;
				}
			}
		}
		if(condInE || containsAssign(statement, olve)) {
			if(!condInE) {
				System.out.println("statement contains assign");
			}
			LiveVariableEvaluation lve = (LiveVariableEvaluation) condition.getEvaluation();
			changed = lve.merge(olve) || changed;
			for(int i = 0; i < list.size(); i++) {
				EvaluatedStatement es = list.get(i);
				Value[] values = es.getValues();
				System.out.println("adding assign: " + table.getVariableName(es.getAssign()));
				System.out.print(lve + " -> ");
				lve.add(es.getAssign());
				System.out.println(lve);
				for(int j = 0; j < values.length; j++) {
					if(!values[j].isConstant()) {
						Variable v = (Variable) values[j];
						if(table.isTemporaryVariable(v)) {
							System.out.println("removing: " + table.getVariableName(v));
							System.out.print(lve + " -> ");
							lve.remove(v);
							System.out.println(lve);
						} else {
							System.out.println("adding: " + table.getVariableName(v));
							System.out.print(lve + " -> ");
							changed = lve.add(v) || changed;
							System.out.println(lve);
						}
					}
				}
			}
		}
		System.out.println("evaluateCondition end");
		return changed;
	}

	private boolean containsAssign(EvaluatedStatement root, LiveVariableEvaluation e) {
		boolean contains = false;
		if(root.getChildCount() > 0) {
			ListIterator<EvaluatedStatement> iterator = root.getChildren().listIterator();
			while(iterator.hasNext()) {
				EvaluatedStatement es = iterator.next();
				contains = containsAssign(es, e);
				if(contains) {
					break;
				}
			}
		}
		if(root.getAssign() != null && e.contains(root.getAssign())) {
			contains = true;
		}
		return contains;
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		LiveVariableEvaluation lv1 = (LiveVariableEvaluation) e1;
		LiveVariableEvaluation lv2 = (LiveVariableEvaluation) e2;
		if(lv1 == null && lv2 == null) {
			return new LiveVariableEvaluation(table);
		} else if(lv1 == null) {
			return lv2;
		} else if(lv2 == null) {
			return lv1;
		} else {
			LiveVariableEvaluation e = new LiveVariableEvaluation(table);
			e.merge(lv1);
			e.merge(lv2);
			return e;
		}
	}

	@Override
	public Evaluation initEvaluation(Statement s) {
		return new LiveVariableEvaluation(table);
	}

	@Override
	public boolean isForwardAnalysis() {
		return false;
	}

	@Override
	public void startAnalysis(CompilationUnit unit) {
		table = unit.getVariableTable();
	}

	@Override
	public void finishAnalysis(CompilationUnit unit) {
		table = null;
	}

	static class LiveVariableEvaluation extends Evaluation {

		Set<Variable> set;
		VariableTable table;

		private LiveVariableEvaluation(Set<Variable> set, VariableTable vt) {
			this.set = new TreeSet<Variable>();
			for(Variable v : set) {
				this.set.add(v);
			}
			table = vt;
		}

		private LiveVariableEvaluation(VariableTable vt) {
			set = new TreeSet<Variable>();
			table = vt;
		}

		boolean add(Variable v) {
			if(v == null) {
				return false;
			}
			return set.add(v);
		}

		boolean remove(Variable v) {
			if(v == null) {
				return false;
			}
			return set.remove(v);
		}

		boolean merge(LiveVariableEvaluation other) {
			return set.addAll(other.set);
		}

		boolean contains(Variable v) {
			return set.contains(v);
		}

		@Override
		public String toString(){
			String s = "";
			TreeSet<String> t = new TreeSet<String>();
			for(Variable v : set) {
				String sts = null;
				final String varname = table.getVariableName(v);
				sts = Integer.toHexString(System.identityHashCode(v));
				t.add(varname + " = " + sts);
			}
			if(t.size() > 0) {
				for(String str : t){
					s += "; " + str;
				}
				s = s.substring(2);
			}
			return "[" + s + "]";
		}
	}
}
