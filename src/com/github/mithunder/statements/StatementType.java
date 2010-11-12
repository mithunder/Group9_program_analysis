package com.github.mithunder.statements;

public final class StatementType {

	//Assign.
	public static final int ASSIGN = 0;		/* x := a */

	//Numeric binary.
	public static final int PLUS = 1;		/* + */
	public static final int MINUS = 2;		/* - (binary) */
	public static final int MULTIPLY = 3;		/* * */
	public static final int DIVIDE = 4;		/* / */
	public static final int LT = 5;			/* < */
	public static final int GT = 6;			/* > */
	public static final int LT_EQ = 7;		/* <= */
	public static final int GT_EQ = 8;		/* >= */
	public static final int EQ = 9;			/* = */
	public static final int NEQ = 10;		/* != */

	//Unary.
	public static final int LOGIC_NOT = 11;		/* ! */
	public static final int SIGN_INVERT = 12;	/* - (unary) */

	//Logic binary.
	public static final int LOGIC_AND = 13;		/* & */
	public static final int LOGIC_OR = 14;		/* | */

	//Compound.
	public static final int DO = 15;		/* do ... od */
	public static final int IF = 16;		/* if ... fi */
	public static final int SCOPE = 17;		/* {...} (can also be used in do and if for grouping statements)*/

	//Other.
	public static final int ABORT = 18;
	public static final int SKIP = 19;
	public static final int READ = 20;
	public static final int WRITE = 21;

	/**
	 * Number of operand a statement has (compound always statements have 0).
	 *
	 * Note some simple statements (e.g. abort and skip) also have 0 operands.
	 */
	public static final int[] OPERANDS = new int[]{
		1, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2,
		1, 1, 2, 2, 0, 0, 0,
		0, 0, 0, 1
	};

	/**
	 * Symbol related to a simple statement if any (e.g. compound statements have null elements).
	 */
	public static final String[] SIMPLE_STATEMENT_SYMBOLS = new String[]{
		":=", "+", "-", "*", "/",
		"<", ">", "<=", ">=", "=", "!=",
		"!", "-", "&", "|", null, null, null,
		"abort", "skip", "read", "write"
	};

	public static final boolean isUnary(int stype){
		return OPERANDS[stype] == 1;
	}

	public static final boolean isBinary(int stype){
		return OPERANDS[stype] == 2;
	}

	public static final boolean isComparison(int stype) {
		return (LT <= stype) && (stype <= NEQ);
	}

	static {
		if(SIMPLE_STATEMENT_SYMBOLS.length != OPERANDS.length) {
			throw new AssertionError("Operand and SS-Symbol table disagree in number of statement types!");
		}
	}

	/* No - you cannot instantiate this class */
	private StatementType(){}

}
