package com.github.mithunder.statements.simple;

import java.util.List;

import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CodeLocation;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;

public class SimpleStatement extends Statement{

	/* Either children or values are null */
	protected List<SimpleStatement> children;
	protected Value[] values;

	public SimpleStatement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations){
		super(stype, assign, cloc, annotations);
	}

	public SimpleStatement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations, List<SimpleStatement> children){
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
	public List<SimpleStatement> getChildren(){
		return children;
	}
}
