package com.github.mithunder.statements;

import java.util.List;

import com.github.mithunder.analysis.Evaluation;

public abstract class EvaluatedStatement extends Statement {

	private final Statement statement;
	private Evaluation evaluation;
	private List<? extends EvaluatedStatement> children;

	protected EvaluatedStatement(int stype, Statement statement, Evaluation evaluation, List<? extends EvaluatedStatement> children) {
		super(stype);
		this.statement = statement;
		this.evaluation = evaluation;
		this.children = children;
	}

	@Override
	public List<? extends EvaluatedStatement> getChildren() {
		return children;
	}

	@Override
	public abstract Value[] getValues();

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
