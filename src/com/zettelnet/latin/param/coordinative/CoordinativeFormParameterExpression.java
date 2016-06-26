package com.zettelnet.latin.param.coordinative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.latin.param.FormParameter;

/**
 * Represents a {@link ParameterExpression} that coordinates multiple
 * {@link SubParameterExpression}s.
 */
public class CoordinativeFormParameterExpression<T> implements ParameterExpression<T, FormParameter> {

	private static final SubParameterExpression COPY_ALL = new CopySubParameterExpression();

	private final ParameterManager<T, FormParameter> parameterManager;
	private final TokenParameterizer<T, FormParameter> parameterizer;

	private final Set<SubParameterExpression> handlers;

	public CoordinativeFormParameterExpression(final ParameterManager<T, FormParameter> parameterManager, final TokenParameterizer<T, FormParameter> parameterizer) {
		this.parameterManager = parameterManager;
		this.parameterizer = parameterizer;
		this.handlers = new HashSet<>();
	}

	public CoordinativeFormParameterExpression<T> copyAll() {
		handlers.add(COPY_ALL);
		return this;
	}

	public CoordinativeFormParameterExpression<T> copy(Object... propertyTypes) {
		handlers.add(new CopySubParameterExpression(propertyTypes));
		return this;
	}

	public CoordinativeFormParameterExpression<T> with(SubParameterExpression expression) {
		handlers.add(expression);
		return this;
	}

	@SuppressWarnings("unchecked")
	private Collection<FormParameter> call(FormParameter parameter, FormParameter childParameter, NonTerminal<T> parameterSymbol, Function<SubParameterExpression, Map<Object, Set<? extends Property>>> toCall) {
		Map<Object, Set<? extends Property>> data = new HashMap<>();

		for (SubParameterExpression subExpression : handlers) {
			Map<Object, Set<? extends Property>> computed = toCall.apply(subExpression);

			for (Map.Entry<Object, Set<? extends Property>> computedEntry : computed.entrySet()) {
				Object propertyType = computedEntry.getKey();
				Set<? extends Property> properties = computedEntry.getValue();

				data.put(propertyType, FormParameter.deriveProperties((Set<Property>) data.get(propertyType), (Set<Property>) properties));
			}
		}

		return Arrays.asList(parameterManager.copyParameter(new FormParameter(data), parameterSymbol));
	}

	@Override
	public Collection<FormParameter> predict(FormParameter parameter, FormParameter childParameter, NonTerminal<T> childSymbol) {
		return call(parameter, childParameter, childSymbol, (SubParameterExpression subExpression) -> {
			return subExpression.predict(parameter, childParameter);
		});
	}

	@Override
	public Collection<FormParameter> scan(FormParameter parameter, NonTerminal<T> parentSymbol, T token, Terminal<T> terminal) {
		Collection<FormParameter> results = new ArrayList<>();
		for (FormParameter tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			Collection<FormParameter> parameterResult = call(parameter, tokenParameter, parentSymbol, (SubParameterExpression subExpression) -> {
				return subExpression.predict(parameter, tokenParameter);
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
	public Collection<FormParameter> complete(FormParameter parameter, NonTerminal<T> parentSymbol, FormParameter childParameter) {
		return call(parameter, childParameter, parentSymbol, (SubParameterExpression subExpression) -> {
			return subExpression.predict(parameter, childParameter);
		});
	}

	@Override
	public String toString() {
		if (handlers.isEmpty()) {
			return "?";
		} else {
			StringBuilder str = new StringBuilder();
			for (SubParameterExpression subExpression : handlers) {
				str.append(" & ");
				str.append(subExpression);
			}
			return str.substring(3);
		}
	}
}
