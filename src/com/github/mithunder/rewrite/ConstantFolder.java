package com.github.mithunder.rewrite;

import static com.github.mithunder.statements.StatementType.ASSIGN;
import static com.github.mithunder.statements.StatementType.WRITE;

import com.github.mithunder.analysis.ConstantPropagationAnalysis.CPAEvaluation;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementFactory;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.statements.visitor.StatementVisitor;

public class ConstantFolder extends CodeRewriter implements StatementVisitor {

	protected CompilationUnit unit;
	protected StatementFactory factory;
	protected VariableTable table;

	@Override
	public CompilationUnit rewrite(CompilationUnit unit) throws IllegalArgumentException {
		StatementIterator it = new StatementIterator(this);
		Statement s = unit.getRootStatement();
		if(!(s instanceof EvaluatedStatement) ||
				!(((EvaluatedStatement)s).getEntryEvaluation() instanceof CPAEvaluation)) {
			throw new IllegalArgumentException("The unit must have been evaluated by ConstantPropagationAnalysis");
		}
		synchronized (this) {
			if(unit == null) {
				throw new IllegalStateException("Already rewriting a unit.");
			}
			this.unit = unit;
			factory = unit.getFactory();
			table = unit.getVariableTable();
		}
		try {
			it.tour(unit);
		} catch(RuntimeException e){
			throw e;
		} catch(Exception e){
			// This class will not throw an exception.
			throw new AssertionError(e);
		}
		synchronized (this) {
			this.unit = null;
			factory = null;
			table = null;
		}
		return unit;
	}

	@Override
	public void endTour(CompilationUnit unit) throws Exception {}

	@Override
	public void enter(int vitype, Statement compound, Statement parent, int cno) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterCompound(Statement compound, Statement parent, int cno) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void leave(int vitype, Statement compound, Statement parent, int cno) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveCompound(Statement compound, Statement parent, int cno) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTour(CompilationUnit unit) throws Exception {}

	@Override
	public void visitStatement(Statement s, Statement parent, int cno) throws Exception {
		EvaluatedStatement e = (EvaluatedStatement)s;
		CPAEvaluation data = (CPAEvaluation)e.getExitEvaluation();
		final int stype = e.getStatementType();
		Variable assign = e.getAssign();
		if(data == null) {
			return;
		}
		if(data.isAbortEvaluation()) {
			/*
			 * A forward *MUST* analysis claims this statement always
			 * follows an abort statement, regardless of the path taken
			 * to it... That sounds like it is dead.
			 */
			s.killStatement();
			return;
		}
		if(assign != null){
			ConstantValue con;
			if(table.isTemporaryVariable(assign)) {
				/* Not interested in temporary variables */
				return;
			}
			con = data.getConstant(assign);
			if(con != null){
				/* awesome */
				Statement re;
				if(stype == ASSIGN && e.getValues()[0].isConstant()){
					/* Not quite as awesome after all*/
					return;
				}
				e.killStatement();
				re = factory.createSimpleStatement(ASSIGN, s.getCodeLocation(), s.getAnnotations(), assign, con);
				parent.replaceChild(cno, re);
			}
		} else if(stype == WRITE){
			Value v = e.getValues()[0];
			if(!v.isConstant()){
				ConstantValue con = data.getConstant((Variable)v);
				if(con != null){
					/* awesome */
					Statement re = factory.createSimpleStatement(WRITE, s.getCodeLocation(), s.getAnnotations(), null, con);
					e.killStatement();
					parent.replaceChild(cno, re);
				}
			}
		}
	}

}
