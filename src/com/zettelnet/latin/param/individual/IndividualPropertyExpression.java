package com.zettelnet.latin.param.individual;

import java.util.Set;

import com.zettelnet.earley.param.property.Property;

public interface IndividualPropertyExpression {

	Set<Property> predict(Set<Property> parameter, Set<Property> childParameter);

	Set<Property> scan(Set<Property> parameter, Set<Property> tokenParameter);

	Set<Property> complete(Set<Property> parameter, Set<Property> childParameter);

	default String toString(Class<? extends Property> propertyType) {
		return toString();
	}

}
