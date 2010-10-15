package com.github.mithunder.statements;

public abstract class Value {

	protected final int vtype;

	protected Value(int vtype){
		this.vtype = vtype;
	}

	public int getValueType(){
		return vtype;
	}
	public abstract boolean isConstant();
}
