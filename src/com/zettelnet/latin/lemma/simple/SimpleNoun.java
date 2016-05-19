package com.zettelnet.latin.lemma.simple;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.simple.declension.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class SimpleNoun extends AbstractDeclinableLemma {
	
	public SimpleNoun(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus, final LemmaProperty... properties) {
		super(firstForm, stem, formProvider, genus, properties);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Noun;
	}

	@Override
	public boolean isDerivation() {
		return false;
	}

	@Override
	public Lemma getDerivedFrom() {
		return null;
	}
	
	@Override
	public Derivation getDerivationKind() {
		return null;
	}
}
