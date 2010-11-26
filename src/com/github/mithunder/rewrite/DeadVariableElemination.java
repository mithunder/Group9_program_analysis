package com.github.mithunder.rewrite;

import com.github.mithunder.analysis.LiveVariableAnalysis.LiveVariableEvaluation;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.statements.visitor.StatementVisitor;

public class DeadVariableElemination extends CodeRewriter implements StatementVisitor {

	protected VariableTable table;
	@Override
	public CompilationUnit rewrite(CompilationUnit unit) throws IllegalArgumentException {
		StatementIterator it = new StatementIterator(this);
		Statement s = unit.getRootStatement();
		if(!(s instanceof EvaluatedStatement) ||
				!(((EvaluatedStatement)s).getEntryEvaluation() instanceof LiveVariableEvaluation)) {
			throw new IllegalArgumentException("The unit must have been evaluated by LiveVariableAnalysis");
		}
		table = unit.getVariableTable();
		try {
			it.tour(unit);
		} catch(RuntimeException e){
			throw e;
		} catch(Exception e){
			// This class will not throw an exception.
			throw new AssertionError(e);
		}
		return unit;
	}


	@Override
	public void endTour(CompilationUnit unit) {}

	@Override
	public void enter(int vitype, Statement compound, Statement parent, int cno) {}

	@Override
	public void enterCompound(Statement compound, Statement parent, int cno) {}

	@Override
	public void leave(int vitype, Statement compound, Statement parent, int cno) {}

	@Override
	public void leaveCompound(Statement compound, Statement parent, int cno) {}

	@Override
	public void startTour(CompilationUnit unit) {}

	@Override
	public void visitStatement(Statement s, Statement parent, int cno) {
		EvaluatedStatement e = (EvaluatedStatement)s;
		LiveVariableEvaluation data = (LiveVariableEvaluation)e.getEntryEvaluation();
		Variable assign = e.getAssign();
		if(data == null) {
			return;
		}
		/*
		 * Check if we can kill the statement - note we can never kill a read, since it
		 * is a potential abort.
		 */
		if(assign != null && e.getStatementType() != StatementType.READ){
			if(!data.contains(assign)) {
				/*
				 * The LiveVariableAnalysis does not consider the variable live here.
				 * But the assignment value of "guards" are never considered "live" -
				 * so check if one of the operands are live.
				 */
				Value[] values = e.getValues();
				if(values != null) {
					for(Value v : values) {
						if(!v.isConstant() && data.contains((Variable)v)) {
							/* Operand is alive, then so is this statement. */
							return;
						}
					}
				}
				e.killStatement();
			}
		}
	}

}
