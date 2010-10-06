package com.github.mithunder.statements;

public interface StatementType {

	public static final int PLUS = 0;		/* + */
	public static final int MINUS = 1;		/* - (binary) */
	public static final int MULTIPLY = 2;		/* * */
	public static final int DIVIDE = 3;		/* / */
	public static final int LT = 4;			/* < */
	public static final int GT = 5;			/* > */
	public static final int LT_EQ = 6;		/* <= */
	public static final int GT_EQ = 7;		/* >= */
	public static final int EQ = 8;			/* = */
	public static final int NEQ = 9;		/* != */
	public static final int LOGIC_NOT = 10;		/* ! */
	public static final int SIGN_INVERT = 11;	/* - (unary) */

	public static final int LOGIC_AND = 12;		/* & */
	public static final int LOGIC_OR = 13;		/* | */
	public static final int DO = 14;		/* do ... od */
	public static final int IF = 15;		/* if ... fi */
	public static final int SCOPE = 16;		/* {...} (can also be used in do and if for grouping statements)*/
}
