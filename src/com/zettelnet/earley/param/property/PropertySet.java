package com.zettelnet.earley.param.property;

import java.util.Arrays;
import java.util.Collection;

public interface PropertySet<T extends Property> {

	boolean hasProperty(Object propertyType);

	<U extends T> U getProperty(Object propertyType);

	Collection<T> values();

	PropertySet<T> retainAll(Collection<?> propertyTypes);

	boolean hasProperties(@SuppressWarnings("unchecked") T... properties);

	default PropertySet<T> derive(@SuppressWarnings("unchecked") T... properties) {
		return derive(Arrays.asList(properties));
	}

	PropertySet<T> derive(Collection<? extends T> values);

}
