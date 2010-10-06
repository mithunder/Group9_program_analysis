package com.github.mithunder.statements;

public abstract class ConstantValue extends Value {

	public static final ConstantValue TRUE = null;
	public static final ConstantValue FALSE = null;

	public static ConstantValue getConstantValue(int type, int value) {
		return null;
	}
}
