package com.zettelnet.earley.param.property;

import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;

public class SpecificPropertyParameterExpression<T, U extends Property> extends SpecificParameterExpression<T, PropertyParameter<U>> {

	public SpecificPropertyParameterExpression(final PropertyParameterManager<U> manager, final TokenParameterizer<T, PropertyParameter<U>> parameterizer, final PropertySet<U> values) {
		super(manager, parameterizer, manager.makeParameter(values));
	}
}
