package com.zettelnet.earley.param.property;

public class SimplePropertyType {

	private final String name;
	
	public SimplePropertyType(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
