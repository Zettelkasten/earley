package com.zettelnet.earley.translate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.token.TokenFactory;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class SimpleTranslator<T, P extends Parameter, U, Q extends Parameter> implements Translator<T, P, U, Q> {

	private final ParameterManager<T, P> parameterManager;
	private final TokenFactory<U, Q> tokenFactory;

	private final TranslationSet<T, P, U, Q> translations;

	public SimpleTranslator(final Grammar<T, P> sourceGrammar, final TokenFactory<U, Q> tokenFactory, final TranslationSet<T, P, U, Q> translations) {
		this.parameterManager = sourceGrammar.getParameterManager();
		this.tokenFactory = tokenFactory;
		this.translations = translations;
	}

	@Override
	public SyntaxTree<U, Q> translate(SyntaxTree<T, P> sourceTree) {
		return new TranslatedSyntaxTree<>(this, sourceTree);
	}

	@Override
	public Set<TranslationTree<T, P, U, Q>> getTranslationTrees(SyntaxTreeVariant<T, P> sourceVariant) {
		P parameter = sourceVariant.getParameter();
		Set<TranslationTree<T, P, U, Q>> variantTranslations = new HashSet<>();
		for (Translation<T, P, U, Q> translation : getTranslations(sourceVariant)) {
			if (parameterManager.isCompatible(translation.keyParameter(), parameter)) {
				variantTranslations.add(translation.value());
			}
		}
		return variantTranslations;
	}

	private final Set<Translation<T, P, U, Q>> getTranslations(SyntaxTreeVariant<T, P> sourceVariant) {
		if (sourceVariant.isTerminal()) {
			return translations.getTranslations((Terminal<T>) sourceVariant.getRootSymbol());
		} else {
			return translations.getTranslations(sourceVariant.getProduction());
		}
	}

	@Override
	public Collection<U> makeToken(Terminal<U> symbol, Q parameter) {
		return tokenFactory.makeToken(parameter);
	}
}
