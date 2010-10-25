package com.github.mithunder.runner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.github.mithunder.parser.GuardCommandLexer;
import com.github.mithunder.parser.GuardCommandParser;
import com.github.mithunder.parser.GuardCommandParser.program_return;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.visitor.CodeWriter;
import com.github.mithunder.statements.visitor.StatementIterator;

public class PrintRunner {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Please input the name of the program (enter for default):");
		
		String fileName;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			fileName = reader.readLine();
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		if (fileName.equals("")) {
			fileName = "test_program.cmd";
		}
		GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(fileName));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

        GuardCommandParser parser = new GuardCommandParser(tokens);

		CompilationUnit compilationUnit;

        try {
        	
        	program_return program_r = parser.program();
        	compilationUnit = program_r.compilationUnit;
        	
        	StatementIterator staIte = new StatementIterator(new CodeWriter());
        	staIte.tour(compilationUnit);
        
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
	}
}
