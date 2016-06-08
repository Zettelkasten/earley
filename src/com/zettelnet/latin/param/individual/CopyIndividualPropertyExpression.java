package com.zettelnet.latin.param.individual;

import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

public class CopyIndividualPropertyExpression implements IndividualPropertyExpression {

	@Override
	public Set<Property> predict(Set<Property> parameter, Set<Property> childParameter) {
		return FormParameter.deriveProperties(parameter, childParameter);
	}

	@Override
	public Set<Property> scan(Set<Property> parameter, Set<Property> tokenParameter) {
		return FormParameter.deriveProperties(parameter, tokenParameter);
	}

	@Override
	public Set<Property> complete(Set<Property> parameter, Set<Property> childParameter) {
		return FormParameter.deriveProperties(parameter, childParameter);
	}

	@Override
	public String toString(Object propertyType) {
		return propertyType + "[&pi;]";
	}
}
