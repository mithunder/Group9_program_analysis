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
		if(stype == StatementType.WRITE) {
			return changed;
		}
		if(stype == StatementType.READ || StatementType.isBinary(stype) ||
				StatementType.isUnary(stype)){
			Variable assign = statement.getAssign();
			if(!table.isTemporaryVariable(assign)){
				rde.remove(assign);
				rde.add(assign, statement);
			}
		}
		return changed;
	}

	@Override
	public boolean evaluateCondition(EvaluatedStatement condition, EvaluatedStatement statement, Evaluation e) {
		return evaluate(condition, e);
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
		} else {
			rde = new ReachingDefinitionEvaluation(re1.map, table);
			if(e2 != null) {
				rde.merge(re2);
			}
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

		ReachingDefinitionEvaluation(Map<Variable, Set<Statement>> m, VariableTable table) {
			this.map = new HashMap<Variable, Set<Statement>>();
			this.te = table;
			for(Map.Entry<Variable, Set<Statement>> e : m.entrySet()){
				this.map.put(e.getKey(), makeSet(e.getValue()));
			}
			if(te == null) {
				throw new IllegalArgumentException();
			}
		}

		ReachingDefinitionEvaluation(VariableTable table) {
			map = new HashMap<Variable, Set<Statement>>(1);
			this.te = table;
			if(te == null) {
				throw new IllegalArgumentException("" + te);
			}
		}

		Set<Statement> makeSet(Set<Statement> s){
			TreeSet<Statement> t = new TreeSet<Statement>(new Comparator<Statement>() {
				@Override
				public int compare(Statement o1, Statement o2) {
					return System.identityHashCode(o1) - System.identityHashCode(o2);
				}
			});
			if(s != null) {
				t.addAll(s);
			}
			return t;
		}

		boolean add(Variable var, Statement s){
			Set<Statement> set = map.get(var);
			if(s instanceof EvaluatedStatement){
				s = ((EvaluatedStatement)s).getStatement();
			}
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
				String sts = null;
				final String varname = te.getVariableName(e.getKey());
				for(Statement st : e.getValue()){
					if(st instanceof EvaluatedStatement){
						st = ((EvaluatedStatement)st).getStatement();
					}
					if(sts != null) {
						sts += ", " + Integer.toHexString(System.identityHashCode(st));
					} else {
						sts = Integer.toHexString(System.identityHashCode(st));
					}
				}
				if(sts == null) {
					sts = "";
				}
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
