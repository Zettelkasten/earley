package com.zettelnet.latin.param;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.token.Determination;

import static com.zettelnet.latin.param.FormParameter.unsafeCast;

public class FormParameterBuilder<T> {

	private final Map<Object, Set<? extends Property>> data;
	private Determination cause;

	public FormParameterBuilder() {
		this.data = new HashMap<>();
		this.cause = null;
	}

	public FormParameterBuilder<T> with(Property... properties) {
		for (Property property : properties) {
			with(property);
		}
		return this;
	}

	public FormParameterBuilder<T> with(Property property) {
		Object propertyType = property.getType();
		if (!data.containsKey(propertyType)) {
			data.put(propertyType, new HashSet<>());
		}
		unsafeCast(data.get(propertyType)).add(property);
		return this;
	}

	public FormParameter build() {
		return new FormParameter(data, cause);
	}

	public ParameterFactory<T, FormParameter> buildFactory() {
		return new SingletonParameterFactory<T, FormParameter>(build());
	}
}
