package com.github.mithunder.statements;

import java.util.List;
import java.util.ListIterator;

import com.github.mithunder.worklist.ReverseListIterator;

public abstract class Statement {

	protected int stype;

	protected Statement(int stype){
		this.stype = stype;
	}

	public int getStatementType(){
		return stype;
	}

	public abstract int getChildCount();

	public abstract List<? extends Statement> getChildren();

	public abstract Value[] getValues();

	public abstract Variable getAssign();

	public abstract List<Annotation> getAnnotations();

	public abstract CodeLocation getCodeLocation();

	public abstract boolean isKilled();

	public abstract void killStatement();

	public abstract void replaceChild(int i, Statement replacement);

	/**
	 * @param guard The guard statement
	 * @return The "final" statement in the guard (the one that decides the outcome
	 * of the guard).
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Statement> T finalGuardStatement(T guard){
		ListIterator it;
		List<? extends Statement> children = guard.getChildren();
		if(children == null || children.size() == 0) {
			return guard;
		}
		it = new ReverseListIterator(children);
		if(it.hasNext()) {
			return (T) it.next();
		}
		throw new AssertionError();
	}
}
