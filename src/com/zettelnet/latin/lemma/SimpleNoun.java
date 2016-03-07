package com.zettelnet.latin.lemma;

import com.zettelnet.latin.form.Genus;

public class SimpleNoun extends AbstractDeclinableLemma implements Noun {

	public SimpleNoun(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus) {
		super(firstForm, stem, formProvider, genus);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Noun;
	}

	@Override
	public String toString() {
		return getFirstForm();
	}
}
