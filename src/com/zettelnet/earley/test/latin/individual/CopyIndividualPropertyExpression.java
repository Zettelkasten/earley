package com.zettelnet.earley.test.latin.individual;

import java.util.Set;

import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.latin.form.FormProperty;

public class CopyIndividualPropertyExpression implements IndividualPropertyExpression {

	@Override
	public Set<FormProperty> predict(Set<FormProperty> parameter, Set<FormProperty> childParameter) {
		return FormParameter.deriveProperties(parameter, childParameter);
	}

	@Override
	public Set<FormProperty> scan(Set<FormProperty> parameter, Set<FormProperty> tokenParameter) {
		return FormParameter.deriveProperties(parameter, tokenParameter);
	}

	@Override
	public Set<FormProperty> complete(Set<FormProperty> parameter, Set<FormProperty> childParameter) {
		return FormParameter.deriveProperties(parameter, childParameter);
	}

}
