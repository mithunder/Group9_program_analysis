package com.github.mithunder.statements.simple;

import com.github.mithunder.statements.Variable;

public class SimpleVariable extends Variable {

	protected final int vid;

	protected SimpleVariable(int vtype, int vid) {
		super(vtype);
		this.vid = vid;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

}
