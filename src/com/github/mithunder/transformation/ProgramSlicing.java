package com.github.mithunder.transformation;

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
import com.github.mithunder.rewrite.CodeRewriter;
import com.github.mithunder.rewrite.PurgeDeadCode;
import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.visitor.PrettyCodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.statements.visitor.PrettyCodeWriter.Visitor;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;

//TODO: Not finished!
//TODO: Consider making some local variables fields instead,
//such as variable table, rdStatement, temporaryToStatement, etc.
public class ProgramSlicing extends CodeRewriter {

	private final Set<Statement> includedStatements =
		new HashSet<Statement>();
	private final KillRepairAnalysisWorklist workList;
	public static final String programAnalysisAnnoName = "programanalysis";
	public static final String trueString = "true";

	private void clear() {
		includedStatements.clear();
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
		final Map<EvaluatedStatement, EvaluatedStatement> childToParent =
			new HashMap<EvaluatedStatement, EvaluatedStatement>();
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
		final Map<Statement, EvaluatedStatement> statementToEvaluated =
			new HashMap<Statement, EvaluatedStatement>();
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				statementToEvaluated.put(s.getStatement(), s);
			}
		});
		//Go through the program and find all the statements with the annotation:
		//programslicing="true".
		//These will be included in the program slicing,
		//and will be the starting points for the transformation.
		final Queue<EvaluatedStatement> investigationQueue = new LinkedList<EvaluatedStatement>();
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				for (final Annotation anno : s.getAnnotations()) {
					if (anno.getName().equalsIgnoreCase(programAnalysisAnnoName)
							&& anno.getValue().equalsIgnoreCase(trueString)) {
						testIncludeStatement(s, investigationQueue, childToParent);
						break;
					}
				}
			}
		});
		//If no statements are to be investigated, throw an exception.
		if (includedStatements.isEmpty()) {
			throw new IllegalStateException("Error: no annotation on the form [" +
					programAnalysisAnnoName + "=" + trueString + "] was found."
			);
		}
		//Go through all statements, and add them if they are temporary variables.
		final Map<Variable, EvaluatedStatement> tempVarToStatement =
			new HashMap<Variable, EvaluatedStatement>();
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
		//
		//Get the used variables in the current statement,
		//and get the statements they are defined from using the RD-analysis.
		//For each statement not already included, mark the statement
		//as included, and put it on the investigation queue.
		//Furthermore, get all loops/ifs the current statement is a child in,
		//and process those loops/ifs too if they have not already been included.
		while (!investigationQueue.isEmpty()) {

			final EvaluatedStatement currentStatement = investigationQueue.poll();

			//Handle the variables used in the current statement.
			final ReachingDefinitionEvaluation rdEvaluation =
				(ReachingDefinitionEvaluation)
				(currentStatement).getEntryEvaluation();
			//Find those variables that are read,
			//and add them to the queue.
			for (final Variable var :
					getVariablesRead(
							currentStatement, childToParent, tempVarToStatement, variableTable,
							includedStatements
					)) {
				System.out.println("Var was temp?: " + variableTable.isTemporaryVariable(var));
				final Collection<Statement> reachingStatements = rdEvaluation.getMap().get(var);
				for (final Statement statement : reachingStatements) {
					testIncludeStatement(
						statementToEvaluated.get(statement), investigationQueue,
						childToParent
					);
				}
			}

			//Handle the loops/ifs/scopes the current statement is contained in.
			//Note that order is important!
			EvaluatedStatement currentParent = childToParent.get(currentStatement);
			while (currentParent != null) {
				testIncludeStatement(currentParent, investigationQueue, childToParent);
				currentParent = childToParent.get(currentParent);

			}
		}

		//Print.
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				if (includedStatements.contains(s)) {
					System.out.println("Inc.: " + s.getCodeLocation() + ", " + s.getStatementType());
				}
			}
		});



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
		//Debug print.
		StatementIterator staIte = new StatementIterator(new PrettyCodeWriter());
		try {
			staIte.tour(newCompilationUnit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Clean up the dead code and return.
		return (new PurgeDeadCode()).rewrite(newCompilationUnit);
	}

	private final Collection<Variable> getVariablesRead(final EvaluatedStatement s,
			final Map<EvaluatedStatement, EvaluatedStatement> childToParent,
			final Map<Variable, EvaluatedStatement> tempVarToStatement,
			final VariableTable variableTable, final Set<Statement> includedStatements) {

		final Collection<Variable> variablesRead = new ArrayList<Variable>();
		includedStatements.add(s);

		final Value[] values = s.getValues();
		//If a statement has values, it has reads. So skip if there is no values.
		if (values == null || values.length == 0) {
			return variablesRead;
		}

		final int sType = s.getStatementType();

		//Handle unary expressions.
		if (StatementType.isUnary(sType) && sType != StatementType.ASSIGN) {
			final Value val = s.getValues()[0];
			final EvaluatedStatement refS = tempVarToStatement.get(val);
			addVariables(variablesRead, variableTable, val);
			if (refS != null) {
				getVariablesRead(refS, childToParent, tempVarToStatement,
						variableTable, includedStatements
				);
			}
		}
		//Handle binary expressions.
		else if (StatementType.isBinary(sType)) {
			final Value val1 = s.getValues()[0];
			final Value val2 = s.getValues()[1];
			final EvaluatedStatement refS1 = tempVarToStatement.get(val1);
			final EvaluatedStatement refS2 = tempVarToStatement.get(val2);
			addVariables(variablesRead, variableTable, val1, val2);
			if (refS1 != null) {
				getVariablesRead(refS1, childToParent, tempVarToStatement, variableTable,
						includedStatements
				);
			}
			if (refS2 != null) {
				getVariablesRead(refS2, childToParent, tempVarToStatement, variableTable,
						includedStatements
				);
			}
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

	private final void testIncludeStatement(final EvaluatedStatement evalStatement,
			final Queue<EvaluatedStatement> investigationQueue,
			final Map<EvaluatedStatement, EvaluatedStatement> childToParent) {

		//We include the statement if we haven't investigated it before.
		if (evalStatement != null && !includedStatements.contains(evalStatement)) {
			//Add the statement.
			includedStatements.add(evalStatement);
			investigationQueue.add(evalStatement);

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
					testIncludeStatement(child, investigationQueue, childToParent);
					//Remember to include not just the scope, but also the test.
					testIncludeStatement(
							child.getChildren().get(child.getChildCount()-1),
							investigationQueue, childToParent
					);
					childCount++;
					if (childCount >= childHalfCount) {
						break;
					}
				}
				break;
			}
			case SCOPE : {
				//If we are in a scope, we might be a part of
				//a guard-command pair in a "do".
				//If that is the case, remember to add our guard.
				final EvaluatedStatement parent = childToParent.get(evalStatement);
				if (parent != null && parent.getStatementType() == DO) {

					System.out.println(evalStatement.getCodeLocation() + ", " + evalStatement.getStatementType());

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
						testIncludeStatement(
								guardScope,
								investigationQueue,childToParent
						);
						//Include not just the scope, but also the test.
						final EvaluatedStatement guardTest =
							guardScope.getChildren().get(guardScope.getChildCount()-1);
						testIncludeStatement(
								guardTest,
								investigationQueue,childToParent
						);
					}
					break;
				}
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
