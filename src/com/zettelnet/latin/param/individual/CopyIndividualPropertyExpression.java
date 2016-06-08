package com.zettelnet.latin.param.individual;

import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

public class CopyIndividualPropertyExpression<T extends Property> implements IndividualPropertyExpression<T> {

	@Override
	public Set<T> predict(Set<T> parameter, Set<T> childParameter) {
		return FormParameter.deriveProperties(parameter, childParameter);
	}

	@Override
	public Set<T> scan(Set<T> parameter, Set<T> tokenParameter) {
		return FormParameter.deriveProperties(parameter, tokenParameter);
	}

	@Override
	public Set<T> complete(Set<T> parameter, Set<T> childParameter) {
		return FormParameter.deriveProperties(parameter, childParameter);
	}

	@Override
	public String toString(Object propertyType) {
		return propertyType + "[&pi;]";
	}
}
