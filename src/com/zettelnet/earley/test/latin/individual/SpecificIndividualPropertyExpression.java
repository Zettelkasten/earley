package com.zettelnet.earley.test.latin.individual;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.latin.form.FormProperty;

public class SpecificIndividualPropertyExpression implements IndividualPropertyExpression {

	private final Set<FormProperty> specifiedParameter;
	
	public SpecificIndividualPropertyExpression(final Set<FormProperty> parameter) {
		this.specifiedParameter = parameter;
	}
	
	@Override
	public Set<FormProperty> predict(Set<FormProperty> parameter, Set<FormProperty> childParameter) {
		return FormParameter.deriveProperties(specifiedParameter, childParameter);
	}

	@Override
	public Set<FormProperty> scan(Set<FormProperty> parameter, Set<FormProperty> tokenParameter) {
		return FormParameter.deriveProperties(specifiedParameter, tokenParameter);
	}

	@Override
	public Set<FormProperty> complete(Set<FormProperty> parameter, Set<FormProperty> childParameter) {
		if (FormParameter.isCompatable(specifiedParameter, childParameter)) {
			return parameter;
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Iterator<FormProperty> i = specifiedParameter.iterator(); i.hasNext();) {
			str.append(i.next().shortName());
			if (i.hasNext()) {
				str.append('/');
			}
		}
		return str.toString();
	}
}
