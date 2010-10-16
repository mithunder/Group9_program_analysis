package com.github.mithunder.statements.visitor;

import static com.github.mithunder.statements.StatementType.ASSIGN;
import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.READ;
import static com.github.mithunder.statements.StatementType.SCOPE;
import static com.github.mithunder.statements.StatementType.SIMPLE_STATEMENT_SYMBOLS;
import static com.github.mithunder.statements.StatementType.WRITE;
import static com.github.mithunder.statements.StatementType.isBinary;
import static com.github.mithunder.statements.StatementType.isUnary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class CodeWriter implements StatementVisitor {

	protected PrintStream out;
	protected File outfile;
	protected String indent;
	protected VariableTable varTable;

	public CodeWriter(){
		this(null);
	}

	public CodeWriter(File outfile){
		this.outfile = outfile;
	}

	@Override
	public void endTour(CompilationUnit unit) throws IOException {
		out.println("end");
		varTable = null;
		if(outfile != null) {
			out.close();
			if(out.checkError()) {
				throw new IOException("Error writing " + unit.getUnitName() + " to " + outfile);
			}
		}
	}

	@Override
	public void enter(int vitype, Statement compound, Statement parent, int cno) {
		if(StatementVisitor.VITYPE_GUARD == vitype) {
			out.print(indent + "[] ");

		} else if(StatementVisitor.VITYPE_COMMAND == vitype) {
			out.print("->");
		} else {
			out.print(indent);
		}
	}

	@Override
	public void leave(int vitype, Statement compound, Statement parent, int cno) {}

	@Override
	public void enterCompound(Statement s, Statement parent, int cno) {
		int stype = s.getStatementType();
		indent = indent + "  ";
		switch(stype){
		case DO:
			out.print("do ");
			break;
		case IF:
			out.print("if ");
			break;
		case SCOPE:
			if(cno != StatementVisitor.ROOT_STATEMENT) {
				out.println("{");
				out.print(indent);
			}
			break;
		}
	}


	@Override
	public void leaveCompound(Statement s, Statement parent, int cno) {
		int stype = s.getStatementType();
		out.println();
		indent = indent.substring(2);
		switch(stype){
		case DO:
			out.println(indent + "od");
			break;
		case IF:
			out.print(indent + "fi");
			break;
		case SCOPE:
			if(cno != StatementVisitor.ROOT_STATEMENT) {
				out.println(indent + "}");
			}
			break;
		}

	}

	@Override
	public void startTour(CompilationUnit unit) throws FileNotFoundException {
		if(outfile != null) {
			out = new PrintStream(new FileOutputStream(outfile));
		} else {
			out = System.out;
		}
		varTable = unit.getVariableTable();
		indent = "";
		out.println("module " + unit.getUnitName() + ":");
	}

	@Override
	public void visitStatement(Statement s, Statement parent, int cno) {
		int stype = s.getStatementType();
		switch(stype){
		case SCOPE:
			out.print(indent);
			break;
		case DO:
		case IF:
			if(cno > 0) {
				if((cno & 1) == 0) {
					out.print(indent + "[] ");
				} else {
					out.print(indent + " -> ");
				}
			}
			break;
		}
		printStatement(s);
		if(cno + 1 < parent.getChildCount()) {
			out.print(";");
		}
		out.println();
	}

	protected String v2s(Value v){
		String ret;
		if(v.isConstant()){
			ConstantValue cv = (ConstantValue)v;
			int value = cv.getValue();
			if(cv.getValueType() == ValueType.BOOLEAN_TYPE){
				if(value != 0) {
					ret = "TRUE";
				} else {
					ret = "FALSE";
				}
			} else {
				ret = String.valueOf(value);
			}
		} else {
			ret = varTable.getVariableName((Variable)v);
		}
		return ret;
	}

	protected void printStatement(Statement s){
		Variable assign = s.getAssign();
		Value[] v = s.getValues();
		int stype = s.getStatementType();
		if(isBinary(stype)){
			out.println(varTable.getVariableName(assign) + " :=  " + v2s(v[0]) + " " + SIMPLE_STATEMENT_SYMBOLS[stype] + " " + v2s(v[1]));
		} else if(isUnary(stype)){
			if(stype == ASSIGN){
				out.println(varTable.getVariableName(assign) + " := " + v2s(v[0]));
			} else {
				switch(stype){
				case READ:
					out.println("read " + assign);
					break;
				case WRITE:
					out.println("write " + v2s(v[0]));
					break;
				default:
					out.println(varTable.getVariableName(assign) + " :=  " + SIMPLE_STATEMENT_SYMBOLS[stype] + " " + v2s(v[0]));
					break;
				}
			}
		} else {
			out.println(SIMPLE_STATEMENT_SYMBOLS[stype]);
		}
	}
}