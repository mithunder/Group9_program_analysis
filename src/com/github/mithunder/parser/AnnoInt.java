package com.github.mithunder.parser;

import com.github.mithunder.statements.Annotation;

public class AnnoInt {
	private final Annotation anno;
	private final int lineNumber;
	public AnnoInt(Annotation anno, int lineNumber) {
		super();
		this.anno = anno;
		this.lineNumber = lineNumber;
		if (lineNumber == 0) {
			throw new IllegalArgumentException();
		}
	}
	public Annotation getAnno() {
		return anno;
	}
	public int getLineNumber() {
		return lineNumber;
	}
}
