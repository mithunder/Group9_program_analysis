package com.github.mithunder.worklist;

import com.github.mithunder.analysis.KillRepairAnalysis;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;


public interface KillRepairAnalysisWorklist {

	/**
	 * @return true if and only if the current node has not been visited before.
	 */
	public abstract boolean isFirstVisit();

	/**
	 * @return true if the current statement is (inside) a loop
	 */
	public abstract boolean isInsideLoop();

	/**
	 * @return true if the current statement is a part of a guard.
	 */
	public abstract boolean isGuard();

	/**
	 * Kills the current statement.
	 * If invoked the analysis <i>must</i> return that the statement has been changed
	 * unless this method returns false. This allows:
	 * <code>
	 * 		return worklist.kill();
	 * </code>
	 * 
	 * @return true if the analysis must return true after invoking this.
	 */
	public abstract boolean kill();

	/**
	 * Kills the current guard and associated branch.
	 * 
	 * Can only be used if {@link #isGuard()} returns true.
	 * If invoked the analysis <i>must</i> return that the branch has been changed
	 * unless this method returns false. This allows:
	 * <code>
	 * 		return worklist.killBranch();
	 * </code>
	 * 
	 * @return true if the analysis must return true after invoking this.
	 */
	public abstract boolean killBranch();

	/**
	 * The method to call for using the selected worklist on some list of statements, using a specific analysis to analyse them
	 * @param analysis the selected analysis
	 * @param unit the compilation unit to run on.
	 * @return a list of statements evaluated according to the analysis
	 */
	public abstract EvaluatedStatement run(KillRepairAnalysis analysis, CompilationUnit unit);

}
