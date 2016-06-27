package com.zettelnet.earley.translate;

import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;

public interface TranslationTree<T, P extends Parameter, U, Q extends Parameter> {

	Iterable<TranslationTreeVariant<T, P, U, Q>> getVariants();

	default Set<TranslationTreeVariant<T, P, U, Q>> getVariantsSet() {
		Iterable<TranslationTreeVariant<T, P, U, Q>> iterable = getVariants();
		if (iterable instanceof Set) {
			return (Set<TranslationTreeVariant<T, P, U, Q>>) iterable;
		} else {
			Set<TranslationTreeVariant<T, P, U, Q>> set = new HashSet<>();
			for (TranslationTreeVariant<T, P, U, Q> element : getVariants()) {
				set.add(element);
			}
			return set;
		}
	}

	default Set<TranslationTreeVariant<T, P, U, Q>> getVariantsSet(int maxAmount) {
		Set<TranslationTreeVariant<T, P, U, Q>> set = new HashSet<>();
		for (TranslationTreeVariant<T, P, U, Q> element : getVariants()) {
			if (--maxAmount < 0) {
				break;
			}
			set.add(element);
		}
		return set;
	}
}
