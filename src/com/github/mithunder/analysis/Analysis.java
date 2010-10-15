package com.github.mithunder.analysis;

import java.util.List;

import com.github.mithunder.statements.AbstractStatement;


public abstract class Analysis {

	public abstract Object evaluate(AbstractStatement statement, List<Object> objs);

	public abstract int compare(Object o, Object object);

	public abstract Object merge(Object object, Object o);

	public abstract Object init(Object o);

}
