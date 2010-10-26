package com.github.mithunder.statements;

import com.github.mithunder.statements.simple.SimpleVariableTable;

public abstract class VariableTable {

	/*
	 * Returns the variable for the given varName. If it does not exists, it will then be created.
	 */
	public abstract Variable getVariable(String varName);

	/*
	 * Returns the name of a variable.
	 */
	public abstract String getVariableName(Variable variable);

	/*
	 * Creates a new temporary variable.
	 */
	public abstract Variable createTemporaryVariable(int vtype);

	/*
	 * For when reading a temporary variable.
	 * It is undefined what happens if the temporary variable is not created first.
	 */
	public abstract Variable getCurrentTemporaryVariable();

	public abstract boolean isTemporaryVariable(Variable var);

	public static VariableTable newInstance() {
		return new SimpleVariableTable();
	}
}
