package com.zettelnet.latin.form;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface FormValueProvider<T> extends Function<Form, Collection<T>> {

	Collection<T> getValue(final Form key);

	boolean hasValue(final Form key);
	
	Map<Form, Collection<T>> getValues();
	
	@Override
	default Collection<T> apply(Form key) {
		return getValue(key);
	}
}
