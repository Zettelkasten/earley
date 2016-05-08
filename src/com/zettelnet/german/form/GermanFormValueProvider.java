package com.zettelnet.german.form;

import java.util.Collection;
import java.util.function.Function;

public interface GermanFormValueProvider<T> extends Function<GermanForm, Collection<T>> {

	Collection<T> getValue(final GermanForm key);

	@Override
	default Collection<T> apply(GermanForm key) {
		return getValue(key);
	}
}
