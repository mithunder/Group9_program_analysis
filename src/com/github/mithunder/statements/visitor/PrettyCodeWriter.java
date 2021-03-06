package com.github.mithunder.statements.visitor;

import static com.github.mithunder.statements.StatementType.ASSIGN;
import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.READ;
import static com.github.mithunder.statements.StatementType.SCOPE;
import static com.github.mithunder.statements.StatementType.SIMPLE_STATEMENT_SYMBOLS;
import static com.github.mithunder.statements.StatementType.WRITE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mithunder.statements.Annotation;
import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;

public class PrettyCodeWriter implements StatementVisitor, CWriter {

	protected PrintStream out;
	protected File outfile;
	protected VariableTable varTable;
	private Map<Variable, Statement> tempVarToStatement;
	private final String indent = "   ";
	private Map<Integer, String> indentCache = new HashMap<Integer, String>();
	private boolean entry = false;

	public PrettyCodeWriter(){
		this(null);
	}

	public PrettyCodeWriter(File outfile){
		this.outfile = outfile;
	}

	public void setPrintEntryEvaluation(boolean entry){
		this.entry = entry;
	}

	public void endTour(CompilationUnit unit) throws Exception {}
	public void enter(int vitype, Statement compound, Statement parent, int cno)
	throws Exception {}
	public void enterCompound(Statement compound, Statement parent, int cno)
	throws Exception {}
	public void leave(int vitype, Statement compound, Statement parent, int cno)
	throws Exception {}
	public void leaveCompound(Statement compound, Statement parent, int cno)
	throws Exception {}
	public void visitStatement(Statement s, Statement parent, int cno)
	throws Exception {
	}

	@Override
	public void startTour(CompilationUnit unit) throws FileNotFoundException {

		//* Preparation.

		if(outfile != null) {
			out = new PrintStream(new FileOutputStream(outfile));
		} else {
			out = System.out;
		}
		varTable = unit.getVariableTable();

		final Statement rootStatement = unit.getRootStatement();

		//Go through all statements, and add them if they are temporary variables.
		tempVarToStatement = new HashMap<Variable, Statement>();
		walkStatements(rootStatement, new Visitor<Statement>(){
			public void visitStatement(Statement s) {
				if (s.getAssign() != null &&
						varTable.isTemporaryVariable(s.getAssign())) {
					tempVarToStatement.put(s.getAssign(), s);
				}
			}
		});



		//* Print.
		if(entry) {
			out.println("// Printing entry evaluation");
		} else {
			out.println("// Printing exit evaluation");
		}

		final Statement rootStat = unit.getRootStatement();

		out.print("module " + unit.getUnitName() + ":");
		printAnnotations(rootStat);
		out.println();

		printStatements(rootStat, -1, GuardedType.NONE, "");
		out.println("end");
	}

	@SuppressWarnings("unchecked")
	public static <T extends Statement> void  walkStatements(T s, Visitor<T> visitor) {
		visitor.visitStatement(s);
		if (s.getChildCount() != 0) {
			for (Statement child : s.getChildren()) {
				walkStatements((T) child, visitor);
			}
		}
	}

	public interface Visitor<T extends Statement> {
		public void visitStatement(T s);
	}

	private void printStatements(Statement s, int level, GuardedType guardedType, String postFix) {
		final int sType = s.getStatementType();
		switch (sType) {
		case DO :
		case IF :{
			//Print start.
			if (sType == DO) {out.println(repeat(level) + "do");}
			else if  (sType == IF) {out.println(repeat(level) + "if");}

			//Print children.
			final List<? extends Statement> children = s.getChildren();
			final int halfSize = s.getChildCount()/2;
			for (int i = 0; i < halfSize; i++) {
				if (i != 0) {
					out.print(repeat(level+1) + "[] ");
				}
				printStatements(children.get(i), i != 0 ? -1 : level, GuardedType.GUARD, " ->");
				//Since children of if/do is not guaranteed to be scopes, handle that.
				final Statement command = children.get(i+halfSize);
				if (command.getStatementType() == StatementType.SCOPE) {
					printStatements(command, level+1, GuardedType.NONE, "");
				}
				else {
					printStatements(command, level+2, GuardedType.NONE, "");
				}
			}

			//Print final.
			if (sType == DO) {out.print(repeat(level) + "od");}
			else if  (sType == IF) {out.print(repeat(level) + "fi");}
			out.print(postFix);
			printAnnoEval(s);

			break;
		}
		case SCOPE :{
			//First, detect if this is a test scope.
			if (guardedType == GuardedType.GUARD) {
				//We must combine these children into something sensible.
				final Statement lastChild = s.getChildren().get(s.getChildren().size()-1);
				out.print(
						repeat(level+1) +
						getExpressionPart(
								lastChild, tempVarToStatement
						)
				);

				out.print(postFix);
				//Print the annotations of the scope, not the child.
				printAnnotations(s);
				if(lastChild instanceof EvaluatedStatement){
					printEvaluation((EvaluatedStatement)lastChild);
				}
				out.println();
			}
			else {

				//This is not a test scope.

				//First, find the last non-temporary assign-value child.
				Statement lastNonTempChild = null;
				for (Statement child : s.getChildren()) {
					final Variable ass = child.getAssign();
					if (ass != null && varTable.isTemporaryVariable(ass)) {
						continue;
					}
					lastNonTempChild = child;
				}

				//Print all children, but remember to add semicolon the right places.
				for (Statement child : s.getChildren()) {

					//If it is a temporary variable, ignore it.
					final Variable ass = child.getAssign();
					if (ass != null && varTable.isTemporaryVariable(ass)) {
						continue;
					}

					final String childPostFix;
					if (child != lastNonTempChild) {
						childPostFix = ";";
					}
					else {
						childPostFix = "";
					}
					printStatements(child, level+1, GuardedType.NONE, childPostFix);
				}
				out.print(postFix);
			}
			break;
		}
		case WRITE : {
			out.print(repeat(level) + getExpressionPart(s, tempVarToStatement));
			out.print(postFix);
			printAnnoEval(s);
			break;
		}
		case READ : {
			out.print(repeat(level) +
					SIMPLE_STATEMENT_SYMBOLS[sType] + " " +
					v2s(s.getAssign())
			);
			out.print(postFix);
			printAnnoEval(s);
			break;
		}
		default : {

			//If we have a non-temporary assignment, print it.
			if (s.getAssign() != null && !varTable.isTemporaryVariable(s.getAssign())) {
				out.print(repeat(level) +
						v2s(s.getAssign()) + " " +
						SIMPLE_STATEMENT_SYMBOLS[ASSIGN] + " " +
						getExpressionPart(s, tempVarToStatement)
				);
				out.print(postFix);
				printAnnoEval(s);
			}
			else {
				//Else, simply print the statement symbol.
				out.print(repeat(level) + SIMPLE_STATEMENT_SYMBOLS[sType]);
				out.print(postFix);
				printAnnoEval(s);
			}
			break;
		}
		}
	}

	private void printAnnoEval(Statement s) {
		printAnnotations(s);

		if(s instanceof EvaluatedStatement){
			printEvaluation((EvaluatedStatement)s);
		}

		out.println();
	}

	private String getExpressionPart(final Statement s,
			final Map<Variable, Statement> tempVarToStatement) {
		final int sType = s.getStatementType();

		//Handle unary expressions.
		if (StatementType.isUnary(sType) && sType != ASSIGN) {
			final Value val = s.getValues()[0];
			final Statement refS = tempVarToStatement.get(val);
			if (refS == null) {
				return SIMPLE_STATEMENT_SYMBOLS[sType] + (sType == WRITE ? " " : "") +
				"(" + v2s(val) + ")"
				;
			}
			return SIMPLE_STATEMENT_SYMBOLS[sType] + (sType == WRITE ? " " : "") +
			"(" + getExpressionPart(refS, tempVarToStatement) + ")"
			;
		}

		//Handle binary expressions.
		if (StatementType.isBinary(sType)) {
			final Value val1 = s.getValues()[0];
			final Value val2 = s.getValues()[1];
			final Statement refS1 = tempVarToStatement.get(val1);
			final Statement refS2 = tempVarToStatement.get(val2);
			final String str1 =
				refS1 == null ?
						v2s(val1) :
							getExpressionPart(refS1, tempVarToStatement)
							;
						final String str2 =
							refS2 == null ?
									v2s(val2) :
										getExpressionPart(refS2, tempVarToStatement)
										;
									return "(" + str1 + SIMPLE_STATEMENT_SYMBOLS[sType] + str2 + ")";
		}

		//Handle single val.
		return v2s(s.getValues()[0]);
	}

	private enum GuardedType {
		GUARD, NONE;
	}

	protected String v2s(Value v){
		String ret;
		if(v.isConstant()){
			ConstantValue cv = (ConstantValue)v;
			int value = cv.getValue();
			if(cv.getValueType() == ValueType.BOOLEAN_TYPE){
				if(value != 0) {
					ret = "true";
				} else {
					ret = "false";
				}
			} else {
				ret = String.valueOf(value);
			}
		} else {
			ret = varTable.getVariableName((Variable)v);
		}
		return ret;
	}

	private final String repeat(int level) {
		final String str = indentCache.get(level);
		if (str != null) {
			return str;
		}
		String sum = "";
		for (int i = 0; i < level; i++) {
			sum += indent;
		}
		indentCache.put(level, sum);
		return sum;
	}

	private void printEvaluation(EvaluatedStatement e){
		Statement o = e.getStatement();
		out.print(repeat(2) + " //");
		out.print("ID: " + Integer.toHexString(System.identityHashCode(o)) + ", Analysis: ");
		if(entry) {
			out.print(e.getEntryEvaluation());
		} else {
			out.print(e.getExitEvaluation());
		}
		out.print(", is killed: " + e.isKilled());
	}

	private void printAnnotations(Statement e) {

		String name = null;
		for(Annotation a : e.getAnnotations()) {
			if("Name".equalsIgnoreCase(a.getName())) {
				name = a.getValue();
				break;
			}
		}
		if(name != null) {
			out.print(repeat(2) + " //");
			out.print("Name: " + name);
		}
	}
}
