package com.zettelnet.latin.grammar;

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
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.individual.IndividualFormParameterExpression;
import com.zettelnet.latin.token.Token;

final class LatinHelper {

	private LatinHelper() {
	}

	@SafeVarargs
	static ParameterExpression<Token, FormParameter> copy(TokenParameterizer<Token, FormParameter> parameterizer, Object... propertyTypes) {
		IndividualFormParameterExpression<Token> expression = new IndividualFormParameterExpression<>(parameterizer);
		for (Object propertyType : propertyTypes) {
			expression.copy(propertyType);
		}
		return expression;
	}

	static ParameterExpression<Token, FormParameter> specify(ParameterManager<Token, FormParameter> parameterManager, TokenParameterizer<Token, FormParameter> parameterizer, Property... formProperties) {
		return new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(formProperties));
	}

	static ParameterFactory<Token, FormParameter> key(Property... formProperties) {
		return new SingletonParameterFactory<>(new FormParameter(formProperties));
	}
	
	static void makeOptional(SimpleGrammar<Token, FormParameter> grammar, NonTerminal<Token> optionalSymbol, Symbol<Token> symbol, ParameterExpression<Token, FormParameter> expression) {
		grammar.addProduction(optionalSymbol);
		grammar.addProduction(optionalSymbol, new ParameterizedSymbol<>(symbol, expression));
	}
}
