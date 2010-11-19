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
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.visitor.PrettyCodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;
import com.github.mithunder.worklist.SimpleRRKRWorklist;

public class PrintRunner {

	private enum Options {PRINT, RD, LV, CP, CPBK, ALFPRD };

	public static void main(String[] args) throws Exception {

		String fileName;
		Options chosenOption = null;
		BufferedReader reader = null;
		try {
			System.out.println("Please input the name of the program (enter for default):");

			reader = new BufferedReader(new InputStreamReader(System.in));
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

		if (fileName.equals("")) {
			fileName = "test_program_simple2.cmd";
		}
		GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(fileName));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		GuardCommandParser parser = new GuardCommandParser(tokens);
		//parser.setAnnotations(lex.getAnnotations());

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
				StatementIterator staIte = new StatementIterator(new PrettyCodeWriter());
				System.out.println("Starting analysis");
				long st = System.currentTimeMillis();
				EvaluatedStatement nroot = wl.run(ana, unit);
				System.out.println("Finished analysis, time: " + (System.currentTimeMillis() - st) + "ms.");
				staIte.tour(new CompilationUnit(unit.getUnitName(), nroot, unit.getVariableTable(), unit.getFinalStatements()));
				new ALFPReachingDefinition().convertToALFP(nroot, unit);
				break;
			}
			default : {
				KillRepairAnalysis ana;
				KillRepairAnalysisWorklist wl = new SimpleRRKRWorklist();
				switch(chosenOption){
				case RD: ana = new ReachingDefinitionAnalysis(); break;
				case LV: ana = new LiveVariableAnalysis(); break;
				case CP: ana = new ConstantPropagationAnalysis(); break;
				case CPBK: ana = new ConstantPropagationBranchKiller(); break;
				default: throw new AssertionError();
				}
				if(!ana.isForwardAnalysis()) {
					cw.setPrintEntryEvaluation(true);
				}
				StatementIterator staIte = new StatementIterator(cw);
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
