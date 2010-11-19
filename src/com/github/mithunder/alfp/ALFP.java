package com.github.mithunder.alfp;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;

public abstract class ALFP {

	public abstract void convertToALFP(EvaluatedStatement statement, CompilationUnit unit);
}
