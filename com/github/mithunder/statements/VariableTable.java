package com.github.mithunder.statements;

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
	public abstract Variable createTemporaryVariable();

	/*
	* For when reading a temporary variable.
	* It is undefined what happens if the temporary variable is not created first.
	*/
	public abstract Variable getCurrentTemporaryVariable();

	public static VariableTable newInstance() {
		return null;
	}
}
