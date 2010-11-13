package com.github.mithunder.chains;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.statements.Statement;

public abstract class Chain {

	public abstract void createChain(Statement root, Analysis ana);
}
