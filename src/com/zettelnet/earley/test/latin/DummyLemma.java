package com.zettelnet.earley.test.latin;

import java.util.Map;
import java.util.Set;

import com.zettelnet.latin.Form;
import com.zettelnet.latin.form.provider.FormProvider;
import com.zettelnet.latin.lemma.Lemma;

public class DummyLemma implements Lemma {

	private final Lemma.Type type;

	public DummyLemma(Lemma.Type type) {
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
	public Set<Form> getAvailableForms() {
		return null;
	}

	@Override
	public FormProvider<? extends Lemma> getFormProvider() {
		return null;
	}

	@Override
	public Set<Lemma> getDerivatives() {
		return null;
	}

	@Override
	public Lemma getDerivedFrom() {
		return null;
	}

	@Override
	public Type getType() {
		return type;
	}
}
