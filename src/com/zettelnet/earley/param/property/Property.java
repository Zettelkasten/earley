package com.zettelnet.earley.param.property;

public interface Property {

	default String name() {
		return toString();
	}
	
	default String shortName() {
		return name();
	}
	
	default Class<? extends Property> getType() {
		return getClass();
	}
}
