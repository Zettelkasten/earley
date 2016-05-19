package com.zettelnet.latin.lemma.simple;

import java.util.Map;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class SimpleParticiple extends SimpleAdjective {

	private final Lemma verb;
	private final Derivation derivation;

	public SimpleParticiple(final Map<Genus, String> firstForm, final String stem, final Map<Genus, FormProvider<DeclinableLemma>> formProvider, final Lemma verb, final Derivation derivation, LemmaProperty... properties) {
		super(firstForm, stem, formProvider, properties);

		this.verb = verb;
		this.derivation = derivation;

		setProperties(getProperties().derive(verb.getProperties().values()).derive(Finiteness.Participle));
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
	public LemmaType getType() {
		return LemmaType.Participle;
	}
	
	@Override
	public Derivation getDerivationKind() {
		return derivation;
	}
}
