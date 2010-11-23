package com.github.mithunder.statements;

public class CodeLocation {

	private final int lineNumber;

	public CodeLocation(final int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString() {
		return "line: " + lineNumber;
	}
}
