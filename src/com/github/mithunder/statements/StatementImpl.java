package com.github.mithunder.statements;

import java.util.List;

public class StatementImpl extends Statement{

	/* Either children or values are null */
	protected List<StatementImpl> children;
	protected Value[] values;

	public StatementImpl(int stype, Variable assign, List<Annotation> annotations){
		super(stype, assign, annotations);
	}

	public StatementImpl(int stype, Variable assign, List<Annotation> annotations, List<StatementImpl> children){
		this(stype, assign, annotations);
		this.children = children;
	}

	public StatementImpl(int stype, Variable assign, List<Annotation> annotations, Value[] values){
		this(stype, assign, annotations);
		this.values = values;
	}

	@Override
	public Value[] getValues(){
		return values;
	}

	@Override
	public List<StatementImpl> getChildren(){
		return children;
	}
}
