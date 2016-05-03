package com.zettelnet.latin.param.individual;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

public class SpecificIndividualPropertyExpression implements IndividualPropertyExpression {

	private final Set<Property> specifiedParameter;

	public SpecificIndividualPropertyExpression(final Set<Property> parameter) {
		this.specifiedParameter = parameter;
	}

	@Override
	public Set<Property> predict(Set<Property> parameter, Set<Property> childParameter) {
		return FormParameter.deriveProperties(specifiedParameter, childParameter);
	}

	@Override
	public Set<Property> scan(Set<Property> parameter, Set<Property> tokenParameter) {
		return FormParameter.deriveProperties(specifiedParameter, tokenParameter);
	}

	@Override
	public Set<Property> complete(Set<Property> parameter, Set<Property> childParameter) {
		if (FormParameter.isCompatable(specifiedParameter, childParameter)) {
			return parameter;
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Iterator<Property> i = specifiedParameter.iterator(); i.hasNext();) {
			str.append(i.next().shortName());
			if (i.hasNext()) {
				str.append('/');
			}
		}
		return str.toString();
	}
}
