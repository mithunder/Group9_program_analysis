package com.github.mithunder.analysis;

import com.github.mithunder.statements.CompilationUnit;
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

	/**
	 * Start the analysis (called before any statement is reviewed).
	 * @param unit The CompilationUnit that will be examined.
	 */
	public abstract void startAnalysis(CompilationUnit unit);

	/**
	 * Notify the Analysis that the last statement has been evaluated.
	 * @param unit The CompilationUnit that was examined
	 */
	public abstract void finishAnalysis(CompilationUnit unit);
}
