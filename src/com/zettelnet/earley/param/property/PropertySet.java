package com.zettelnet.earley.param.property;

import java.util.Arrays;
import java.util.Collection;

public interface PropertySet<T extends Property> {

	boolean hasProperty(Class<? extends T> property);

	<U extends T> U getProperty(Class<U> property);

	Collection<T> values();

	PropertySet<T> retainAll(Collection<Class<? extends T>> properties);

	boolean hasProperties(@SuppressWarnings("unchecked") T... properties);

	default PropertySet<T> derive(@SuppressWarnings("unchecked") T... properties) {
		return derive(Arrays.asList(properties));
	}

	PropertySet<T> derive(Collection<? extends T> values);

}
