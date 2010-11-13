package com.github.mithunder.worklist;


public interface KillRepairAnalysisWorklist extends Worklist {

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
	 */
	public abstract void kill();

	/**
	 * Kills the current guard and associated branch.
	 * 
	 * Can only be used if {@link #isGuard()} returns true.
	 */
	public abstract void killBranch();

}
