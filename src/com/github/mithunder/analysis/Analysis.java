package com.github.mithunder.analysis;

import com.github.mithunder.statements.Statement;


public abstract class Analysis {

	public abstract Evaluation evaluate(Statement statement, Evaluation e);

	public abstract int compare(Evaluation e1, Evaluation e2);

	public abstract Evaluation merge(Evaluation e1, Evaluation e2);

	public abstract Evaluation initEvaluation(Statement s);

	/**
	 * @return true if this is a forward analysis, false if it is a
	 * backwards analysis.
	 */
	public abstract boolean isForwardAnalysis();

}
