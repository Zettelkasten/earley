package com.zettelnet.latin.lemma.simple;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.simple.declension.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class SimpleGerund extends AbstractDeclinableLemma {

	private final Lemma verb;
	private final Derivation derivation;

	public SimpleGerund(String firstForm, String stem, FormProvider<DeclinableLemma> formProvider, Genus genus, Lemma verb, Derivation derivation, LemmaProperty... properties) {
		super(firstForm, stem, formProvider, genus, properties);

		this.verb = verb;
		this.derivation = derivation;
		
		setProperties(getProperties().derive(verb.getProperties().values()).derive(Finiteness.Gerund));
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Gerund;
	}

	@Override
	public boolean isDerivation() {
		return true;
	}

	@Override
	public Lemma getDerivedFrom() {
		return verb;
	}

	@Override
	public Derivation getDerivationKind() {
		return derivation;
	}
}
