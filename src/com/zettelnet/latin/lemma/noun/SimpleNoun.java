package com.zettelnet.latin.lemma.noun;

import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public class SimpleNoun extends AbstractDeclinableLemma implements Noun {

	public SimpleNoun(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus, final LemmaProperty... properties) {
		super(firstForm, stem, formProvider, genus, properties);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Noun;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
