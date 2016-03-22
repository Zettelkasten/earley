package com.zettelnet.earley.test.latin.individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.latin.form.FormProperty;

public class IndividualFormParameterExpression<T> implements ParameterExpression<T, FormParameter> {

	private static final IndividualPropertyExpression COPY = new CopyIndividualPropertyExpression();

	private final TokenParameterizer<T, FormParameter> parameterizer;

	private final Map<Class<? extends FormProperty>, IndividualPropertyExpression> handlers;

	public IndividualFormParameterExpression(final TokenParameterizer<T, FormParameter> parameterizer) {
		this.parameterizer = parameterizer;
		this.handlers = new HashMap<>();
	}

	public IndividualFormParameterExpression<T> copy(Class<? extends FormProperty> propertyType) {
		handlers.put(propertyType, COPY);

		return this;
	}

	public IndividualFormParameterExpression<T> specify(FormProperty... properties) {
		Map<Class<? extends FormProperty>, Set<FormProperty>> specified = new HashMap<>();

		for (FormProperty property : properties) {
			Class<? extends FormProperty> propertyType = property.getClass();
			if (!specified.containsKey(propertyType)) {
				specified.put(propertyType, new HashSet<>());
			}
			specified.get(propertyType).add(property);
		}

		for (Map.Entry<Class<? extends FormProperty>, Set<FormProperty>> entry : specified.entrySet()) {
			handlers.put(entry.getKey(), new SpecificIndividualPropertyExpression(entry.getValue()));
		}

		return this;
	}

	@Override
	public Collection<FormParameter> predict(FormParameter parameter, FormParameter childParameter) {
		Map<Class<? extends FormProperty>, Set<FormProperty>> newData = new HashMap<>(childParameter.getProperties());
		for (Map.Entry<Class<? extends FormProperty>, IndividualPropertyExpression> entry : handlers.entrySet()) {
			Class<? extends FormProperty> propertyType = entry.getKey();
			IndividualPropertyExpression expression = entry.getValue();
			Set<FormProperty> properties = expression.predict(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
			if (properties.isEmpty()) {
				return Collections.emptyList();
			} else {
				newData.put(entry.getKey(), properties);
			}
		}
		return Arrays.asList(new FormParameter(newData));
	}

	@Override
	public Collection<FormParameter> scan(FormParameter parameter, T token, Terminal<T> terminal) {
		Collection<FormParameter> results = new ArrayList<>();
		for (FormParameter tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			Map<Class<? extends FormProperty>, Set<FormProperty>> newData = new HashMap<>(parameter.getProperties());
			for (Map.Entry<Class<? extends FormProperty>, IndividualPropertyExpression> entry : handlers.entrySet()) {
				Class<? extends FormProperty> propertyType = entry.getKey();
				IndividualPropertyExpression expression = entry.getValue();
				Set<FormProperty> properties = expression.scan(parameter.getProperty(propertyType), tokenParameter.getProperty(propertyType));
				if (properties.isEmpty()) {
					return Collections.emptyList();
				} else {
					newData.put(entry.getKey(), properties);
				}
			}
			results.add(new FormParameter(newData));
		}
		return results;
	}

	@Override
	public Collection<FormParameter> complete(FormParameter parameter, FormParameter childParameter) {
		Map<Class<? extends FormProperty>, Set<FormProperty>> newData = new HashMap<>(parameter.getProperties());
		for (Map.Entry<Class<? extends FormProperty>, IndividualPropertyExpression> entry : handlers.entrySet()) {
			Class<? extends FormProperty> propertyType = entry.getKey();
			IndividualPropertyExpression expression = entry.getValue();
			Set<FormProperty> properties = expression.complete(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
			if (properties.isEmpty()) {
				return Collections.emptyList();
			} else {
				newData.put(entry.getKey(), properties);
			}
		}
		return Arrays.asList(new FormParameter(newData));
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Map.Entry<Class<? extends FormProperty>, IndividualPropertyExpression> entry : handlers.entrySet()) {
			str.append(' ');
			str.append(entry.getValue().toString(entry.getKey()));
		}

		if (str.length() == 0) {
			return "?";
		} else {
			return str.substring(1);
		}
	}
}
