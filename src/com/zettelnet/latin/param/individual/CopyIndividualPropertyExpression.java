package com.zettelnet.latin.param.individual;

import java.util.Set;

import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

/**
 * Represents an {@link IndividualPropertyExpression} that copies all parameters
 * of a certain type from parent to child. Similar to
 * {@link CopyParameterExpression}.
 * 
 * @author Zettelkasten
 * 
 * @param <T>
 *            The class of the property this expression handles (not to be
 *            confused with the property type returned by
 *            {@link Property#getType()})
 */
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
