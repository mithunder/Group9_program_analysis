package com.github.mithunder.statements;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStatement extends Statement {

	// Can be null in some cases (e.g. abort statements)
	protected Variable assign;
	protected List<Annotation> annotations;

	protected CodeLocation cloc;

	protected AbstractStatement(int stype, Variable assign, CodeLocation cloc, List<Annotation> annotations){
		super(stype);
		this.assign = assign;
		this.cloc = cloc;
		if(annotations != null) {
			this.annotations = Collections.unmodifiableList(annotations);
		} else {
			this.annotations = Collections.emptyList();
		}
	}

	public Variable getAssign(){
		return assign;
	}

	public List<Annotation> getAnnotations(){
		return annotations;
	}

	public CodeLocation getCodeLocation(){
		return cloc;
	}

	@Override
	public abstract List<? extends Statement> getChildren();

	@Override
	public abstract Value[] getValues();


}
