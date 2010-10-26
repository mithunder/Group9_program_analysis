package com.github.mithunder.analysis;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class ReachingDefinitionAnalysis extends Analysis {

	protected VariableTable table;

	@Override
	public boolean evaluate(EvaluatedStatement statement, Evaluation e) {
		ReachingDefinitionEvaluation orde = (ReachingDefinitionEvaluation)e;
		ReachingDefinitionEvaluation rde = (ReachingDefinitionEvaluation)statement.getEvaluation();
		boolean changed = false;
		int stype = statement.getStatementType();
		if(rde == null){
			rde = new ReachingDefinitionEvaluation(orde.map, table);
			statement.setEvaluation(rde);
			changed = true;
		} else {
			changed = rde.merge(orde);
		}
		if(stype == StatementType.READ || StatementType.isBinary(stype) ||
				StatementType.isUnary(stype)){
			Variable assign = statement.getAssign();
			if(stype != StatementType.WRITE && !table.isTemporaryVariable(assign)){
				changed = rde.remove(assign) || changed;
			}
			if(stype != StatementType.READ){
				Value[] v = statement.getValues();
				changed = checkValue(rde, v[0], statement, changed);
				if(StatementType.isBinary(stype)) {
					changed = checkValue(rde, v[1], statement, changed);
				}
			}
		}
		return changed;
	}

	protected boolean checkValue(ReachingDefinitionEvaluation rde, Value v, Statement s, boolean changed){
		Variable var;
		if(v.isConstant()) {
			return changed;
		}
		var = (Variable)v;
		if(table.isTemporaryVariable(var)) {
			return changed;
		}
		return rde.add(var, s) || changed;
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		ReachingDefinitionEvaluation re1 = (ReachingDefinitionEvaluation)e1;
		ReachingDefinitionEvaluation re2 = (ReachingDefinitionEvaluation)e2;
		ReachingDefinitionEvaluation rde;
		if(e1 == null && e2 == null) {
			return new ReachingDefinitionEvaluation(table);
		} else if(e1 == null) {
			return new ReachingDefinitionEvaluation(re2.map, table);
		} else if(e2 == null) {
			return new ReachingDefinitionEvaluation(re1.map, table);
		} else {
			rde = new ReachingDefinitionEvaluation(re1.map, table);
			rde.merge(re2);
			return rde;
		}
	}

	@Override
	public Evaluation initEvaluation(Statement s) {
		return new ReachingDefinitionEvaluation(table);
	}

	@Override
	public boolean isForwardAnalysis() {
		return true;
	}

	@Override
	public void startAnalysis(CompilationUnit unit) {
		table = unit.getVariableTable();
	}

	@Override
	public void finishAnalysis(CompilationUnit unit) {
		table = null;
	}

	static class ReachingDefinitionEvaluation extends Evaluation {

		Map<Variable,Set<Statement>> map;
		VariableTable te;

		ReachingDefinitionEvaluation(Map<Variable, Set<Statement>> map, VariableTable table) {
			this.map = new HashMap<Variable, Set<Statement>>(map);
			this.te = table;
			if(te == null || map == null) {
				throw new IllegalArgumentException();
			}
		}

		ReachingDefinitionEvaluation(VariableTable table) {
			map = new HashMap<Variable, Set<Statement>>(1);
			this.te = table;
			if(te == null || map == null) {
				throw new IllegalArgumentException(te + " " + map);
			}
		}

		Set<Statement> makeSet(Set<Statement> s){
			TreeSet<Statement> t =new TreeSet<Statement>(new Comparator<Statement>() {
				@Override
				public int compare(Statement o1, Statement o2) {
					return 0;
				}
			});
			if(s != null) {
				t.addAll(s);
			}
			return t;
		}

		boolean add(Variable var, Statement s){
			Set<Statement> set = map.get(var);
			if(set == null) {
				set = makeSet(null);
				map.put(var, set);
			}
			return set.add(s);
		}

		boolean remove(Variable var){
			Set<?> o = map.remove(var);
			return o != null && o.size() > 0;
		}

		boolean merge(ReachingDefinitionEvaluation other){
			boolean changes = false;
			for(Map.Entry<Variable, Set<Statement> > el : other.map.entrySet()){
				Variable v = el.getKey();
				Set<Statement> oset = el.getValue();
				Set<Statement> cset = map.get(v);
				if(cset == null){
					cset = makeSet(oset);
					map.put(v, cset);
					changes = true;
				} else {
					changes = cset.addAll(oset) || changes;
				}
			}
			return changes;
		}

		@Override
		public String toString(){
			String s = "";
			TreeSet<String> t = new TreeSet<String>();
			for(Map.Entry<Variable, Set<Statement>> e : map.entrySet()){
				final int c = e.getValue().size();
				final String varname = te.getVariableName(e.getKey());
				t.add(varname + " = " + c);
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
