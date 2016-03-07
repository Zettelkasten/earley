package com.zettelnet.latin.lemma;

import com.zettelnet.latin.form.Genus;

public class SimpleAdjective extends AbstractDeclinableLemma implements Adjective {

	public SimpleAdjective(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus) {
		super(firstForm, stem, formProvider, genus);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Adjective;
	}

	@Override
	public String toString() {
		return getFirstForm();
	}
}
