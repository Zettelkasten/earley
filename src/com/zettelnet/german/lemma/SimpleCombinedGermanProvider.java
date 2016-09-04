package com.zettelnet.german.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.german.derivation.GermanDerivation;
import com.zettelnet.german.derivation.GermanDerivationProvider;
import com.zettelnet.german.form.GermanForm;

public class SimpleCombinedGermanProvider<T> implements CombinedGermanProvider<T> {

	private final GermanFormProvider<T> formProvider;
	private final GermanDerivationProvider<T> derivationProvider;

	public SimpleCombinedGermanProvider(final GermanFormProvider<T> formProvider, final GermanDerivationProvider<T> derivationProvider) {
		this.formProvider = formProvider;
		this.derivationProvider = derivationProvider;
	}

	@Override
	public Collection<String> getForm(T lemma, GermanForm form) {
		return formProvider.getForm(lemma, form);
	}

	@Override
	public boolean hasForm(T lemma, GermanForm form) {
		return formProvider.hasForm(lemma, form);
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms(T lemma) {
		return formProvider.getForms(lemma);
	}

	@Override
	public Collection<GermanLemma> getDerivation(T lemma, GermanDerivation derivation) {
		return derivationProvider.getDerivation(lemma, derivation);
	}

	@Override
	public boolean hasDerivation(T lemma, GermanDerivation derivation) {
		return derivationProvider.hasDerivation(lemma, derivation);
	}

	@Override
	public Map<GermanDerivation, Collection<GermanLemma>> getDerivations(T lemma) {
		return derivationProvider.getDerivations(lemma);
	}
}
