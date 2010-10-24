package com.github.mithunder.analysis;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.Statement;


public abstract class Analysis {

	/**
	 * Evaluates a statement given the resulting evaluation of the last evaluated statement according to the analysis
	 * @param statement the statement which is about to be evaluated
	 * @param e the entry evaluation for the statement
	 * @return true or false depending on if the evaluated value have been changed
	 */
	public abstract boolean evaluate(Statement statement, Evaluation e);

	/**
	 * Merges two evaluations into one
	 * @param e1 the first evaluation
	 * @param e2 the second evaluation
	 * @return the merged evaluation
	 */
	public abstract Evaluation merge(Evaluation e1, Evaluation e2);

	/**
	 * Initiates the chain of evaluations, should only be used on the very first statement
	 * @param s the first statement
	 * @return the evaluation
	 */
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
