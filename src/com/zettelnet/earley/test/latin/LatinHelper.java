package com.zettelnet.earley.test.latin;

import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.test.latin.individual.IndividualFormParameterExpression;

public final class LatinHelper {

	private LatinHelper() {
	}

	@SafeVarargs
	static ParameterExpression<Token, FormParameter> copy(TokenParameterizer<Token, FormParameter> parameterizer, Class<? extends Property>... propertyTypes) {
		IndividualFormParameterExpression<Token> expression = new IndividualFormParameterExpression<>(parameterizer);
		for (Class<? extends Property> propertyType : propertyTypes) {
			expression.copy(propertyType);
		}
		return expression;
	}

	static ParameterExpression<Token, FormParameter> specify(ParameterManager<FormParameter> parameterManager, TokenParameterizer<Token, FormParameter> parameterizer, Property... formProperties) {
		return new SpecificParameterExpression<Token, FormParameter>(parameterManager, parameterizer, new FormParameter(formProperties));
	}

	static ParameterFactory<FormParameter> key(Property... formProperties) {
		return new SingletonParameterFactory<FormParameter>(new FormParameter(formProperties));
	}
	
	static void makeOptional(SimpleGrammar<Token, FormParameter> grammar, NonTerminal<Token> optionalSymbol, NonTerminal<Token> symbol, ParameterExpression<Token, FormParameter> expression) {
		grammar.addProduction(optionalSymbol);
		grammar.addProduction(optionalSymbol, new ParameterizedSymbol<>(symbol, expression));
	}
}
