package com.zettelnet.latin.lemma.supine;

import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.verb.Verb;

public class SimpleSupine extends AbstractDeclinableLemma implements Supine {

	private final Verb verb;

	public SimpleSupine(String stem, FormProvider<DeclinableLemma> formProvider, Genus genus, Verb verb, LemmaProperty... properties) {
		super(null, stem, formProvider, genus, properties);

		this.verb = verb;
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Supine;
	}

	@Override
	public boolean isDerivation() {
		return true;
	}

	@Override
	public Verb getDerivedFrom() {
		return verb;
	}
}
