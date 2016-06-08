package com.zettelnet.latin.param.individual;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

/**
 * Represents an {@link IndividualPropertyExpression} that specifies a concrete
 * property set for one property type. Acts like a
 * {@link CopyIndividualPropertyExpression} that uses the specified type as
 * parent, ignoring the actual parent properties. Similar to
 * {@link SpecificParameterExpression}.
 * 
 * @author Zettelkasten
 * 
 * @param <T>
 *            The class of the property this expression handles (not to be
 *            confused with the property type returned by
 *            {@link Property#getType()})
 */
public class SpecificIndividualPropertyExpression<T extends Property> implements IndividualPropertyExpression<T> {

	private final Set<T> specifiedParameter;

	public SpecificIndividualPropertyExpression(final Set<T> parameter) {
		this.specifiedParameter = parameter;
	}

	@Override
	public Set<T> predict(Set<T> parameter, Set<T> childParameter) {
		return FormParameter.deriveProperties(specifiedParameter, childParameter);
	}

	@Override
	public Set<T> scan(Set<T> parameter, Set<T> tokenParameter) {
		return FormParameter.deriveProperties(specifiedParameter, tokenParameter);
	}

	@Override
	public Set<T> complete(Set<T> parameter, Set<T> childParameter) {
		if (FormParameter.isCompatable(specifiedParameter, childParameter)) {
			return parameter;
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Iterator<T> i = specifiedParameter.iterator(); i.hasNext();) {
			str.append(i.next().shortName());
			if (i.hasNext()) {
				str.append('/');
			}
		}
		return str.toString();
	}
}
