package com.zettelnet.german.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public class MapGermanFormValueProvider<T> implements GermanFormValueProvider<T> {

	private final Map<GermanForm, Collection<T>> data;
	private final Collection<ValuesPropertyType<?>> retainedProperties;

	protected MapGermanFormValueProvider(Collection<ValuesPropertyType<?>> retainedProperties) {
		this(new HashMap<>(), retainedProperties);
	}

	public MapGermanFormValueProvider(final Map<GermanForm, Collection<T>> data, final Collection<ValuesPropertyType<?>> retainedProperties) {
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

	@Override
	public Map<GermanForm, Collection<T>> getValues() {
		return data;
	}
	
	@Override
	public boolean hasValue(GermanForm key) {
		return data.containsKey(key);
	}
}
