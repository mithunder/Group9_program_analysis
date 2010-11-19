package com.github.mithunder.chains;

import com.github.mithunder.analysis.KillRepairAnalysis;
import com.github.mithunder.statements.Statement;

public abstract class Chain {

	public abstract void createChain(Statement root, KillRepairAnalysis ana);
}
