package com.github.mithunder.transformation;

import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;

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
import com.github.mithunder.statements.visitor.PrettyCodeWriter;
import com.github.mithunder.statements.visitor.PrettyCodeWriter.Visitor;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;

//TODO: Not finished!
public class ProgramSlicing {

	private final Set<Statement> includedStatements =
		new HashSet<Statement>();
	public static final String programAnalysisAnnoName = "programanalysis";
	public static final String trueString = "true";

	private void clear() {
		includedStatements.clear();
	}

	/**
	 * @param compilationUnit This method is allowed to change the compilation unit.
	 * If it does, it also returns it.
	 * @param workList
	 * @return
	 */
	public CompilationUnit performTransformation(final CompilationUnit compilationUnit,
			final KillRepairAnalysisWorklist workList) {

		clear();

		//Perform reaching definition (RD) analysis, and get the root statement.
		final EvaluatedStatement rdStatement =
			getReachingDefinitionsAnalysis(compilationUnit, workList);

		//Init:
		//Make a map to each parent, simply by going through statements.
		final Map<Statement, Statement> childToParent =
			new HashMap<Statement, Statement>();
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
		//Go through the program and find all the statements with the annotation:
		//programslicing="true".
		//These will be included in the program slicing,
		//and will be the starting points for the transformation.
		final Queue<Statement> investigationQueue = new LinkedList<Statement>();
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<Statement>() {
			public void visitStatement(Statement s) {
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


			final Statement currentStatement = investigationQueue.poll();

			//Handle the variables used in the current statement.
			if (currentStatement instanceof EvaluatedStatement) {

				//Warning: Two casts.
				final ReachingDefinitionEvaluation rdEvaluation =
					(ReachingDefinitionEvaluation)
					((EvaluatedStatement)currentStatement).getExitEvaluation();

				for (final Set<Statement> statements : rdEvaluation.getMap().values()) {
					for (final Statement statement : statements) {
						testIncludeStatement(
							statement, investigationQueue,
							childToParent
						);
					}
				}
			}
			else {
				System.out.println(currentStatement.getCodeLocation().getLineNumber());
			}

			//Handle the loops/ifs/scopes the current statement is contained in.
			//Note that order is important!
			Statement currentParent = childToParent.get(currentStatement);
			while (currentParent != null) {
				System.out.println("Added in parent.");
				System.out.println(currentParent.getCodeLocation().getLineNumber() +
						" " + currentParent.getStatementType());
				testIncludeStatement(currentParent, investigationQueue, childToParent);
				currentParent = childToParent.get(currentParent);

			}
		}

		for (Statement statement : includedStatements) {
			System.out.println(statement.getCodeLocation().getLineNumber() + " " + statement.getStatementType());
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
		//TODO: Clean up the killed code.

		return new CompilationUnit(
				compilationUnit.getUnitName(),
				rdStatement,
				compilationUnit.getVariableTable(),
				compilationUnit.getFinalStatements(),
				compilationUnit.getFactory()
		);
	}

	private final void testIncludeStatement(final Statement evalStatement,
			final Queue<Statement> investigationQueue,
			final Map<Statement, Statement> childToParent) {

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
				for (Statement child : evalStatement.getChildren()) {
					testIncludeStatement(child, investigationQueue, childToParent);
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
				final Statement parent = childToParent.get(evalStatement);
				if (parent != null && parent.getStatementType() == DO) {
					//First, find ourselves.
					int i = 0;
					for (Statement siblings : parent.getChildren()) {
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
					//To find our guard, subtract the halfsize from our index.
					final int guardIndex = i - parent.getChildCount()/2;
					//Include our guard.
					testIncludeStatement(
							parent.getChildren().get(guardIndex),
							investigationQueue,childToParent
					);
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
