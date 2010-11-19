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
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;

public class ReachingDefinitionAnalysis extends KillRepairAnalysis {

	protected VariableTable table;

	@Override
	public boolean evaluate(EvaluatedStatement statement, Evaluation e, KillRepairAnalysisWorklist w) {
		ReachingDefinitionEvaluation orde = (ReachingDefinitionEvaluation)e;
		ReachingDefinitionEvaluation rde = (ReachingDefinitionEvaluation)statement.getExitEvaluation();
		boolean changed = false;
		int stype = statement.getStatementType();
		Variable assign = statement.getAssign();
		Set<Statement> old = null;
		if(rde == null){
			rde = new ReachingDefinitionEvaluation(table);
			statement.setExitEvaluation(rde);
			changed = true;
		} else {
			if(assign != null && !table.isTemporaryVariable(assign)) {
				old = rde.map.get(assign);
			}
		}
		rde.merge(orde);
		if(old != null){
			Set<Statement> cur = rde.map.get(assign);
			changed = !old.equals(cur);
		}
		if(stype == StatementType.WRITE) {
			return changed;
		}
		if(stype == StatementType.READ || StatementType.isBinary(stype) ||
				StatementType.isUnary(stype)){
			if(!table.isTemporaryVariable(assign)){
				rde.remove(assign);
				rde.add(assign, statement);
			}
		}
		return changed;
	}


	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		ReachingDefinitionEvaluation re1 = (ReachingDefinitionEvaluation)e1;
		ReachingDefinitionEvaluation re2 = (ReachingDefinitionEvaluation)e2;
		ReachingDefinitionEvaluation rde = new ReachingDefinitionEvaluation(table);
		if(e1 != null) {
			rde.merge(re1);
		}
		if(e2 != null) {
			rde.merge(re2);
		}
		return rde;
	}

	@Override
	public Evaluation initEvaluation() {
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

	public static class ReachingDefinitionEvaluation extends Evaluation {

		Map<Variable,Set<Statement>> map;
		VariableTable te;

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

		void add(Variable var, Statement s){
			Set<Statement> set = map.get(var);
			if(s instanceof EvaluatedStatement){
				s = ((EvaluatedStatement)s).getStatement();
			}
			if(set == null) {
				set = makeSet(null);
				map.put(var, set);
			}
			set.add(s);
		}

		void remove(Variable var){
			map.remove(var);
		}

		void merge(ReachingDefinitionEvaluation other){
			for(Map.Entry<Variable, Set<Statement> > el : other.map.entrySet()){
				Variable v = el.getKey();
				Set<Statement> oset = el.getValue();
				Set<Statement> cset = map.get(v);
				if(cset == null){
					cset = makeSet(oset);
					map.put(v, cset);
				} else {
					cset.addAll(oset);
				}
			}
		}

		public Map<Variable,Set<Statement>> getMap() {
			return map;
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
