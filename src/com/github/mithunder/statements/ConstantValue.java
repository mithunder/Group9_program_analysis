package com.github.mithunder.statements;

public class ConstantValue extends Value {

	public static final ConstantValue TRUE = null;
	public static final ConstantValue FALSE = null;

	protected final int value;

	protected ConstantValue(int vtype, int value){
		super(vtype);
		this.value = value;
	}

	@Override
	public boolean isConstant(){
		return true;
	}

	public static ConstantValue getConstantValue(int vtype, int value) {
		return new ConstantValue(vtype, value);
	}
}
