package com.zettelnet.latin.form;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FormPropertyType<T extends FormProperty> {

	private final String name;
	private final Set<T> values;
	
	@SafeVarargs
	public FormPropertyType(final String name, T... values) {
		this.name = name;
		this.values = new HashSet<>(Arrays.asList(values));
	}
	
	public String getName() {
		return name;
	}
	
	public Set<T> getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
