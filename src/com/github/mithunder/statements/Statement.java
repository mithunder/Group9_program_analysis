package com.github.mithunder.statements;

import java.util.Collections;
import java.util.List;

public abstract class Statement {

	protected int stype;
	// Can be null in some cases (e.g. abort statements)
	protected Variable assign;
	protected List<Annotation> annotations;

	protected Statement(int stype, Variable assign, List<Annotation> annotations){
		this.stype = stype;
		this.assign = assign;
		if(annotations != null) {
			this.annotations = Collections.unmodifiableList(annotations);
		} else {
			this.annotations = Collections.emptyList();
		}
	}

	public int getStatementType(){
		return stype;
	}

	public Variable getAssign(){
		return assign;
	}

	public List<Annotation> getAnnotations(){
		return annotations;
	}

	public abstract List<? extends Statement> getChildren();

	public abstract Value[] getValues();
}
