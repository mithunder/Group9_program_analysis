package com.github.mithunder.rewrite;

import static com.github.mithunder.statements.StatementType.ABORT;
import static com.github.mithunder.statements.StatementType.ASSIGN;

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
		this.unit = unit;
		factory = unit.getFactory();
		table = unit.getVariableTable();
		try {
			it.tour(unit);
		} catch(RuntimeException e){
			throw e;
		} catch(Exception e){
			// This class will not throw an exception.
			throw new AssertionError(e);
		}
		this.unit = null;
		factory = null;
		table = null;
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
		CPAEvaluation data = (CPAEvaluation)e.getExitEvaluation();
		final int stype = e.getStatementType();
		Variable assign;
		Value[] values;
		if(data == null) {
			return;
		}
		if(stype == ABORT) {
			/* Cannot do anything about abort */
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
		assign = e.getAssign();
		values = e.getValues();
		if(assign != null){
			ConstantValue con = data.getConstant(assign);
			if(con != null){
				/* awesome */
				Statement re;
				if(stype == ASSIGN && values[0].isConstant()){
					/* Not quite as awesome after all*/
					return;
				}
				e.killStatement();
				re = factory.createSimpleStatement(ASSIGN, s.getCodeLocation(), s.getAnnotations(), assign, con);
				parent.replaceChild(cno, re);
				return;
			}
		}
		if(values != null){
			/* partial rewrite */
			boolean changed = false;
			Value[] rv = new Value[values.length];
			for(int i = 0 ; i < values.length ; i++) {
				if(!values[i].isConstant()){
					ConstantValue con = data.getConstant((Variable)values[i]);
					if(con != null){
						/* awesome */
						rv[i] = con;
						changed = true;
						continue;
					}
				}
				rv[i] = values[i];
			}
			if(changed){
				Statement re = factory.createSimpleStatement(stype, s.getCodeLocation(), s.getAnnotations(), s.getAssign(), rv);
				e.killStatement();
				parent.replaceChild(cno, re);
			}
		}
	}
}
