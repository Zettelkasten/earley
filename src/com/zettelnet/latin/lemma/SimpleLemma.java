package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.zettelnet.latin.form.Form;

public class SimpleLemma implements Lemma {

	private final String firstForm;
	private final LemmaType type;

	public SimpleLemma(final LemmaType type) {
		this(UUID.randomUUID().toString(), type);
	}

	public SimpleLemma(final String firstForm, final LemmaType type) {
		this.firstForm = firstForm;
		this.type = type;
	}

	@Override
	public String getFirstForm() {
		return firstForm;
	}

	@Override
	public Collection<String> getForm(Form form) {
		return Collections.emptyList();
	}

	@Override
	public boolean hasForm(Form form) {
		return false;
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		return Collections.emptyMap();
	}

	@Override
	public LemmaType getType() {
		return type;
	}
}
