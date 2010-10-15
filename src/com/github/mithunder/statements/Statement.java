package com.github.mithunder.statements;

import java.util.List;

public abstract class Statement extends AbstractStatement {

	protected int stype;

	protected Statement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations){
		super(assign, cloc, annotations);
		this.stype = stype;
	}

	public int getStatementType(){
		return stype;
	}

	@Override
	public abstract List<? extends AbstractStatement> getChildren();

	@Override
	public abstract Value[] getValues();

}
