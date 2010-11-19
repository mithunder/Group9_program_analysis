package com.github.mithunder.alfp;

import java.util.List;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;

public abstract class ALFP {

	public abstract void convertToALFP(List<EvaluatedStatement> statements, CompilationUnit unit);
}
