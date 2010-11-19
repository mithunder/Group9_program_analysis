package com.github.mithunder.statements;

import java.util.List;

import com.github.mithunder.analysis.Evaluation;

public final class EvaluatedStatement extends Statement {

	private final Statement statement;
	private Evaluation exitEval;
	private Evaluation entryEval;
	private List<EvaluatedStatement> children;

	public EvaluatedStatement(Statement statement, List<EvaluatedStatement> children) {
		super(statement.getStatementType());
		this.statement = statement;
		this.children = children;
	}

	@Override
	public List<EvaluatedStatement> getChildren() {
		return children;
	}

	@Override
	public int getChildCount() {
		return children != null ? children.size() : 0;
	}

	@Override
	public Value[] getValues() {
		return statement.getValues();
	}

	@Override
	public Variable getAssign() {
		return statement.getAssign();
	}

	@Override
	public List<Annotation> getAnnotations() {
		return statement.getAnnotations();
	}

	@Override
	public CodeLocation getCodeLocation() {
		return statement.getCodeLocation();
	}

	public Statement getStatement() {
		return statement;
	}

	public Evaluation getEntryEvaluation() {
		return entryEval;
	}

	public void setEntryEvaluation(Evaluation e) {
		entryEval = e;
	}

	public Evaluation getExitEvaluation() {
		return exitEval;
	}

	public void setExitEvaluation(Evaluation e) {
		exitEval = e;
	}

	public void setChildren(List<EvaluatedStatement> c) {
		children = c;
	}

	@Override
	public boolean isKilled() {
		return statement.isKilled();
	}

	@Override
	public void killStatement() {
		statement.killStatement();
		for(EvaluatedStatement e : children) {
			e.killStatement();
		}
		exitEval = null;
	}
}
