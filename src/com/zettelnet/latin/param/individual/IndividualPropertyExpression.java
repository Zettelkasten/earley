package com.zettelnet.latin.param.individual;

import java.util.Set;

import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;

/**
 * Represents a {@link ParameterExpression}-like which handles <strong>only one
 * property type</strong>. These {@link IndividualPropertyExpression}s are used
 * in parallel by a {@link ParameterExpression} (e.g.
 * {@link IndividualFormParameterExpression}), therefore the type of in- and
 * output has to be consistent (you cannot have a property expression that takes
 * a set of {@link Mood} parameters and returns a set of {@link Genus}).
 * 
 * @author Zettelkasten
 *
 * @param <T>
 *            The class of the property this expression handles (not to be
 *            confused with the property type returned by
 *            {@link Property#getType()})
 */
public interface IndividualPropertyExpression<T extends Property> {

	Set<T> predict(Set<T> parameter, Set<T> childParameter);

	Set<T> scan(Set<T> parameter, Set<T> tokenParameter);

	Set<T> complete(Set<T> parameter, Set<T> childParameter);

	default String toString(Object propertyType) {
		return toString();
	}
}
