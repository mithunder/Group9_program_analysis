package com.github.mithunder.statements;

import java.util.List;

import com.github.mithunder.statements.simple.SimpleStatementFactory;

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
	public abstract AbstractStatement createSimpleStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, Variable assign, Value ... v);
	//Meant for things like abort.
	public abstract AbstractStatement createSimpleStatement(int type, CodeLocation codeLoc, List<Annotation> annotations);

	/*
	 * Creates grouping statement
	 * type =  do/if/{}
	 * The result of a test is s[s.length - 1].getAssign() (should return temporary variable)
	 *
	 */
	public abstract AbstractStatement createCompoundStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, AbstractStatement ... s);
	public abstract AbstractStatement createCompoundStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, List<AbstractStatement> s);

	/*
	 * Method for creating a root node containing s as its children
	 */
	public abstract AbstractStatement createRootStatement(CodeLocation codeLoc, List<Annotation> annotations, AbstractStatement ... s);
	public abstract AbstractStatement createRootStatement(CodeLocation codeLoc, List<Annotation> annotations, List<AbstractStatement> s);

	public static StatementFactory newInstance() {
		return new SimpleStatementFactory();
	}
}
