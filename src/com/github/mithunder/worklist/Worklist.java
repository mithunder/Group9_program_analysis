package com.github.mithunder.worklist;

import java.util.List;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;


public abstract class Worklist {

	/**
	 * The method to call for using the selected worklist on some list of statements, using a specific analysis to analyse them
	 * @param analysis the selected analysis
	 * @param ss a list of statements
	 * @return a list of statements evaluated according to the analysis
	 */
	public abstract List<EvaluatedStatement> run(Analysis analysis, List<? extends Statement> ss);
}
