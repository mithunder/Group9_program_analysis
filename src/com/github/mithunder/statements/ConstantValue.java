package com.github.mithunder.statements;

public class ConstantValue extends Value {

	public static final ConstantValue TRUE = getConstantValue(ValueType.BOOLEAN_TYPE, 1);
	public static final ConstantValue FALSE = getConstantValue(ValueType.BOOLEAN_TYPE, 0);

	protected final int value;

	protected ConstantValue(int vtype, int value){
		super(vtype);
		this.value = value;
	}

	@Override
	public boolean isConstant(){
		return true;
	}

	@Override
	public int hashCode() {
		return value;
	}

	public int getValue(){
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		ConstantValue other;
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		other = (ConstantValue) obj;
		return other.vtype == vtype && other.value == value;
	}

	public static ConstantValue getConstantValue(int vtype, int value) {
		if(vtype == ValueType.BOOLEAN_TYPE){
			/* no reason to make multiple versions of these */
			if(value != 0) {
				return TRUE;
			}
			return FALSE;
		}
		return new ConstantValue(vtype, value);
	}
}
