package com.github.mithunder.statements;

import java.util.List;

import com.github.mithunder.analysis.Evaluation;

public class EvaluatedStatement extends Statement {

	private final Statement statement;
	private Evaluation evaluation;
	private List<EvaluatedStatement> children;

	public EvaluatedStatement(Statement statement, Evaluation evaluation, List<EvaluatedStatement> children) {
		super(statement.getStatementType());
		this.statement = statement;
		this.evaluation = evaluation;
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

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation e) {
		evaluation = e;
	}
}
