package com.zettelnet.latin.lemma.infinitive;

import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public class SimpleGerund extends AbstractDeclinableLemma implements Infinitive {

	public SimpleGerund(String firstForm, String stem, FormProvider<DeclinableLemma> formProvider, Genus genus, LemmaProperty... properties) {
		super(firstForm, stem, formProvider, genus, properties);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Infinitive;
	}
}
