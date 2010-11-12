package com.github.mithunder.runner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.github.mithunder.analysis.Analysis;
import com.github.mithunder.analysis.ReachingDefinitionAnalysis;
import com.github.mithunder.parser.GuardCommandLexer;
import com.github.mithunder.parser.GuardCommandParser;
import com.github.mithunder.parser.GuardCommandParser.program_return;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.visitor.CodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.worklist.RoundRobinWorklist;
import com.github.mithunder.worklist.Worklist;

public class PrintRunner {
	
	private enum Options {PRINT, RD};

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
			boolean recognized = false;
			for (Options o : Options.values()) {
				if (o.ordinal() == Integer.parseInt(option)) {
					recognized = true;
					chosenOption = o;
				}
			}
			if (!recognized) {
				System.out.println("Didn't recognized option: " + option);
				System.exit(0);
			}
			
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}

		if (fileName.equals("")) {
			fileName = "test_program_simple.cmd";
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
			case RD : {
				Analysis ana = new ReachingDefinitionAnalysis();
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
