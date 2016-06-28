package com.zettelnet.earley.translate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;

public class SimpleTranslationTree<T, P extends Parameter, U, Q extends Parameter> implements TranslationTree<T, P, U, Q> {

	private final Set<TranslationTreeVariant<T, P, U, Q>> variants;
	
	@SafeVarargs
	public SimpleTranslationTree(TranslationTreeVariant<T, P, U, Q>... variants) {
		this.variants = new HashSet<>(Arrays.asList(variants));
	}
	
	@Override
	public Iterable<TranslationTreeVariant<T, P, U, Q>> getVariants() {
		return variants;
	}
	
	@Override
	public Set<TranslationTreeVariant<T, P, U, Q>> getVariantsSet() {
		return variants;
	}
}
