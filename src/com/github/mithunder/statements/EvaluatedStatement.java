package com.github.mithunder.statements;

import java.util.ArrayList;
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

	private List<EvaluatedStatement> eval(List<? extends Statement> children){
		final int size;
		List<EvaluatedStatement> e;
		if(children == null) {
			return new ArrayList<EvaluatedStatement>();
		}
		size = children.size();
		e = new ArrayList<EvaluatedStatement>(size);
		for(int i = 0 ; i < size ; i++){
			Statement s = children.get(i);
			e.add(new EvaluatedStatement(s, eval(s.getChildren())));
		}
		return e;
	}

	@Override
	public void replaceChild(int i, Statement replacement) {
		Statement sr;
		EvaluatedStatement er;
		if(replacement instanceof EvaluatedStatement) {
			er = (EvaluatedStatement)replacement;
			sr = er.getStatement();
		} else {
			sr = replacement;
			er = new EvaluatedStatement(sr, eval(sr.getChildren()));
		}
		statement.replaceChild(i, sr);
		children.set(i, er);
	}
}
