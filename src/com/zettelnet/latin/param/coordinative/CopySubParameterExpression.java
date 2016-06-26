package com.zettelnet.latin.param.coordinative;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

public class CopySubParameterExpression implements SubParameterExpression {

	// if null every type is copied
	private final Set<Object> copiedTypes;

	public CopySubParameterExpression(Object... copiedTypes) {
		this.copiedTypes = new HashSet<>(Arrays.asList(copiedTypes));
	}

	public CopySubParameterExpression() {
		this.copiedTypes = null;
	}

	private Map<Object, Set<? extends Property>> handle(FormParameter parameter, FormParameter childParameter) {
		return FormParameter.deriveProperties(parameter.getProperties(), childParameter.getProperties(), copiedTypes);
	}

	@Override
	public Map<Object, Set<? extends Property>> predict(FormParameter parameter, FormParameter childParameter) {
		return handle(parameter, childParameter);
	}

	@Override
	public Map<Object, Set<? extends Property>> scan(FormParameter parameter, FormParameter childParameter) {
		return handle(parameter, childParameter);
	}

	@Override
	public Map<Object, Set<? extends Property>> complete(FormParameter parameter, FormParameter childParameter) {
		return handle(parameter, childParameter);
	}

	@Override
	public String toString() {
		if (copiedTypes == null) {
			return "&pi;";
		} else {
			StringBuilder str = new StringBuilder();
			for (Object copiedType : copiedTypes) {
				str.append(copiedType);
			}
			str.append("[&pi;]");
			return str.toString();
		}
	}
}
