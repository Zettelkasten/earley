package com.zettelnet.latin.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapFormValueProvider<T> implements FormValueProvider<T> {

	private final Map<Form, Collection<T>> data;
	private final Collection<Class<? extends FormProperty>> retainedProperties;

	protected MapFormValueProvider(Collection<Class<? extends FormProperty>> retainedProperties) {
		this(new HashMap<>(), retainedProperties);
	}

	public MapFormValueProvider(final Map<Form, Collection<T>> data, final Collection<Class<? extends FormProperty>> retainedProperties) {
		this.data = data;
		this.retainedProperties = retainedProperties;
	}

	protected void put(T value, FormProperty... formProperties) {
		this.data.put(Form.withValues(formProperties), Arrays.asList(value));
	}

	protected void put(T[] values, FormProperty... formProperties) {
		this.data.put(Form.withValues(formProperties), Arrays.asList(values));
	}

	public Collection<T> getMorph(Form form) {
		Collection<T> variants = data.get(form.retainAll(retainedProperties));
		if (variants != null) {
			return variants;
		} else {
			return Collections.emptyList();
		}
	}
}
