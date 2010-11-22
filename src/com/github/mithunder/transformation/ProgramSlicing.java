package com.github.mithunder.transformation;

import java.util.HashSet;
import java.util.LinkedList;
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

	private final Set<EvaluatedStatement> includedStatements =
		new HashSet<EvaluatedStatement>();
	public static final String programAnalysisAnnoName = "programanalysis";
	public static final String trueString = "true";

	private void clear() {
		includedStatements.clear();
	}

	public CompilationUnit performTransformation(CompilationUnit compilationUnit,
			KillRepairAnalysisWorklist workList) {

		//TODO:
		if (true) {
			throw new UnsupportedOperationException("This method has not been implemented!");
		}

		clear();

		//Perform reaching definition (RD) analysis, and get the root statement.
		EvaluatedStatement rdStatement =
			getReachingDefinitionsAnalysis(compilationUnit, workList);

		//Init: Go through the program and find all the statements with the annotation:
		//programslicing="true".
		//These will be included in the program slicing,
		//and will be the starting points for the transformation.
		final Queue<EvaluatedStatement> investigationQueue = new LinkedList<EvaluatedStatement>();
		PrettyCodeWriter.walkStatements(rdStatement, new Visitor<EvaluatedStatement>() {
			public void visitStatement(EvaluatedStatement s) {
				for (final Annotation anno : s.getAnnotations()) {
					if (anno.getName().equalsIgnoreCase(programAnalysisAnnoName)
							&& anno.getValue().equalsIgnoreCase(trueString)) {
						testIncludeStatement(s);
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
		//Furthermore, get all loops the current statement is a child in,
		//and process those loops too if they have not already been included.
		while (!investigationQueue.isEmpty()) {
			final EvaluatedStatement currentStatement = investigationQueue.poll();

			//Handle the variables used in the current statement.
			final ReachingDefinitionEvaluation rdEvaluation =
				(ReachingDefinitionEvaluation) currentStatement.getExitEvaluation();
			for (final Set<Statement> statements : rdEvaluation.getMap().values()) {
				for (final Statement statement : statements) {
					testIncludeStatement((EvaluatedStatement)statement);
				}
			}

			//Handle the loops the current statement is contained in.
			//TODO: Implement.
		}

		//Final: Construct a new program based on the statements that have been included.


		//TODO: Implement.
		return null;
	}

	private final void testIncludeStatement(EvaluatedStatement evalStatement) {
		//We include the statement if we haven't investigated it before.
		if (!includedStatements.contains(evalStatement)) {
			//
		}
	}

	private EvaluatedStatement getReachingDefinitionsAnalysis(CompilationUnit compilationUnit,
			KillRepairAnalysisWorklist workList) {

		final ReachingDefinitionAnalysis analysis = new ReachingDefinitionAnalysis();

		return workList.run(analysis, compilationUnit);
	}
}
