package com.github.mithunder.analysis;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;


/**
 * <ul>
 * 	<li>{@link StatementType#SKIP} and semantical equivalent statements are not guaranteed to be
 *		be handed to {@link #evaluate(EvaluatedStatement, Evaluation, KillRepairAnalysisWorklist)}.
 *		If they are not passed to the analysis, then their exit value will be equal to their exit value.
 *		</li>
 *	<li>{@link StatementType#ABORT} will not be handled specially.
 *		This <i>may</i> also hold for statements semantically equivalent to
 *		{@link StatementType#ABORT}; this depends on the worklist used. The worklist <i>may</i> in this case
 *		</li>
 * </ul>
 */
public abstract class KillRepairAnalysis {


	/**
	 * Creates an empty evaluation.
	 * @return the evaluation
	 */
	public abstract Evaluation initEvaluation();

	/**
	 * @return true if this is a forward analysis, false if it is a
	 * backwards analysis.
	 */
	public abstract boolean isForwardAnalysis();

	/**
	 * @param killed The statement that (most likely) has been analysed before but has been killed.
	 * @param e The analysis data before the statement. In a do, this will be the
	 * current final evaluation of to do calculated at this point.
	 * 
	 * @note May be invoked on statements that have not been analysed.
	 */
	public abstract Evaluation repairAnalysis(EvaluatedStatement killed, Evaluation e);

	/**
	 * Evaluates a statement given the resulting evaluation of the last evaluated statement
	 * according to the analysis. The analysis <i>must</i> update/set the exit value of
	 * statement, if something has changed. Update of the entry value will be handled by
	 * the worklist.
	 * 
	 * @note that {@link EvaluatedStatement#getEntryEvaluation()} may not return entry. The analysis can
	 * use this to compare the new entry value with the old entry value, assuming that the evaluations
	 * are immutable. Beware that this implies that {@link EvaluatedStatement#getEntryEvaluation()} may
	 * return null the first time the statement is visited.
	 * 
	 * @param statement the statement which is about to be evaluated
	 * @param entry the entry evaluation for the statement
	 * @param semanticEqv The worklist can use this to signal that s is deemed to be semantically equivalent
	 * to semanticEqv. (e.g. an IF with all branches killed => ABORT).
	 * @param w The Worklist used to iterate the current compilation unit.
	 * @return true or false depending on if the evaluated value have been changed
	 * 
	 * @note if semanticEqv and {@link Statement#getStatementType()} are different, then s may be a non-simple
	 * or a compound statement.
	 */
	public abstract boolean evaluate(EvaluatedStatement s, Evaluation entry, int semanticEqv, KillRepairAnalysisWorklist w);

	/**
	 * Merges two evaluations into a new one.
	 * 
	 * @note Either e1 or e2 may be null, but not both (unless the analysis can return
	 * null evaluations, in which case both can be null).
	 * @param e1 the first evaluation
	 * @param e2 the second evaluation
	 * @return A new merged evaluation
	 * @warning The worklist relies on the analysis does <i>not</i> modify e1 nor e2.
	 */
	public abstract Evaluation merge(Evaluation e1, Evaluation e2);

	/**
	 * @return If and only if the analysis can repair the evaluation
	 */
	public abstract boolean canRepair();

	/**
	 * Called when the worklist is leaving a guard.
	 * 
	 * @param guard
	 * @param w
	 */
	public abstract void leavingGuard(EvaluatedStatement guard, KillRepairAnalysisWorklist w);

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
