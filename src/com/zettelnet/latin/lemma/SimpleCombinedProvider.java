package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.derivation.DerivationProvider;
import com.zettelnet.latin.form.Form;

public class SimpleCombinedProvider<T> implements CombinedProvider<T> {

	private final FormProvider<T> formProvider;
	private final DerivationProvider<T> derivationProvider;

	public SimpleCombinedProvider(final FormProvider<T> formProvider, final DerivationProvider<T> derivationProvider) {
		this.formProvider = formProvider;
		this.derivationProvider = derivationProvider;
	}

	@Override
	public Collection<String> getForm(T lemma, Form form) {
		return formProvider.getForm(lemma, form);
	}

	@Override
	public boolean hasForm(T lemma, Form form) {
		return formProvider.hasForm(lemma, form);
	}

	@Override
	public Map<Form, Collection<String>> getForms(T lemma) {
		return formProvider.getForms(lemma);
	}

	@Override
	public Collection<Lemma> getDerivation(T lemma, Derivation derivation) {
		return derivationProvider.getDerivation(lemma, derivation);
	}

	@Override
	public boolean hasDerivation(T lemma, Derivation derivation) {
		return derivationProvider.hasDerivation(lemma, derivation);
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations(T lemma) {
		return derivationProvider.getDerivations(lemma);
	}

}
