package com.zettelnet.latin.param.individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.latin.param.FormParameter;

public class IndividualFormParameterExpression<T> implements ParameterExpression<T, FormParameter> {

	private static final IndividualPropertyExpression<?> COPY = new CopyIndividualPropertyExpression<>();

	private final TokenParameterizer<T, FormParameter> parameterizer;

	private final Map<Object, IndividualPropertyExpression<?>> handlers;

	public IndividualFormParameterExpression(final TokenParameterizer<T, FormParameter> parameterizer) {
		this.parameterizer = parameterizer;
		this.handlers = new HashMap<>();
	}

	public IndividualFormParameterExpression<T> copy(Object propertyType) {
		handlers.put(propertyType, COPY);

		return this;
	}

	@SafeVarargs
	public final IndividualFormParameterExpression<T> copy(Object... propertyTypes) {
		for (Object type : propertyTypes) {
			handlers.put(type, COPY);
		}

		return this;
	}

	public IndividualFormParameterExpression<T> specify(Property... properties) {
		Map<Object, Set<Property>> specified = new HashMap<>();

		for (Property property : properties) {
			Object propertyType = property.getType();
			if (!specified.containsKey(propertyType)) {
				specified.put(propertyType, new HashSet<>());
			}
			specified.get(propertyType).add(property);
		}

		for (Map.Entry<Object, Set<Property>> entry : specified.entrySet()) {
			handlers.put(entry.getKey(), new SpecificIndividualPropertyExpression<>(entry.getValue()));
		}

		return this;
	}

	// caller must ensure expression is compatible with propertyType
	public IndividualFormParameterExpression<T> with(Object propertyType, IndividualPropertyExpression<?> expression) {
		handlers.put(propertyType, expression);

		return this;
	}

	private Collection<FormParameter> call(FormParameter parameter, FormParameter childParameter, BiFunction<Object, IndividualPropertyExpression<?>, Set<? extends Property>> toCall) {
		Map<Object, Set<? extends Property>> newData = new HashMap<>(childParameter.getProperties());
		for (Map.Entry<Object, IndividualPropertyExpression<?>> entry : handlers.entrySet()) {
			Object propertyType = entry.getKey();
			IndividualPropertyExpression<?> expression = entry.getValue();
			Set<? extends Property> properties = expression.predict(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
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
	public Collection<FormParameter> predict(FormParameter parameter, FormParameter childParameter) {
		return call(parameter, childParameter, (Object propertyType, IndividualPropertyExpression<?> expression) -> {
			return expression.predict(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
		});
	}

	@Override
	public Collection<FormParameter> scan(FormParameter parameter, T token, Terminal<T> terminal) {
		Collection<FormParameter> results = new ArrayList<>();
		for (FormParameter tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			Collection<FormParameter> parameterResult = call(parameter, tokenParameter, (Object propertyType, IndividualPropertyExpression<?> expression) -> {
				return expression.scan(parameter.getProperty(propertyType), tokenParameter.getProperty(propertyType));
			});

			if (parameterResult.isEmpty()) {
				return Collections.emptyList();
			} else {
				results.addAll(parameterResult);
			}
		}
		return results;
	}

	@Override
	public Collection<FormParameter> complete(FormParameter parameter, FormParameter childParameter) {
		return call(parameter, childParameter, (Object propertyType, IndividualPropertyExpression<?> expression) -> {
			return expression.complete(parameter.getProperty(propertyType), childParameter.getProperty(propertyType));
		});
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Map.Entry<Object, IndividualPropertyExpression<?>> entry : handlers.entrySet()) {
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
