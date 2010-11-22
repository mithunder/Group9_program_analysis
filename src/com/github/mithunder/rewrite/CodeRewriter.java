package com.github.mithunder.rewrite;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;

public abstract class CodeRewriter {

	/**
	 * Rewrites the compilation unit using some evaluation. The statements in unit must
	 * be {@link EvaluatedStatement} (unless the particular subclass defines otherwise).
	 * 
	 * @note unit <i>may</i> be modified in place; in this case the method must return unit.
	 * 
	 * @param unit
	 * @return Either unit, which has been modified or a modified copy of unit.
	 * @throws IllegalArgumentException
	 */
	public abstract CompilationUnit rewrite(CompilationUnit unit) throws IllegalArgumentException;
}
