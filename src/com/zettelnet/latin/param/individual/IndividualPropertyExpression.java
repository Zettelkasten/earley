package com.zettelnet.latin.param.individual;

import java.util.Set;

import com.zettelnet.earley.param.property.Property;

public interface IndividualPropertyExpression<T extends Property> {

	Set<T> predict(Set<T> parameter, Set<T> childParameter);

	Set<T> scan(Set<T> parameter, Set<T> tokenParameter);

	Set<T> complete(Set<T> parameter, Set<T> childParameter);

	default String toString(Object propertyType) {
		return toString();
	}

}
