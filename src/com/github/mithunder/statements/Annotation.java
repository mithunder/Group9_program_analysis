package com.github.mithunder.statements;

public class Annotation {

	private String name;
	private String value;

	public Annotation(String name, String value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static Annotation newInstance(String name, String value){
		return new Annotation(name, value);
	}
}
