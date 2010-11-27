package com.github.mithunder.rewrite;

import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.github.mithunder.analysis.ReachingDefinitionAnalysis;
import com.github.mithunder.analysis.ReachingDefinitionAnalysis.ReachingDefinitionEvaluation;
import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.visitor.PrettyCodeWriter;
import com.github.mithunder.statements.visitor.PrettyCodeWriter.Visitor;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;

public class ProgramSlicing extends CodeRewriter {

	//State.
	private final Set<Statement> includedStatements =
		new HashSet<Statement>();
	private final KillRepairAnalysisWorklist workList;
	private final Queue<EvaluatedStatement> investigationQueue = new LinkedList<EvaluatedStatement>();
	private final Map<EvaluatedStatement, EvaluatedStatement> childToParent =
		new HashMap<EvaluatedStatement, EvaluatedStatement>();
	private final Map<Statement, EvaluatedStatement> statementToEvaluated =
		new HashMap<Statement, EvaluatedStatement>();
	private final Map<Variable, EvaluatedStatement> tempVarToStatement =
		new HashMap<Variable, EvaluatedStatement>();

	public static final String programSlicingAnnoName = "programslicing";
	public static final String trueString = "true";

	private void clear() {
		includedStatements.clear();
		investigationQueue.clear();
		childToParent.clear();
		statementToEvaluated.clear();
		tempVarToStatement.clear();
	}

	public ProgramSlicing(final KillRepairAnalysisWorklist workList) {
		this.workList = workList;
	}

	/**
	 * @param compilationUnit This method is allowed to change the compilation unit.
	 * If it does, it also returns it.
	 * @param workList
	 * @return
	 */
	@Override
	public CompilationUnit rewrite(final CompilationUnit compilationUnit) {

		clear();

		//Perform reaching definition (RD) analysis, and get the root statement.
		final EvaluatedStatement rdStatement =
			getReachingDefinitionsAnalysis(compilationUnit, workList);
		final VariableTable variableTable = compilationUnit.getVariableTable();

		//Init:
		//Make a map to each parent, simply by going through statements.
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				if (s.getChildCount() != 0) {
					for (EvaluatedStatement child : s.getChildren()) {
						if (child != null && s != null) {
							childToParent.put(child, s);
						}
					}
				}
			}
		});
		//Make a map to each evaluated statement, simply by going through statements.
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				statementToEvaluated.put(s.getStatement(), s);
			}
		});
		//Go through the program and find all the statements with the annotation:
		//programslicing="true".
		//These will be included in the program slicing,
		//and will be the starting points for the transformation.
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				for (final Annotation anno : s.getAnnotations()) {
					if (anno.getName().equalsIgnoreCase(programSlicingAnnoName)
							&& anno.getValue().equalsIgnoreCase(trueString)) {
						addToInvestigation(s);
						break;
					}
				}
			}
		});
		//If no statements are to be investigated, throw an exception.
		if (investigationQueue.isEmpty()) {
			throw new IllegalStateException("Error: no annotation on the form [" +
					programSlicingAnnoName + "=" + trueString + "] was found."
			);
		}
		//Go through all statements, and add them if they are temporary variables.
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>(){
			public void visitStatement(EvaluatedStatement s) {
				if (s.getAssign() != null &&
						variableTable.isTemporaryVariable(s.getAssign())) {
					tempVarToStatement.put(s.getAssign(), s);
				}
			}
		});



		//Main:
		//Repeatedly retrieved the head of the queue until it is empty.
		//The retrieved statement is the "current statement".
		//For each statement on the investigation queue,
		//if it has not been added, add it and add all
		//those statements it references somehow on the investigation queue
		//(if they have not already been investigated).
		//
		//First perform special handling of the statement if necessary.
		//This includes handling of if and scope.
		//
		//Then get the used variables in the current statement,
		//and get the statements they are defined from using the RD-analysis.
		//
		//Finally, get all loops/ifs the current statement is a child in.
		while (!investigationQueue.isEmpty()) {

			final EvaluatedStatement currentStatement = investigationQueue.poll();
			if (includedStatements.contains(currentStatement)) {
				continue;
			}

			//Handle the special statement types.
			handleSpecialStatementTypes(currentStatement);
			includedStatements.add(currentStatement);

			//Handle the variables used in the current statement.
			final ReachingDefinitionEvaluation rdEvaluation =
				(ReachingDefinitionEvaluation)
				(currentStatement).getEntryEvaluation();
			//Find those variables that are read,
			//and add them to the queue.
			for (final Variable var :
					getVariablesRead(
							currentStatement, variableTable
					)) {
				final Collection<Statement> reachingStatements = rdEvaluation.getMap().get(var);
				for (final Statement statement : reachingStatements) {
					addToInvestigation(statementToEvaluated.get(statement));
				}
			}

			//Handle the loops/ifs/scopes the current statement is contained in.
			//Note that order is important!
			EvaluatedStatement currentParent = childToParent.get(currentStatement);
			while (currentParent != null) {
				addToInvestigation(currentParent);
				currentParent = childToParent.get(currentParent);
			}
		}



		//Final: Construct a new program based on the statements that have been included.
		//Do this by killing statements that have not been included.
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				if (!includedStatements.contains(s)) {
					s.killStatement();
				}
			}
		});
		final CompilationUnit newCompilationUnit = new CompilationUnit(
				compilationUnit.getUnitName(),
				rdStatement,
				compilationUnit.getVariableTable(),
				compilationUnit.getFinalStatements(),
				compilationUnit.getFactory()
		);
		//Clean up the dead code and return.
		return (new PurgeDeadCode()).rewrite(newCompilationUnit);
	}

	private final Collection<Variable> getVariablesRead(final EvaluatedStatement s,
			final VariableTable variableTable) {

		final Collection<Variable> variablesRead = new ArrayList<Variable>();
		addToInvestigation(s);

		final Value[] values = s.getValues();
		//If a statement has values, it has reads. So skip if there is no values.
		if (values == null || values.length == 0) {
			return variablesRead;
		}

		final int sType = s.getStatementType();

		//Handle unary expressions.
		if (StatementType.isUnary(sType) && sType != StatementType.ASSIGN) {
			System.out.println("St_type: " + sType);
			final Value val = s.getValues()[0];
			final EvaluatedStatement refS = tempVarToStatement.get(val);
			System.out.println(val + ", ref: " + refS);
			addVariables(variablesRead, variableTable, val);
			addToInvestigation(refS);
		}
		//Handle binary expressions.
		else if (StatementType.isBinary(sType)) {
			final Value val1 = s.getValues()[0];
			final Value val2 = s.getValues()[1];
			final EvaluatedStatement refS1 = tempVarToStatement.get(val1);
			final EvaluatedStatement refS2 = tempVarToStatement.get(val2);
			addVariables(variablesRead, variableTable, val1, val2);
			addToInvestigation(refS1);
			addToInvestigation(refS2);
		}
		//Handle assign.
		else {
			addVariables(variablesRead, variableTable, s.getValues()[0]);
		}

		return variablesRead;
	}

	private final void addVariables(final Collection<Variable> variablesRead,
			final VariableTable variableTable, final Value ... vals) {
		for (final Value val : vals) {
			if (val != null && val instanceof Variable) {
				final Variable var = (Variable) val;
				if (!variableTable.isTemporaryVariable(var)) {
					variablesRead.add(var);
				}
			}
		}
	}

	private final void addToInvestigation(final EvaluatedStatement evalStatement) {
		if (evalStatement != null && !includedStatements.contains(evalStatement)) {
			investigationQueue.add(evalStatement);
		}
	}


	private final void handleSpecialStatementTypes(final EvaluatedStatement evalStatement) {

		final int statType = evalStatement.getStatementType();
		switch (statType) {
		case IF : {
			//Special handling:
			//If we have an "if", we must include all guards at the very least.
			//If we have a do, we let the adding be handled by the scope itself.
			int childCount = 0;
			final int childHalfCount = evalStatement.getChildCount()/2;
			for (EvaluatedStatement child : evalStatement.getChildren()) {
				//Include the scope.
				addToInvestigation(child);
				//Remember to include not just the scope, but also the test.
				addToInvestigation(child.getChildren().get(child.getChildCount()-1));
				childCount++;
				if (childCount >= childHalfCount) {
					break;
				}
			}
			break;
		}
		//If we are in a scope, we might be a part of
		//a guard-command pair in a "do".
		//If that is the case, remember to add our guard.
		case SCOPE : {
			final EvaluatedStatement parent = childToParent.get(evalStatement);
			if (parent != null && parent.getStatementType() == DO) {

				//First, find ourselves.
				int i = 0;
				for (EvaluatedStatement siblings : parent.getChildren()) {
					if (siblings == evalStatement) {
						//We have found ourselves.
						break;
					}
					i++;
				}
				//Ensure sanity.
				if (i == parent.getChildCount()) {
					throw new IllegalStateException("We did not find ourselves!");
				}
				//To find our potential, subtract the halfsize from our index.
				final int guardIndex = i - parent.getChildCount()/2;
				//Include our guard only if we are a command.
				System.out.println("Guard: " + guardIndex);
				if (guardIndex >= 0) {
					final EvaluatedStatement guardScope = parent.getChildren().get(guardIndex);
					addToInvestigation(guardScope);

					//Include not just the scope, but also the test.
					final EvaluatedStatement guardTest =
						guardScope.getChildren().get(guardScope.getChildCount()-1);
					addToInvestigation(guardTest);
				}
				break;
			}
		}
		}
	}

	private EvaluatedStatement getReachingDefinitionsAnalysis(CompilationUnit compilationUnit,
			KillRepairAnalysisWorklist workList) {

		final ReachingDefinitionAnalysis analysis = new ReachingDefinitionAnalysis();

		return workList.run(analysis, compilationUnit);
	}
}
