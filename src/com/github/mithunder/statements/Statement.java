package com.github.mithunder.statements;

import java.util.List;

public abstract class Statement {

	protected int stype;

	protected Statement(int stype){
		this.stype = stype;
	}

	public int getStatementType(){
		return stype;
	}

	public abstract List<? extends Statement> getChildren();

	public abstract Value[] getValues();

	public abstract Variable getAssign();

	public abstract List<Annotation> getAnnotations();

	public abstract CodeLocation getCodeLocation();
}
