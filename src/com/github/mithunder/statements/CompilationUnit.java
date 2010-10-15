package com.github.mithunder.statements;

public class CompilationUnit {

	protected Statement root;
	protected String unitName;

	public CompilationUnit(String unitName, Statement root){
		this.unitName = unitName;
		this.root = root;
	}

	public String getUnitName(){
		return unitName;
	}

	public Statement getRootStatement(){
		return root;
	}
}
