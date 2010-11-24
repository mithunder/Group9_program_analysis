package com.github.mithunder.analysis;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;

public class ConstantPropagationBranchKiller extends KillRepairAnalysis {

	protected ConstantPropagationAnalysis cp = new ConstantPropagationAnalysis();


	@Override
	public void finishAnalysis(CompilationUnit unit) {
		cp.finishAnalysis(unit);
	}

	@Override
	public Evaluation initEvaluation() {
		return cp.initEvaluation();
	}

	@Override
	public boolean isForwardAnalysis() {
		return cp.isForwardAnalysis();
	}

	@Override
	public Evaluation merge(Evaluation e1, Evaluation e2) {
		return cp.merge(e1, e2);
	}

	@Override
	public void startAnalysis(CompilationUnit unit) {
		cp.startAnalysis(unit);
	}

	@Override
	public boolean canRepair() {
		return false;
	}

	@Override
	public boolean evaluate(EvaluatedStatement s, Evaluation e, int eqv, KillRepairAnalysisWorklist w) {
		return cp.evaluate(s, e, eqv, w);
	}

	@Override
	public Evaluation repairAnalysis(EvaluatedStatement killed, Evaluation e) {
		throw new UnsupportedOperationException("Cannot repair the analysis.");
	}

	@Override
	public void leavingGuard(EvaluatedStatement guard, KillRepairAnalysisWorklist w) {
		EvaluatedStatement last = Statement.finalGuardStatement(guard);
		ConstantPropagationAnalysis.CPAEvaluation eval = (ConstantPropagationAnalysis.CPAEvaluation)last.getExitEvaluation();
		ConstantValue con;
		if(w.isInsideLoop() && !w.isLastVisit()){
			con = null;
			if(last.getStatementType() == StatementType.ASSIGN){
				Value v = last.getValues()[0];
				if(v.isConstant()){
					/* Even inside a loop a constant value will always be a constant value. */
					con = (ConstantValue)v;
				}
			}
		} else {
			/* not in a loop or it is the final visit in the loop */
			con = eval.getConstant(last.getAssign());
		}
		if(con != null && con.getValue() == 0){
			/* Not inside a loop; last statement evaluates to 0 or false - we will never enter the branch */
			w.killBranch();
		}
	}


}
