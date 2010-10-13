package com.github.mithunder.statements;

public interface StatementType {

	public static final int ASSIGN = 0;		/* x := a */
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
	public static final int LOGIC_NOT = 11;		/* ! */
	public static final int SIGN_INVERT = 12;	/* - (unary) */

	public static final int LOGIC_AND = 13;		/* & */
	public static final int LOGIC_OR = 14;		/* | */
	public static final int DO = 15;		/* do ... od */
	public static final int IF = 16;		/* if ... fi */
	public static final int SCOPE = 17;		/* {...} (can also be used in do and if for grouping statements)*/
}
