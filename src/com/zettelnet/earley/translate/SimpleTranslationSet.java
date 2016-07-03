
package com.zettelnet.earley.translate;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.symbol.Terminal;

public class SimpleTranslationSet<T, P extends Parameter, U, Q extends Parameter> implements TranslationSet<T, P, U, Q> {

	private final Map<Production<T, P>, Set<Translation<T, P, U, Q>>> translations;
	private final Map<Terminal<T>, Set<Translation<T, P, U, Q>>> terminalTranslations;
	
	public SimpleTranslationSet() {
		this.translations = new HashMap<>();
		this.terminalTranslations = new HashMap<>();
	}

	@Override
	public Set<Translation<T, P, U, Q>> getTranslations(Production<T, P> production) {
		if (translations.containsKey(production)) {
			return translations.get(production);
		} else {
			return Collections.emptySet();
		}
	}

	public Translation<T, P, U, Q> addTranslation(Production<T, P> production, Translation<T, P, U, Q> translation) {
		if (!translations.containsKey(production)) {
			translations.put(production, new HashSet<>());
		}
		translations.get(production).add(translation);
		return translation;
	}

	public Translation<T, P, U, Q> addTranslation(Production<T, P> production, ParameterFactory<T, P> keyParameter, TranslationTree<T, P, U, Q> translationTree) {
		return addTranslation(production, new Translation<>(production.key(), keyParameter, translationTree));
	}

	@Override
	public Set<Translation<T, P, U, Q>> getTranslations(Terminal<T> symbol) {
		if (terminalTranslations.containsKey(symbol)) {
			return terminalTranslations.get(symbol);
		} else {
			return Collections.emptySet();
		}
	}
	
	public Translation<T, P, U, Q> addTranslation(Terminal<T> symbol, Translation<T, P, U, Q> translation) {
		if (!terminalTranslations.containsKey(symbol)) {
			terminalTranslations.put(symbol, new HashSet<>());
		}
		terminalTranslations.get(symbol).add(translation);
		return translation;
	}

	public Translation<T, P, U, Q> addTranslation(Terminal<T> symbol, ParameterFactory<T, P> keyParameter, TranslationTree<T, P, U, Q> translationTree) {
		return addTranslation(symbol, new Translation<>(symbol, keyParameter, translationTree));
	}
}
