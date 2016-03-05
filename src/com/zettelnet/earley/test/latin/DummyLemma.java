package com.zettelnet.earley.test.latin;

import java.util.Map;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;

public class DummyLemma implements Lemma {

	private final LemmaType type;

	public DummyLemma(LemmaType type) {
		this.type = type;
	}

	@Override
	public String getFirstForm() {
		return null;
	}

	@Override
	public String getForm(Form form) {
		return null;
	}

	@Override
	public boolean hasForm(Form form) {
		return false;
	}

	@Override
	public Map<Form, String> getForms() {
		return null;
	}

	@Override
	public LemmaType getType() {
		return type;
	}
}
