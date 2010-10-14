package com.github.mithunder.statements;

public interface StatementType {
	
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
	public static final int ABORT = 19;
	public static final int SKIP = 20;
	public static final int READ = 21;
	public static final int WRITE = 22;
}
