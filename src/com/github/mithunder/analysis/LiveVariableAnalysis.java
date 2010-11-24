package com.github.mithunder.analysis;

import java.util.Set;
import java.util.TreeSet;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;

public class LiveVariableAnalysis extends KillRepairAnalysis {

	private VariableTable table;

	@Override
	public boolean evaluate(EvaluatedStatement statement, Evaluation e, int eqv, KillRepairAnalysisWorklist w) {
		LiveVariableEvaluation olve = (LiveVariableEvaluation) e;
		LiveVariableEvaluation lve = (LiveVariableEvaluation)statement.getExitEvaluation();
		boolean changed = false;
		boolean[] pre = null;
		Value[] values = statement.getValues();
		if(lve == null|| (eqv == StatementType.ABORT && lve.set.size() > 0)) {
			lve = new LiveVariableEvaluation(table);
			statement.setExitEvaluation(lve);
			changed = true;
		} else if(values != null) {
			pre = new boolean[values.length];
			for(int i = 0 ; i < values.length ; i++) {
				if(!values[i].isConstant()) {
					pre[i] = lve.contains((Variable)values[i]);
				}
			}
		}
		if(StatementType.ABORT == eqv) {
			/* We do not get anything from abort statements */
			return changed;
		}
		lve.merge(olve);
		if(w.isGuard() || statement.getStatementType() == StatementType.WRITE
				|| lve.remove(statement.getAssign()) ){
			for(int i = 0; i < values.length ; i++){
				Value v = values[i];
				if(!v.isConstant()){
					Variable var = (Variable)v;
					lve.add(var);
					if(pre != null) {
						changed = !pre[i];
					}
				}
			}
		}
		return changed;
	}

	@Override
	public boolean canRepair() {
		return false;
	}

	@Override
	public void leavingGuard(EvaluatedStatement guard, KillRepairAnalysisWorklist w) {
	}

	@Override
	public Evaluation repairAnalysis(EvaluatedStatement killed, Evaluation e) {
		throw new UnsupportedOperationException("Cannot repair analysis");
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		LiveVariableEvaluation lv1 = (LiveVariableEvaluation) e1;
		LiveVariableEvaluation lv2 = (LiveVariableEvaluation) e2;
		LiveVariableEvaluation e = new LiveVariableEvaluation(table);
		if(e1 != null) {
			e.merge(lv1);
		}
		if(e2 != null) {
			e.merge(lv2);
		}
		return e;
	}

	@Override
	public Evaluation initEvaluation() {
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

	public static class LiveVariableEvaluation extends Evaluation {

		Set<Variable> set;
		VariableTable table;

		LiveVariableEvaluation(VariableTable vt) {
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
				final String varname = table.getVariableName(v);
				t.add(varname);
			}
			if(t.size() > 0) {
				for(String str : t){
					s += ", " + str;
				}
				s = s.substring(2);
			}
			return "[" + s + "]";
		}
	}

}
