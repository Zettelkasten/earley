package com.zettelnet.latin.lemma.simple;

import java.util.Map;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public class SimpleParticiple extends SimpleAdjective implements Participle {

	private final Verb verb;
	private final Derivation derivation;

	public SimpleParticiple(final Map<Genus, String> firstForm, final String stem, final Map<Genus, FormProvider<DeclinableLemma>> formProvider, final Verb verb, final Derivation derivation, LemmaProperty... properties) {
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
	public Verb getDerivedFrom() {
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
