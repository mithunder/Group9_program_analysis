package com.github.mithunder.analysis;

import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;


public abstract class KillRepairAnalysis extends Analysis {



	/**
	 * @param killed The statement that (most likely) has been analysed before but has been killed.
	 * @param e The analysis data before the statement. In a do, this will be the
	 * current final evaluation of to do calculated at this point.
	 * 
	 * @note May be invoked on statements that have not been analysed.
	 */
	public abstract Evaluation repairAnalysis(EvaluatedStatement killed, Evaluation e);


	public abstract boolean evaluate(EvaluatedStatement s, Evaluation e, KillRepairAnalysisWorklist w);
	public abstract boolean evaluateCondition(EvaluatedStatement condition, EvaluatedStatement statement, Evaluation e, KillRepairAnalysisWorklist w);

	/**
	 * @return If and only if the analysis can repair the evaluation
	 */
	public abstract boolean canRepair();

	public abstract void leavingGuard(EvaluatedStatement guard, KillRepairAnalysisWorklist w);

}
