package com.github.mithunder.worklist;

import com.github.mithunder.statements.AbstractStatement;

public abstract class Worklist {

	public abstract void addToList(AbstractStatement s);

	public abstract AbstractStatement getNextStatement();
}
