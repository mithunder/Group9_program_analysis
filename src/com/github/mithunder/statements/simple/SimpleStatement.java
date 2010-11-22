package com.github.mithunder.statements.simple;

import java.util.List;

import com.github.mithunder.statements.AbstractStatement;
import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CodeLocation;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;

public class SimpleStatement extends AbstractStatement implements Comparable<SimpleStatement>{

	/* Either children or values are null */
	protected List<Statement> children;
	protected Value[] values;

	public SimpleStatement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations){
		super(stype, assign, cloc, annotations);
	}

	public SimpleStatement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations, List<Statement> children){
		this(stype, assign, cloc, annotations);
		this.children = children;
	}

	public SimpleStatement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations, Value[] values){
		this(stype, assign, cloc, annotations);
		this.values = values;
	}

	@Override
	public Value[] getValues(){
		return values;
	}

	@Override
	public List<Statement> getChildren(){
		return children;
	}

	@Override
	public int getChildCount() {
		return children != null ? children.size() : 0;
	}

	@Override
	public int compareTo(SimpleStatement arg0) {
		return System.identityHashCode(this) - System.identityHashCode(arg0);
	}

	@Override
	public void replaceChild(int i, Statement replacement){
		children.set(i, replacement);
	}
}
