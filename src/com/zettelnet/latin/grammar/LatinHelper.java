package com.zettelnet.latin.grammar;

import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.Production;
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
import com.zettelnet.earley.translate.AbstractTranslationTree;
import com.zettelnet.earley.translate.ConcreteTranslationTree;
import com.zettelnet.earley.translate.ParameterTranslator;
import com.zettelnet.earley.translate.PositionReference;
import com.zettelnet.earley.translate.SimpleTranslationSet;
import com.zettelnet.earley.translate.SimpleTranslationTree;
import com.zettelnet.earley.translate.TranslationTree;
import com.zettelnet.earley.translate.TranslationTreeVariant;
import com.zettelnet.german.token.GermanToken;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.individual.IndividualFormParameterExpression;
import com.zettelnet.latin.token.Token;

final class LatinHelper {

	private LatinHelper() {
	}

	@SafeVarargs
	static ParameterExpression<Token, FormParameter> copy(ParameterManager<Token, FormParameter> parameterManager, TokenParameterizer<Token, FormParameter> parameterizer, Object... propertyTypes) {
		IndividualFormParameterExpression<Token> expression = new IndividualFormParameterExpression<>(parameterManager, parameterizer);
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

	@SafeVarargs
	static TranslationTree<Token, FormParameter, GermanToken, FormParameter> translate(TranslationTreeVariant<Token, FormParameter, GermanToken, FormParameter>... variants) {
		return new SimpleTranslationTree<>(variants);
	}

	static void makeOptional(SimpleGrammar<Token, FormParameter> grammar, NonTerminal<Token> optionalSymbol, Symbol<Token> symbol, ParameterExpression<Token, FormParameter> expression, NonTerminal<GermanToken> translatedOptional, Symbol<GermanToken> translatedSymbol, SimpleTranslationSet<Token, FormParameter, GermanToken, FormParameter> translations, ParameterTranslator<Token, FormParameter, FormParameter> parameterTranslator) {
		Production<Token, FormParameter> prod;
		
		prod = grammar.addProduction(optionalSymbol);
		translations.addTranslation(prod, grammar.getParameterManager(), translate(
				new ConcreteTranslationTree<>(translatedOptional, parameterTranslator)));
		prod = grammar.addProduction(optionalSymbol, new ParameterizedSymbol<>(symbol, expression));
		translations.addTranslation(prod, grammar.getParameterManager(), translate(
				new ConcreteTranslationTree<>(translatedOptional, parameterTranslator, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
	}
}
