package com.github.mithunder.worklist;

import com.github.mithunder.statements.Statement;

public abstract class Worklist {

	public abstract void addToList(Statement s);

	public abstract Statement getNextStatement();
}
