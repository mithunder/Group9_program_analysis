package com.github.mithunder.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStreamReader;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.github.mithunder.alfp.ALFPReachingDefinition;
import com.github.mithunder.analysis.ConstantPropagationAnalysis;
import com.github.mithunder.analysis.ConstantPropagationBranchKiller;
import com.github.mithunder.analysis.KillRepairAnalysis;
import com.github.mithunder.analysis.LiveVariableAnalysis;
import com.github.mithunder.analysis.ReachingDefinitionAnalysis;
import com.github.mithunder.parser.GuardCommandLexer;
import com.github.mithunder.parser.GuardCommandParser;
import com.github.mithunder.parser.GuardCommandParser.program_return;
import com.github.mithunder.rewrite.ConstantFolder;
import com.github.mithunder.rewrite.DeadVariableElemination;
import com.github.mithunder.rewrite.ProgramSlicing;
import com.github.mithunder.rewrite.PurgeDeadCode;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.visitor.PrettyCodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;
import com.github.mithunder.worklist.SimpleRRKRWorklist;

public class PrintRunner {

	private enum Options {PRINT, RD, LV, CP, CPBK, ALFPRD, PS, CPBKandLV };

	public static void main(String[] args) throws Exception {

		String fileName;
		Options chosenOption = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Run and print analysis, 0. Run tests, 1");
			String printTestChoiceString = reader.readLine();
			int printTestChoice = Integer.parseInt(printTestChoiceString);
			if (printTestChoice == 1) {
				runTests();
				System.exit(0);
			}
			else if (printTestChoice != 0) {
				throw new IllegalArgumentException("Error: Choice must be 0 or 1.");
			}

			System.out.println("Please input the name of the program (enter for default):");

			fileName = findFile(reader.readLine());

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
		GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(fileName));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		GuardCommandParser parser = new GuardCommandParser(tokens);
		//Do not remove.
		parser.setAnnotations(lex.getAnnotations());

		CompilationUnit unit;

		try {

			program_return program_r = parser.program();
			PrettyCodeWriter cw = new PrettyCodeWriter();
			unit = program_r.compilationUnit;

			switch (chosenOption) {
			case PRINT : {
				StatementIterator staIte = new StatementIterator(new PrettyCodeWriter());
				staIte.tour(unit);
				break;
			}
			case ALFPRD : {
				KillRepairAnalysis ana;
				KillRepairAnalysisWorklist wl = new SimpleRRKRWorklist();
				ana = new ReachingDefinitionAnalysis();
				System.out.println("Starting analysis");
				long st = System.currentTimeMillis();
				EvaluatedStatement nroot = wl.run(ana, unit);
				System.out.println("Finished analysis, time: " + (System.currentTimeMillis() - st) + "ms.");
				printCode(nroot, unit);
				new ALFPReachingDefinition().convertToALFP(nroot, unit);
				break;
			}
			case PS : {
				ProgramSlicing programSlicing = new ProgramSlicing(new SimpleRRKRWorklist());
				unit = programSlicing.rewrite(unit);
				printCode(unit.getRootStatement(), unit);
				break;
			}
			default : {
				KillRepairAnalysis ana;
				KillRepairAnalysisWorklist wl = new SimpleRRKRWorklist();
				switch(chosenOption){
				case RD: ana = new ReachingDefinitionAnalysis(); break;
				case LV: ana = new LiveVariableAnalysis(); break;
				case CP: ana = new ConstantPropagationAnalysis(); break;
				case CPBKandLV:
				case CPBK: ana = new ConstantPropagationBranchKiller(); break;
				default: throw new AssertionError();
				}
				if(!ana.isForwardAnalysis()) {
					cw.setPrintEntryEvaluation(true);
				}
				StatementIterator staIte = new StatementIterator(cw);
				CompilationUnit eunit;
				System.out.println("Starting analysis");
				long st = System.currentTimeMillis();
				EvaluatedStatement nroot = wl.run(ana, unit);
				System.out.println("Finished analysis, time: " + (System.currentTimeMillis() - st) + "ms.");

				eunit = new CompilationUnit(unit.getUnitName(), nroot,
						unit.getVariableTable(), unit.getFinalStatements(), unit.getFactory());
				staIte.tour(eunit);
				if(chosenOption == Options.CP || chosenOption == Options.CPBK ||
						chosenOption == Options.CPBKandLV) {
					eunit = new ConstantFolder().rewrite(eunit);
					System.out.println("\n\n\n######## The rewritten code (CP) ########\n");
					staIte.tour(eunit);
					if(chosenOption == Options.CPBKandLV){
						System.out.println("Starting analysis");
						st = System.currentTimeMillis();
						nroot = wl.run(new LiveVariableAnalysis(), unit);
						System.out.println("Finished analysis, time: " + (System.currentTimeMillis() - st) + "ms.");

						eunit = new CompilationUnit(unit.getUnitName(), nroot,
								unit.getVariableTable(), unit.getFinalStatements(), unit.getFactory());
						cw.setPrintEntryEvaluation(true);
						System.out.println("\n\n\n######## After LV ########\n");
						staIte.tour(eunit);
					}
				}
				if(chosenOption == Options.LV || chosenOption == Options.CPBKandLV){
					eunit = new DeadVariableElemination().rewrite(eunit);
					System.out.println("\n\n\n######## The rewritten code (LV) ########\n");
					staIte.tour(eunit);
				}
				eunit = new PurgeDeadCode().rewrite(eunit);
				System.out.println("\n\n\n######## The purged code ########\n");
				staIte.tour(eunit);
				break;
			}
			}

		} catch (RecognitionException e)  {
			e.printStackTrace();
		}
	}

	private static void runTests() {

		runProgramSlicingTests();
		runDeadCodeEliminationTests();
		runConstantPropagationTests();

	}

	private static void runProgramSlicingTests() {
		// TODO Auto-generated method stub

	}

	private static void runDeadCodeEliminationTests() {
		// TODO Auto-generated method stub

	}

	private static void runConstantPropagationTests() {
		// TODO Auto-generated method stub

	}

	private static void printCode(final Statement nroot,
			final CompilationUnit unit) throws Exception {

		StatementIterator staIte = new StatementIterator(new PrettyCodeWriter());
		staIte.tour(new CompilationUnit(
				unit.getUnitName(), nroot,
				unit.getVariableTable(), unit.getFinalStatements(),
				unit.getFactory()
		));
	}

	private static String findFile(final String name) throws FileNotFoundException {
		File f = new File(name);
		File dir;
		String names[];
		if(f.canRead()) {
			return name;
		}
		dir = f.getAbsoluteFile().getParentFile();
		names = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.contains(name);
			}
		});
		for(String n : names){
			if(n.startsWith(name +".") && !n.endsWith("~")) {
				return n;
			}
		}
		System.err.println("Could not find " + name + ", possible matches: ");
		for(String n : names){
			if(!n.endsWith("~")) {
				System.err.println(n);
			}
		}
		throw new FileNotFoundException("Could not find a file called: " + name);
	}
}
