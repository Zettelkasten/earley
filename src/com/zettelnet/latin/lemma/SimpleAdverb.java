package com.zettelnet.latin.lemma;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.latin.form.Form;

public class SimpleAdverb implements Lemma {

	private final String firstForm;

	public SimpleAdverb(final String firstForm) {
		this.firstForm = firstForm;
	}

	@Override
	public String getFirstForm() {
		return firstForm;
	}

	@Override
	public Collection<String> getForm(Form form) {
		return Arrays.asList(firstForm);
	}

	@Override
	public boolean hasForm(Form form) {
		return true;
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		Map<Form, Collection<String>> map = new HashMap<>(1);
		map.put(Form.withValues(), Arrays.asList(firstForm));
		return map;
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Adverb;
	}

	@Override
	public String toString() {
		return getFirstForm();
	}
}
