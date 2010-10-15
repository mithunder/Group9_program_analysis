package com.github.mithunder.statements.simple;

import static com.github.mithunder.statements.StatementType.ABORT;
import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;
import static com.github.mithunder.statements.StatementType.WRITE;

import java.util.ArrayList;
import java.util.List;

import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CodeLocation;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementFactory;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;

public class SimpleStatementFactory extends StatementFactory{

	@Override
	public Statement createCompoundStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, Statement... s) {
		isGroupingStatement(type);
		return new SimpleStatement(type, null, codeLoc, annotations, array2list(s));
	}

	@Override
	public Statement createCompoundStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, List<Statement> s) {
		isGroupingStatement(type);
		return new SimpleStatement(type, null, codeLoc, annotations, typecast(s));
	}

	@Override
	public Statement createRootStatement(CodeLocation codeLoc, List<Annotation> annotations, Statement... s) {
		return new SimpleStatement(SCOPE, null, codeLoc, annotations, array2list(s));
	}

	@Override
	public Statement createRootStatement(CodeLocation codeLoc, List<Annotation> annotations, List<Statement> s) {
		return new SimpleStatement(SCOPE, null, codeLoc, annotations, typecast(s));
	}

	@Override
	public Statement createSimpleStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, Variable assign, Value... v) {
		isSimpleStatement(type);
		return new SimpleStatement(type, assign, codeLoc, annotations, v);
	}

	@Override
	public Statement createSimpleStatement(int type, CodeLocation codeLoc, List<Annotation> annotations) {
		if(type != ABORT && type != WRITE) {
			throw new IllegalArgumentException("Simple statement without assignment most be ABORT or WRITE.");
		}
		return new SimpleStatement(type, null, codeLoc, annotations);
	}

	@SuppressWarnings("unchecked")
	private List<SimpleStatement> typecast(List<Statement> s){
		return (List)s;
	}

	private List<SimpleStatement> array2list(Statement[] s){
		List<SimpleStatement> l = new ArrayList<SimpleStatement>(s.length);
		for(int i = 0 ; i < s.length ; i++) {
			l.add((SimpleStatement)s[i]);
		}
		return l;
	}

	protected void isSimpleStatement(int stype) throws IllegalArgumentException{
		switch(stype){
		case DO:
		case IF:
		case SCOPE:
			throw new IllegalArgumentException("Statement type was not a simple statement.");
		default:
			break;
		}
	}

	protected void isGroupingStatement(int stype) throws IllegalArgumentException{
		switch(stype){
		case DO:
		case IF:
		case SCOPE:
			break;
		default:
			throw new IllegalArgumentException("Statement type was not a grouping statement.");
		}
	}

}