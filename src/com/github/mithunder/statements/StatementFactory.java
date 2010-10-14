package com.github.mithunder.statements;

import java.util.List;

public abstract class StatementFactory {

	/*
	* Simple three-address statement
	* read assign
	* write assign
	* assign := v[1] op v[2] (binary op)
	* assign := op v[1] (unary op)
	* assign := v[1] (for type = assign)
	* Number of arguments accepted for v is 1 or 2 depending on which type is used
	*/
	public abstract Statement createSimpleStatement(int type, Variable assign, Value ... v);
	//Meant for things like abort.
	public abstract Statement createSimpleStatement(int type);
	
	/*
	* Creates grouping statement
	* type =  do/if/{}/AND/OR
	* The result of a test is s[s.length - 1].getAssign() (should return temporary variable)
	* 
	*/
	public abstract Statement createCompoundStatement(int type, Statement ... s);
	public abstract Statement createCompoundStatement(int type, List<Statement> s);

	/*
	* Method for creating a root node containing s as its children
	*/
	public abstract Statement createRootStatement(Statement ... s);
	public abstract Statement createRootStatement(List<Statement> s);

	public static StatementFactory newInstance() {
		return null;
	}
}
