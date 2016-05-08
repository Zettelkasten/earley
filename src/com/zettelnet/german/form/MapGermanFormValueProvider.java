package com.zettelnet.german.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapGermanFormValueProvider<T> implements GermanFormValueProvider<T> {

	private final Map<GermanForm, Collection<T>> data;
	private final Collection<Class<? extends GermanFormProperty>> retainedProperties;

	protected MapGermanFormValueProvider(Collection<Class<? extends GermanFormProperty>> retainedProperties) {
		this(new HashMap<>(), retainedProperties);
	}

	public MapGermanFormValueProvider(final Map<GermanForm, Collection<T>> data, final Collection<Class<? extends GermanFormProperty>> retainedProperties) {
		this.data = data;
		this.retainedProperties = retainedProperties;
	}

	public void put(T value, GermanFormProperty... formProperties) {
		this.data.put(GermanForm.withValues(formProperties), Arrays.asList(value));
	}

	public void put(T[] values, GermanFormProperty... formProperties) {
		this.data.put(GermanForm.withValues(formProperties), Arrays.asList(values));
	}
	
	@Override
	public Collection<T> getValue(GermanForm form) {
		Collection<T> variants = data.get(form.retainAll(retainedProperties));
		if (variants != null) {
			return variants;
		} else {
			return Collections.emptyList();
		}
	}
	
	public Map<GermanForm, Collection<T>> getValues() {
		return data;
	}
}
