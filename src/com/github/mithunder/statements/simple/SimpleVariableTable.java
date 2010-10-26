package com.github.mithunder.statements.simple;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class SimpleVariableTable extends VariableTable {

	protected int tempvar = -1;
	protected SimpleVariable curtemp;
	protected List<String> names = new ArrayList<String>();
	protected Map<String, SimpleVariable> name2var = new Hashtable<String, SimpleVariable>();

	@Override
	public Variable createTemporaryVariable(int vtype) {
		return curtemp = new SimpleVariable(vtype, tempvar--);
	}

	@Override
	public Variable getCurrentTemporaryVariable() {
		if(curtemp == null) {
			throw new IllegalStateException("Called getCurrentTemporaryVariable without calling create first!");
		}
		return curtemp;
	}

	@Override
	public Variable getVariable(String varName) {
		SimpleVariable var = name2var.get(varName);
		if(var == null) {
			var = new SimpleVariable(ValueType.INTEGER_TYPE, names.size());
			names.add(varName);
			name2var.put(varName, var);
		}
		return var;
	}

	@Override
	public String getVariableName(Variable var) {
		SimpleVariable v = (SimpleVariable)var;
		if(isTemporaryVariable(var)){
			return "Temp#" + Math.abs(v.vid);
		}
		return names.get(v.vid);
	}

	public int getNamedVariableCount(){
		return names.size();
	}

	@Override
	public boolean isTemporaryVariable(Variable var) {
		SimpleVariable v = (SimpleVariable)var;
		return v.vid < 0;
	}

}
