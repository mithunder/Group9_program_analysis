package com.github.mithunder.runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import com.github.mithunder.rewrite.CodeRewriter;
import com.github.mithunder.rewrite.ConstantFolder;
import com.github.mithunder.rewrite.DeadVariableElemination;
import com.github.mithunder.rewrite.ProgramSlicing;
import com.github.mithunder.rewrite.PurgeDeadCode;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.visitor.CWriter;
import com.github.mithunder.statements.visitor.PrettyCodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;
import com.github.mithunder.worklist.KillRepairAnalysisWorklist;
import com.github.mithunder.worklist.SimpleRRKRWorklist;

public class PrintRunner {

	private enum Options {PRINT, RD, LV, CP, CPBK, ALFPRD, PS, CPBKandLV };
	public final static String programSlicingTestsFolder = "program_slicing_tests";
	public final static String programSlicingTestsResult = "program_slicing_results";

	public static void main(String[] args) throws Exception {

		String fileName;
		Options chosenOption = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("A Guarded Command Language analyser and code-writer");
			System.out.println("Please input either: ");
			System.out.println(" [0] analyse and print a file.");
			System.out.println(" [1] run automated regression tests.");

			String printTestChoiceString = reader.readLine();
			int printTestChoice = Integer.parseInt(printTestChoiceString);
			if (printTestChoice == 1) {
				runTests();
				System.exit(0);
			}
			else if (printTestChoice != 0) {
				throw new IllegalArgumentException("Error: Choice must be 0 or 1.");
			}

			System.out.println("Please input the name of the program:");

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

	private static void runTests() throws Exception{

		//Run program slicing tests.
		System.out.println("Running Program Slicing tests.");
		runTests(
				new File(programSlicingTestsFolder),
				new ProgramSlicing(new SimpleRRKRWorklist())
		);
		System.out.println("Done with Program Slicing tests.");
		System.out.println("Running Dead Code/variable Elimination tests.");
		runDeadCodeEliminationTests();
		System.out.println("Done with Dead Code/variable Elimination tests.");
		System.out.println("Running Constant Folding tests.");
		runConstantPropagationTests();
		System.out.println("Done with Constant Folding tests.");
		System.out.println("Done with all tests.");


	}

	private static void runTests(final File folder,
			final CodeRewriter rewriter) throws Exception{
		//For every expected and source;
		//For the source, perform the analysis.
		//Then write both files prettily.
		//Compare them, and write the result in the results file.

		final File[][] sourceExpectedFiles = getSourceExpectedFiles(folder);
		final File[] sourceFiles = sourceExpectedFiles[0];
		final File[] expectedFiles = sourceExpectedFiles[1];

		//Prepare the results file.
		File resultsFile = new File(folder, "results");
		if (resultsFile.exists()) {
			//Remove previous results.
			resultsFile.delete();
			resultsFile.createNewFile();
		}
		final BufferedWriter resultsWriter = new BufferedWriter(new FileWriter(resultsFile));
		final File resultCleanFile = new File("tmp_file_res");
		final File expectedCleanFile = new File("tmp_file_exp");

		for (int i = 0; i < expectedFiles.length; i++) {

			//Read in the source get the program.
			{
				final CompilationUnit sourceUnit = getProgram(sourceFiles[i].getPath());
				//Perform the test and write.
				final CompilationUnit resultUnit = rewriter.rewrite(sourceUnit);
				printCode(resultUnit.getRootStatement(), resultUnit, resultCleanFile);
			}

			//Read in the expected and print.
			{
				final CompilationUnit expectedUnit = getProgram(expectedFiles[i].getPath());
				printCode(expectedUnit.getRootStatement(), expectedUnit, expectedCleanFile);
			}

			//Compare the results, and write them.
			final boolean isFilesContentEqual = compareFiles(resultCleanFile, expectedCleanFile);
			resultsWriter.append(
					expectedFiles[i].getName().split("\\.")[0] + " passed: " + isFilesContentEqual +
					"\n"
			);

			if (!isFilesContentEqual) {
				throw new IllegalArgumentException("Error-debug; files were not equal.");
			}

		}
		resultsWriter.close();

	}

	private static File[][] getSourceExpectedFiles(File folder) {
		//Filter out all the relevant files.
		final File[] sourceFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String[] nameParts = name.split("\\.");
				if (nameParts.length != 2) {return false;}
				return nameParts[1].equals("source");
			}
		});

		final File[] expectedFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String[] nameParts = name.split("\\.");
				if (nameParts.length != 2) {return false;}
				return nameParts[1].equals("expected");
			}
		});

		//Check the length.
		if (expectedFiles.length != sourceFiles.length) {
			throw new IllegalStateException("Error: " +
					"Number of \".expected\"(" + expectedFiles.length +
					") and \"source\" (" + sourceFiles.length + ") does not match up.");
		}

		//Sort the files alphabetically, so to ease the matching of files.
		final List<File> sourceF = Arrays.asList(sourceFiles);
		final List<File> expectedF = Arrays.asList(expectedFiles);
		Comparator<File> alphaComparator = new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		Collections.sort(sourceF, alphaComparator);
		Collections.sort(expectedF, alphaComparator);

		//Ensure matching sanity.
		for (int i = 0; i < sourceF.size(); i++) {
			final String sourceName = sourceF.get(i).getName();
			final String expectedName = expectedF.get(i).getName();
			if (!sourceName.split("\\.")[0].equals(expectedName.split("\\.")[0])) {
				throw new IllegalArgumentException("Error: Files does not match up, " +
						sourceName + " did not match " + expectedName
				);
			}
		}

		//Construct the new files.
		final File[][] sourceExpected = new File[2][];
		sourceExpected[0] = sourceF.toArray(new File[sourceF.size()]);
		sourceExpected[1] = expectedF.toArray(new File[expectedF.size()]);
		return sourceExpected;
	}

	private static void runDeadCodeEliminationTests() {
		// TODO Auto-generated method stub
	}

	private static void runConstantPropagationTests() throws Exception {
		final File folder = new File("constant_folding_tests");
		final File[][] tests = getSourceExpectedFiles(folder);
		final File[] sourceFiles = tests[0];
		final File[] expectedFiles = tests[1];
		CodeRewriter rewriter = new ConstantFolder();
		KillRepairAnalysis cpa = new ConstantPropagationAnalysis();
		KillRepairAnalysisWorklist wl = new SimpleRRKRWorklist();
		CodeRewriter pdc = new PurgeDeadCode();
		//Prepare the results file.
		File resultsFile = new File(folder, "results");
		final BufferedWriter resultsWriter = new BufferedWriter(new FileWriter(resultsFile));
		final File resultCleanFile = new File("tmp_file_res");
		final File expectedCleanFile = new File("tmp_file_exp");
		for (int i = 0; i < expectedFiles.length; i++) {

			//Read in the source get the program.
			{
				final CompilationUnit sourceUnit = getProgram(sourceFiles[i].getPath());
				//Perform the test and write.
				final EvaluatedStatement root = wl.run(cpa, sourceUnit);
				final CompilationUnit resultUnit = pdc.rewrite(rewriter.rewrite(
						new CompilationUnit(sourceUnit, root)));
				printCode(resultUnit.getRootStatement(), resultUnit, resultCleanFile);
			}

			//Read in the expected and print.
			{
				final CompilationUnit expectedUnit = getProgram(expectedFiles[i].getPath());
				printCode(expectedUnit.getRootStatement(), expectedUnit, expectedCleanFile);
			}

			//Compare the results, and write them.
			final boolean isFilesContentEqual = compareFiles(resultCleanFile, expectedCleanFile);
			resultsWriter.append(
					expectedFiles[i].getName().split("\\.")[0] + " passed: " + isFilesContentEqual +
					"\n"
			);

			if (!isFilesContentEqual) {
				throw new IllegalArgumentException("Error-debug; files were not equal.");
			}

		}
		resultsWriter.close();
	}

	private static CompilationUnit getProgram(String fileName) throws Exception {
		GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(fileName));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		GuardCommandParser parser = new GuardCommandParser(tokens);
		//Do not remove.
		parser.setAnnotations(lex.getAnnotations());

		return parser.program().compilationUnit;
	}

	private static void printCode(final Statement nroot,
			final CompilationUnit unit) throws Exception {
		printCode(nroot, unit, null);
	}

	/**
	 * @param nroot
	 * @param unit
	 * @param file If file is null, print to System.out.
	 * @throws Exception
	 */
	private static void printCode(final Statement nroot,
			final CompilationUnit unit, final File file) throws Exception {

		final CWriter cWriter;
		if (file != null) {
			cWriter = new PrettyCodeWriter(file);
		}
		else {
			cWriter = new PrettyCodeWriter();
		}
		final StatementIterator staIte = new StatementIterator(cWriter);
		staIte.tour(new CompilationUnit(
				unit.getUnitName(), nroot,
				unit.getVariableTable(), unit.getFinalStatements(),
				unit.getFactory()
		));
	}

	private static boolean compareFiles(File resultCleanFile,
			File expectedCleanFile) throws Exception {

		//Simply read in the files, and check the checksum.
		final byte[] checksum1 = getChecksum(resultCleanFile);
		final byte[] checksum2 = getChecksum(expectedCleanFile);
		return MessageDigest.isEqual(checksum1, checksum2);
	}

	private static byte[] getChecksum(File file) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("SHA1");
		final InputStream is = new DigestInputStream(new FileInputStream(file), md);
		try {
			while (is.read() != -1) {
			}
		}
		finally {
			is.close();
		}
		return md.digest();
	}

	private static String findFile(final String name) throws FileNotFoundException {
		File f = new File(name);
		File dir;
		String names[];
		if(f.canRead()) {
			return name;
		}
		if(name.equals("")) {
			throw new FileNotFoundException("Please input a file name.");
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
