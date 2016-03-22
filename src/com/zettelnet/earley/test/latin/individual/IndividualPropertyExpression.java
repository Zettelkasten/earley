package com.zettelnet.earley.test.latin.individual;

import java.util.Set;

import com.zettelnet.latin.form.FormProperty;

public interface IndividualPropertyExpression {

	Set<FormProperty> predict(Set<FormProperty> parameter, Set<FormProperty> childParameter);

	Set<FormProperty> scan(Set<FormProperty> parameter, Set<FormProperty> tokenParameter);

	Set<FormProperty> complete(Set<FormProperty> parameter, Set<FormProperty> childParameter);

	default String toString(Class<? extends FormProperty> propertyType) {
		return toString();
	}

}
