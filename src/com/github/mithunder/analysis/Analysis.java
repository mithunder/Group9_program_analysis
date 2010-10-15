package com.github.mithunder.analysis;

import com.github.mithunder.statements.Statement;


public abstract class Analysis {

	public abstract Evaluation evaluate(Statement statement, Evaluation e);

	public abstract int compare(Evaluation e1, Evaluation e2);

	public abstract Evaluation merge(Evaluation e1, Evaluation e2);

	public abstract Evaluation initEvaluation(Statement s);

}
