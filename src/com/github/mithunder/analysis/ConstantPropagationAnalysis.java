package com.github.mithunder.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class ConstantPropagationAnalysis extends Analysis {

	public static final int UNDEFINED = 0;
	public static final int UNKNOWN = 1;
	public static final int CONSTANT = 2;

	protected VariableTable table;

	@Override
	public boolean evaluate(EvaluatedStatement statement, Evaluation e) {
		CPAEvaluation ocpae = (CPAEvaluation)e;
		CPAEvaluation cpae = (CPAEvaluation)statement.getEvaluation();
		boolean changed = false;
		if(cpae == null){
			cpae = new CPAEvaluation(table);
			statement.setEvaluation(cpae);
			cpae.merge(ocpae);
			changed = true;
		} else {
			changed = cpae.merge(ocpae);
		}
		return constantProgate(cpae, statement) || changed;
	}

	protected boolean constantProgate(CPAEvaluation cpae, Statement s){
		int stype = s.getStatementType();
		Variable assign = s.getAssign();
		boolean changed = false;
		if(assign == null || stype == StatementType.WRITE || stype == StatementType.SKIP) {
			// Does not change anything for us.
			return changed;
		}
		if(stype == StatementType.READ){
			cpae.add(assign, UNKNOWN);
		} else {
			Value[] v = s.getValues();
			ConstantValue result = null;
			if(StatementType.isUnary(stype)){
				ConstantValue con = getConstantValue(cpae, v[0]);
				if(con != null) {
					switch(stype){
					case StatementType.LOGIC_NOT: result = con.getValue() == 0?ConstantValue.TRUE:ConstantValue.FALSE; break;
					case StatementType.SIGN_INVERT: result = ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, con.getValue()); break;
					case StatementType.ASSIGN: result = con;
					}
				}
			} else if(StatementType.isBinary(stype)){
				ConstantValue lhs = getConstantValue(cpae, v[0]);
				ConstantValue rhs = getConstantValue(cpae, v[1]);
				// Are both sides constants (or is it a minus [check for x - x = 0 ])
				if((lhs == null || rhs == null) && stype != StatementType.MINUS){
					// The result is unknown unless we are doing x * 0.
					if( stype != StatementType.MULTIPLY || rhs == lhs ){
						return cpae.add(assign, UNKNOWN) || changed;
					}
				}
				switch(stype){
				case StatementType.PLUS:      result = ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, lhs.getValue() + rhs.getValue()); break;
				case StatementType.MINUS:
					if(rhs != null && lhs != null) {
						result = ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, lhs.getValue() - rhs.getValue());
					} else {
						if(v[0].equals(v[1])){
							result = ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, 0);
						}
					}
					break;
				case StatementType.MULTIPLY:
					if(lhs == null || rhs == null){
						// Maybe we have a 0-multiplication.
						ConstantValue c = lhs;
						if(lhs == null) {
							c = rhs;
						}
						if(c.getValue() == 0) {
							result = c;
						}
					} else {
						result = ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, lhs.getValue() * rhs.getValue());
					}
					break;
				case StatementType.DIVIDE:
					// Let's not divide by zero - We cannot throw an error, because the data here is not guaranteed to remain.
					// e.g. there may be more than one path here.
					if(rhs.getValue() != 0) {
						result = ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, lhs.getValue() * rhs.getValue());
					}
					break;
				case StatementType.LOGIC_AND: result = lhs.getValue() != 0 && rhs.getValue() != 0? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.LOGIC_OR:  result = lhs.getValue() != 0 || rhs.getValue() != 0? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.GT: 		  result = lhs.getValue()      >  rhs.getValue()     ? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.GT_EQ:     result = lhs.getValue()      >= rhs.getValue()     ? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.LT: 		  result = lhs.getValue()      <  rhs.getValue()     ? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.LT_EQ:     result = lhs.getValue()      <= rhs.getValue()     ? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.EQ: 		  result = lhs.getValue()      == rhs.getValue()     ? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				case StatementType.NEQ:       result = lhs.getValue()      != rhs.getValue()     ? ConstantValue.TRUE : ConstantValue.FALSE;    break;
				}
			}

			if(result != null){
				return cpae.add(assign, result) || changed;
			} else {
				return cpae.add(assign, UNKNOWN) || changed;
			}
		}
		return changed;
	}

	@Override
	public boolean evaluateCondition(EvaluatedStatement condition, EvaluatedStatement statement, Evaluation e) {
		return evaluate(condition, e);
	}

	protected static ConstantValue getConstantValue(CPAEvaluation cpae, Value v){
		CPAInfo i;
		if(v.isConstant()) {
			return (ConstantValue)v;
		}
		i = cpae.cpadata.get(v);
		if(i == null) {
			return null;
		}
		return i.value;
	}

	@Override
	public void finishAnalysis(CompilationUnit unit) {
		table = null;
	}

	@Override
	public Evaluation initEvaluation(Statement s) {
		return new CPAEvaluation(table);
	}

	@Override
	public boolean isForwardAnalysis() {
		return true;
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		CPAEvaluation re1 = (CPAEvaluation)e1;
		CPAEvaluation re2 = (CPAEvaluation)e2;
		CPAEvaluation result = new CPAEvaluation(table);
		if(e1 != null) {
			result.merge(re1);
		}
		if(e2 != null) {
			result.merge(re2);
		}
		return result;
	}

	@Override
	public void startAnalysis(CompilationUnit unit) {
		table = unit.getVariableTable();
	}

	// Must remain IMMUTABLE
	static class CPAInfo {

		public static final CPAInfo UNDEF = new CPAInfo(UNDEFINED);
		public static final CPAInfo UNK = new CPAInfo(UNKNOWN);

		final int def;
		final ConstantValue value;

		public CPAInfo(ConstantValue v){
			def = CONSTANT;
			this.value = v;
			if(v == null) {
				throw new IllegalArgumentException();
			}
		}

		public CPAInfo(int d){
			this.def = d;
			if(d == CONSTANT) {
				throw new IllegalArgumentException();
			}
			value = null;
		}

		@Override
		public boolean equals(Object o){
			CPAInfo i;
			if(o == this) {
				return true;
			}
			if(o == null) {
				return false;
			}
			if(!(o instanceof CPAInfo)) {
				return false;
			}
			i = (CPAInfo)o;
			if(i.def != def) {
				return false;
			}
			if(def != CONSTANT) {
				return true;
			}
			return value.equals(i.value);
		}

		@Override
		public int hashCode(){
			int h = def;
			if(value != null) {
				h += value.hashCode();
			}
			return h;
		}
	}

	public static class CPAEvaluation extends Evaluation {
		Map<Variable, CPAInfo> cpadata = new HashMap<Variable, CPAInfo>();
		final VariableTable table;

		public CPAEvaluation(VariableTable table){
			this.table = table;
		}

		public boolean add(Variable v, ConstantValue c){
			CPAInfo info = cpadata.get(v);
			if(info != null) {
				if(info.def == CONSTANT) {
					if(c.equals(info.value)){
						return false;
					}
					cpadata.put(v, CPAInfo.UNK);
				} else {
					if(info.def == UNKNOWN) {
						return false;
					}
					cpadata.put(v, CPAInfo.UNK);
				}
			} else {
				cpadata.put(v, new CPAInfo(c));
			}
			return true;
		}

		public boolean add(Variable v, int def){
			CPAInfo info = cpadata.get(v);
			if(info == null) {
				cpadata.put(v, def == UNKNOWN? CPAInfo.UNK : CPAInfo.UNDEF);
				return true;
			}
			if(info.def == def || info.def == UNKNOWN) {
				return false;
			}
			cpadata.put(v, CPAInfo.UNK);
			return true;
		}

		public ConstantValue getConstant(Variable v){
			CPAInfo info = cpadata.get(v);
			if(info.def == CONSTANT) {
				return info.value;
			}
			return null;
		}

		public boolean merge(CPAEvaluation e){
			boolean changed = false;
			for(Map.Entry<Variable, CPAInfo> entry : e.cpadata.entrySet() ) {
				Variable v = entry.getKey();
				CPAInfo ei = entry.getValue();
				CPAInfo ci = cpadata.get(v);
				if(ci != null){
					if(!ci.equals(ei)) {
						cpadata.put(v, CPAInfo.UNK);
						changed = true;
					}
				} else {
					cpadata.put(v, ei);
					changed = true;
				}
			}
			return changed;
		}

		@Override
		public String toString(){
			String s = "";
			TreeSet<String> t = new TreeSet<String>();
			for(Map.Entry<Variable, CPAInfo> e : cpadata.entrySet()){
				if(table.isTemporaryVariable(e.getKey())) {
					continue;
				}
				final String varname = table.getVariableName(e.getKey());
				final CPAInfo info = e.getValue();
				String value;
				if(info.def == CONSTANT){
					if(info.value.getValueType() == ValueType.BOOLEAN_TYPE){
						if(info.value.getValue() != 0) {
							value = "true";
						} else {
							value = "false";
						}
					} else {
						value = String.valueOf(info.value.getValue());
					}
				} else
					if(info.def == UNKNOWN) {
						value = "?";
					} else {
						value = "U";
					}
				t.add(varname + " = " + value);
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
