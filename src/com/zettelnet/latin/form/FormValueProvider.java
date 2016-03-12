package com.zettelnet.latin.form;

import java.util.Collection;
import java.util.function.Function;

public interface FormValueProvider<T> extends Function<Form, Collection<T>> {

	Collection<T> getMorph(final Form key);

	@Override
	default Collection<T> apply(Form key) {
		return getMorph(key);
	}
}
