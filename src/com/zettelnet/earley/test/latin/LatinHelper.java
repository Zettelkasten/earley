package com.zettelnet.earley.test.latin;

import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.test.latin.individual.IndividualFormParameterExpression;
import com.zettelnet.latin.form.FormProperty;

public final class LatinHelper {

	private LatinHelper() {
	}

	@SafeVarargs
	static ParameterExpression<Token, FormParameter> copy(TokenParameterizer<Token, FormParameter> parameterizer, Class<? extends FormProperty>... propertyTypes) {
		IndividualFormParameterExpression<Token> expression = new IndividualFormParameterExpression<>(parameterizer);
		for (Class<? extends FormProperty> propertyType : propertyTypes) {
			expression.copy(propertyType);
		}
		return expression;
	}

	static ParameterExpression<Token, FormParameter> specify(ParameterManager<FormParameter> parameterManager, TokenParameterizer<Token, FormParameter> parameterizer, FormProperty... formProperties) {
		return new SpecificParameterExpression<Token, FormParameter>(parameterManager, parameterizer, new FormParameter(formProperties));
	}

	static ParameterFactory<FormParameter> key(FormProperty... formProperties) {
		return new SingletonParameterFactory<FormParameter>(new FormParameter(formProperties));
	}
}
