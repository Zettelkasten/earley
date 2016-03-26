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
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.latin.FormParameter;

public class IndividualFormParameterExpression<T> implements ParameterExpression<T, FormParameter> {

	private static final IndividualPropertyExpression COPY = new CopyIndividualPropertyExpression();

	private final TokenParameterizer<T, FormParameter> parameterizer;

	private final Map<Class<? extends Property>, IndividualPropertyExpression> handlers;

	public IndividualFormParameterExpression(final TokenParameterizer<T, FormParameter> parameterizer) {
		this.parameterizer = parameterizer;
		this.handlers = new HashMap<>();
	}

	public IndividualFormParameterExpression<T> copy(Class<? extends Property> propertyType) {
		handlers.put(propertyType, COPY);

		return this;
	}

	public IndividualFormParameterExpression<T> specify(Property... properties) {
		Map<Class<? extends Property>, Set<Property>> specified = new HashMap<>();

		for (Property property : properties) {
			Class<? extends Property> propertyType = property.getClass();
			if (!specified.containsKey(propertyType)) {
				specified.put(propertyType, new HashSet<>());
			}
			specified.get(propertyType).add(property);
		}

		for (Map.Entry<Class<? extends Property>, Set<Property>> entry : specified.entrySet()) {
			handlers.put(entry.getKey(), new SpecificIndividualPropertyExpression(entry.getValue()));
		}

		return this;
	}

	@Override
	public Collection<FormParameter> predict(FormParameter parameter, FormParameter childParameter) {
		Map<Class<? extends Property>, Set<Property>> newData = new HashMap<>(childParameter.getProperties());
		for (Map.Entry<Class<? extends Property>, IndividualPropertyExpression> entry : handlers.entrySet()) {
			Class<? extends Property> propertyType = entry.getKey();
			IndividualPropertyExpression expression = entry.getValue();
			Set<Property> properties = expression.predict(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
			if (properties != null) {
				if (properties.isEmpty()) {
					return Collections.emptyList();
				} else {
					newData.put(entry.getKey(), properties);
				}
			}
		}
		return Arrays.asList(new FormParameter(newData));
	}

	@Override
	public Collection<FormParameter> scan(FormParameter parameter, T token, Terminal<T> terminal) {
		Collection<FormParameter> results = new ArrayList<>();
		for (FormParameter tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			Map<Class<? extends Property>, Set<Property>> newData = new HashMap<>(parameter.getProperties());
			for (Map.Entry<Class<? extends Property>, IndividualPropertyExpression> entry : handlers.entrySet()) {
				Class<? extends Property> propertyType = entry.getKey();
				IndividualPropertyExpression expression = entry.getValue();
				Set<Property> properties = expression.scan(parameter.getProperty(propertyType), tokenParameter.getProperty(propertyType));
				if (properties != null) {
					if (properties.isEmpty()) {
						return Collections.emptyList();
					} else {
						newData.put(entry.getKey(), properties);
					}
				}
			}
			results.add(new FormParameter(newData));
		}
		return results;
	}

	@Override
	public Collection<FormParameter> complete(FormParameter parameter, FormParameter childParameter) {
		Map<Class<? extends Property>, Set<Property>> newData = new HashMap<>(parameter.getProperties());
		for (Map.Entry<Class<? extends Property>, IndividualPropertyExpression> entry : handlers.entrySet()) {
			Class<? extends Property> propertyType = entry.getKey();
			IndividualPropertyExpression expression = entry.getValue();
			Set<Property> properties = expression.complete(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
			if (properties != null) {
				if (properties.isEmpty()) {
					return Collections.emptyList();
				} else {
					newData.put(entry.getKey(), properties);
				}
			}
		}
		return Arrays.asList(new FormParameter(newData));
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Map.Entry<Class<? extends Property>, IndividualPropertyExpression> entry : handlers.entrySet()) {
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
