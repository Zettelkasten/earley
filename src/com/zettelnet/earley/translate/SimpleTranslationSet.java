package com.zettelnet.earley.translate;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterFactory;

public class SimpleTranslationSet<T, P extends Parameter, U, Q extends Parameter> implements TranslationSet<T, P, U, Q> {

	private final Map<Production<T, P>, Set<Translation<T, P, U, Q>>> translations;

	public SimpleTranslationSet() {
		this.translations = new HashMap<>();
	}

	@Override
	public Set<Translation<T, P, U, Q>> getTranslations(Production<T, P> production) {
		if (translations.containsKey(production)) {
			return translations.get(production);
		} else {
			return Collections.emptySet();
		}
	}
	
	public Translation<T, P, U, Q> addTranslation(Translation<T, P, U, Q> translation) {
		Production<T, P> production = translation.key();
		if (!translations.containsKey(production)) {
			translations.put(production, new HashSet<>());
		}
		translations.get(production).add(translation);
		return translation;
	}
	
	public Translation<T, P, U, Q> addTranslation(Production<T, P> production, ParameterFactory<T, P> keyParameter, TranslationTree<T, P, U, Q> translationTree) {
		return addTranslation(new Translation<>(production, keyParameter, translationTree));
	}
}
