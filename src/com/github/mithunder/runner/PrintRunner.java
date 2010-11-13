package com.github.mithunder.runner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.analysis.ConstantPropagationAnalysis;
import com.github.mithunder.analysis.ConstantPropagationBranchKiller;
import com.github.mithunder.analysis.LiveVariableAnalysis;
import com.github.mithunder.analysis.ReachingDefinitionAnalysis;
import com.github.mithunder.parser.GuardCommandLexer;
import com.github.mithunder.parser.GuardCommandParser;
import com.github.mithunder.parser.GuardCommandParser.program_return;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.visitor.CodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.worklist.KRARoundRobinWorklist;
import com.github.mithunder.worklist.RoundRobinWorklist;
import com.github.mithunder.worklist.Worklist;

public class PrintRunner {

	private enum Options {PRINT, RD, LV, CP, CPBK };

	public static void main(String[] args) throws Exception {

		String fileName;
		Options chosenOption = null;
		BufferedReader reader = null;
		try {
			System.out.println("Please input the name of the program (enter for default):");

			reader = new BufferedReader(new InputStreamReader(System.in));
			fileName = reader.readLine();

			System.out.println("Please choose an option:");
			for (Options o : Options.values()) {
				System.out.println(o.ordinal() + ": " + o.toString());
			}
			String option = reader.readLine();
			chosenOption = Options.values()[Integer.parseInt(option)];

		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}

		if (fileName.equals("")) {
			fileName = "test_program_simple2.cmd";
		}
		GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(fileName));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		GuardCommandParser parser = new GuardCommandParser(tokens);

		CompilationUnit unit;

		try {

			program_return program_r = parser.program();
			unit = program_r.compilationUnit;

			switch (chosenOption) {
			case PRINT : {
				StatementIterator staIte = new StatementIterator(new CodeWriter());
				staIte.tour(unit);
				break;
			}
			default : {
				Analysis ana;
				Worklist wl = new RoundRobinWorklist();
				switch(chosenOption){
				case RD: ana = new ReachingDefinitionAnalysis(); break;
				case LV: ana = new LiveVariableAnalysis();  wl = new KRARoundRobinWorklist(); break;
				case CP: ana = new ConstantPropagationAnalysis(); break;
				case CPBK: ana = new ConstantPropagationBranchKiller(); wl = new KRARoundRobinWorklist(); break;
				default: throw new AssertionError();
				}

				StatementIterator staIte = new StatementIterator(new CodeWriter());
				System.out.println("Starting analysis");
				long st = System.currentTimeMillis();
				EvaluatedStatement nroot = wl.run(ana, unit);
				System.out.println("Finished analysis, time: " + (System.currentTimeMillis() - st) + "ms.");
				staIte.tour(new CompilationUnit(unit.getUnitName(), nroot, unit.getVariableTable(), unit.getFinalStatements()));
				break;
			}
			case LV : {
				Analysis ana = new LiveVariableAnalysis();
				Worklist wl = new RoundRobinWorklist();
				StatementIterator staIte = new StatementIterator(new CodeWriter());
				System.out.println("Starting analysis");
				long st = System.currentTimeMillis();
				EvaluatedStatement nroot = wl.run(ana, unit);
				System.out.println("Finished analysis, time: " + (System.currentTimeMillis() - st) + "ms.");
				staIte.tour(new CompilationUnit(unit.getUnitName(), nroot, unit.getVariableTable(), unit.getFinalStatements()));
				break;
			}
			}

		} catch (RecognitionException e)  {
			e.printStackTrace();
		}
	}
}
