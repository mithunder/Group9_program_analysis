package com.github.mithunder.worklist;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;



public abstract class Worklist {

	/**
	 * The method to call for using the selected worklist on some list of statements, using a specific analysis to analyse them
	 * @param analysis the selected analysis
	 * @param unit the compilation unit to run on.
	 * @return a list of statements evaluated according to the analysis
	 */
	public abstract EvaluatedStatement run(Analysis analysis, CompilationUnit unit);
}
