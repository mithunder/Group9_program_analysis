package com.github.mithunder.statements;

public class CompilationUnit {

	protected Statement root;
	protected String unitName;
	protected VariableTable varTable;

	public CompilationUnit(String unitName, Statement root, VariableTable varTable){
		this.unitName = unitName;
		this.root = root;
		this.varTable = varTable;
	}

	public String getUnitName(){
		return unitName;
	}

	public Statement getRootStatement(){
		return root;
	}

	public VariableTable getVariableTable(){
		return varTable;
	}
}
