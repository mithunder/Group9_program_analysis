package com.github.mithunder.statements.visitor;

import static com.github.mithunder.statements.StatementType.ASSIGN;
import static com.github.mithunder.statements.StatementType.READ;
import static com.github.mithunder.statements.StatementType.SIMPLE_STATEMENT_SYMBOLS;
import static com.github.mithunder.statements.StatementType.WRITE;
import static com.github.mithunder.statements.StatementType.isBinary;
import static com.github.mithunder.statements.StatementType.isUnary;

import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.Variable;

//TODO: Implement.
public class PrettyCodeWriter extends CodeWriter {


	protected void printStatement(Statement s){
		Variable assign = s.getAssign();
		Value[] v = s.getValues();
		int stype = s.getStatementType();
		if(isBinary(stype)){
			out.print(indent + varTable.getVariableName(assign) + " :=  " + v2s(v[0]) + " " + SIMPLE_STATEMENT_SYMBOLS[stype] + " " + v2s(v[1]));
		} else if(isUnary(stype)){
			if(stype == ASSIGN){
				out.print(indent + varTable.getVariableName(assign) + " := " + v2s(v[0]));
			} else {
				switch(stype){
				case READ:
					out.print(indent + "read " + varTable.getVariableName(assign));
					break;
				case WRITE:
					out.print(indent + "write " + v2s(v[0]));
					break;
				default:
					out.print(indent + varTable.getVariableName(assign) + " :=  " + SIMPLE_STATEMENT_SYMBOLS[stype] + " " + v2s(v[0]));
					break;
				}
			}
		} else {
			out.print(indent + SIMPLE_STATEMENT_SYMBOLS[stype]);
		}
	}
	
}
