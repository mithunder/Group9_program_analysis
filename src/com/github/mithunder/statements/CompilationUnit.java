package com.github.mithunder.statements;

import java.util.List;

public class CompilationUnit {

	protected Statement root;
	protected String unitName;
	protected VariableTable varTable;
	protected List<Statement> endStatements;

	public CompilationUnit(String unitName, Statement root, VariableTable varTable,
			List<Statement> endStatements){
		this.unitName = unitName;
		this.root = root;
		this.varTable = varTable;
		this.endStatements = endStatements;
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

	public List<Statement> getFinalStatements(){
		return endStatements;
	}
}
