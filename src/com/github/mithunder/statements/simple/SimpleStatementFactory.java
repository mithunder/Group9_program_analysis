package com.github.mithunder.statements.simple;

import static com.github.mithunder.statements.StatementType.ABORT;
import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;
import static com.github.mithunder.statements.StatementType.SKIP;
import static com.github.mithunder.statements.StatementType.WRITE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CodeLocation;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementFactory;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;

public class SimpleStatementFactory extends StatementFactory{

	@Override
	public SimpleStatement createCompoundStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, Statement... s) {
		isGroupingStatement(type);
		return new SimpleStatement(type, null, codeLoc, annotations, reorder(type, array2list(s)));
	}

	@Override
	public SimpleStatement createCompoundStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, List<Statement> s) {
		isGroupingStatement(type);
		return new SimpleStatement(type, null, codeLoc, annotations, reorder(type, s));
	}

	@Override
	public SimpleStatement createRootStatement(CodeLocation codeLoc, List<Annotation> annotations, Statement... s) {
		return new SimpleStatement(SCOPE, null, codeLoc, annotations, array2list(s));
	}

	@Override
	public SimpleStatement createRootStatement(CodeLocation codeLoc, List<Annotation> annotations, List<Statement> s) {
		return new SimpleStatement(SCOPE, null, codeLoc, annotations, s);
	}

	@Override
	public SimpleStatement createSimpleStatement(int type, CodeLocation codeLoc, List<Annotation> annotations, Variable assign, Value... v) {
		isSimpleStatement(type);
		return new SimpleStatement(type, assign, codeLoc, annotations, v);
	}

	@Override
	public SimpleStatement createSimpleStatement(int type, CodeLocation codeLoc, List<Annotation> annotations) {
		if(type != ABORT && type != SKIP && type != WRITE) {
			throw new IllegalArgumentException("Simple statement without assignment must be ABORT, SKIP or WRITE.");
		}
		return new SimpleStatement(type, null, codeLoc, annotations);
	}

	private List<Statement> reorder(int type, List<Statement> s){
		final int size;
		final int sh;
		List<Statement> copy;
		if(type == SCOPE) {
			return s;
		}
		size = s.size();
		sh = size/2;
		copy = new ArrayList<Statement>(s.size());
		for(int i = 0 ; i < size ; i++){
			Statement st1;
			if(i >= sh){
				st1 = s.get((i-sh)* 2 + 1);
			} else {
				st1 = s.get(i* 2);
			}
			copy.add(st1);
		}
		return copy;
	}

	private List<Statement> array2list(Statement[] s){
		List<Statement> l = new ArrayList<Statement>(s.length);
		Collections.addAll(l, s);
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
