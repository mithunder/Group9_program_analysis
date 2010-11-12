package com.github.mithunder.statements.simple;

import com.github.mithunder.statements.Variable;

public class SimpleVariable extends Variable implements Comparable<SimpleVariable> {

	protected final int vid;

	protected SimpleVariable(int vtype, int vid) {
		super(vtype);
		this.vid = vid;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public int hashCode() {
		return vid;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}
		if(!(o instanceof SimpleVariable)) {
			return false;
		}
		SimpleVariable sv = (SimpleVariable) o;
		return (sv.vid == vid && sv.vtype == vtype);
	}

	@Override
	public int compareTo(SimpleVariable arg0) {
		return vid - arg0.vid;
	}
}
