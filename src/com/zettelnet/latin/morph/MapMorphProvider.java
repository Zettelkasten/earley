package com.zettelnet.latin.morph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public class MapMorphProvider implements MorphProvider {

	private final Map<Form, Collection<String>> data;
	private final Collection<Class<? extends FormProperty>> retainedProperties;

	protected MapMorphProvider(Collection<Class<? extends FormProperty>> retainedProperties) {
		this(new HashMap<>(), retainedProperties);
	}

	public MapMorphProvider(final Map<Form, Collection<String>> data, final Collection<Class<? extends FormProperty>> retainedProperties) {
		this.data = data;
		this.retainedProperties = retainedProperties;
	}

	protected void put(String form, FormProperty... formProperties) {
		this.data.put(Form.withValues(formProperties), Arrays.asList(form));
	}

	protected void put(String[] formVariants, FormProperty... formProperties) {
		this.data.put(Form.withValues(formProperties), Arrays.asList(formVariants));
	}

	@Override
	public Collection<String> getMorph(Form form) {
		Collection<String> variants = data.get(form.retainAll(retainedProperties));
		if (variants != null) {
			return variants;
		} else {
			return Collections.emptyList();
		}
	}

}
