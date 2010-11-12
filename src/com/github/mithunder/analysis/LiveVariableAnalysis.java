package com.github.mithunder.analysis;

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

	protected VariableTable table;

	@Override
	public boolean evaluate(EvaluatedStatement statement, Evaluation e) {
		LiveVariableEvaluation olve = (LiveVariableEvaluation) e;
		boolean changed = false;
		if(olve.remove(statement.getAssign()) || (statement.getStatementType() == StatementType.WRITE)) {
			Value[] values = statement.getValues();
			for(int i = 0; i < values.length; i++) {
				if(!values[i].isConstant()) {
					Variable v = (Variable) values[i];
					if(!table.isTemporaryVariable(v)) {
						changed = olve.add(v);
					}
				}
			}
		}
		return changed;
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		LiveVariableEvaluation lv1 = (LiveVariableEvaluation) e1;
		LiveVariableEvaluation lv2 = (LiveVariableEvaluation) e2;
		if(lv1 == null && lv2 == null) {
			return new LiveVariableEvaluation();
		} else if(lv1 == null) {
			return lv2;
		} else if(lv2 == null) {
			return lv1;
		} else {
			LiveVariableEvaluation e = new LiveVariableEvaluation();
			e.merge(lv1);
			e.merge(lv2);
			return e;
		}
	}

	@Override
	public Evaluation initEvaluation(Statement s) {
		return new LiveVariableEvaluation();
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

		private LiveVariableEvaluation() {
			set = new TreeSet<Variable>();
		}

		boolean add(Variable v) {
			return set.add(v);
		}

		boolean remove(Variable v) {
			return set.remove(v);
		}

		boolean merge(LiveVariableEvaluation other) {
			return set.addAll(other.set);
		}

		boolean contains(Variable v) {
			return set.contains(v);
		}
	}
}
