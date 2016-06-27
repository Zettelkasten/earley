package com.zettelnet.earley.translate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.token.TokenFactory;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class Translator<T, P extends Parameter, U, Q extends Parameter> {

	private final Map<Production<T, P>, Set<Translation<T, P, U, Q>>> translations;

	private final ParameterManager<T, P> parameterManager;
	private final TokenFactory<U, Q> tokenFactory;

	public Translator(final ParameterManager<T, P> parameterManager, final TokenFactory<U, Q> tokenFactory) {
		this.translations = new HashMap<>();
		this.parameterManager = parameterManager;
		this.tokenFactory = tokenFactory;
	}

	public SyntaxTree<U, Q> translate(SyntaxTree<T, P> sourceTree) {
		return new TranslatedSyntaxTree<>(this, sourceTree);
	}

	public Set<TranslationTree<T, P, U, Q>> getTranslationTrees(SyntaxTreeVariant<T, P> sourceVariant) {
		Production<T, P> production = sourceVariant.getProduction();
		P parameter = sourceVariant.getParameter();
		
		Set<TranslationTree<T, P, U, Q>> variantTranslations = new HashSet<>();
		for (Translation<T, P, U, Q> translation : translations.get(production)) {
			if (parameterManager.isCompatible(translation.keyParameter(), parameter)) {
				variantTranslations.add(translation.value());
			}
		}
		return variantTranslations;
	}

	public Collection<U> makeToken(Terminal<U> symbol, Q parameter) {
		return tokenFactory.makeToken(parameter);
	}
	
	public Translation<T, P, U, Q> addTranslation(Translation<T, P, U, Q> translation) {
		Production<T, P> production = translation.key();
		if (!translations.containsKey(production)) {
			translations.put(production, new HashSet<>());
		}
		translations.get(production).add(translation);
		return translation;
	}
}
