package com.zettelnet.latin.lemma.participle;

import java.util.Map;

import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.adjective.SimpleAdjective;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.verb.Verb;

public class SimpleParticiple extends SimpleAdjective implements Participle {

	private final Verb verb;

	public SimpleParticiple(final Map<Genus, String> firstForm, final String stem, final Map<Genus, FormProvider<DeclinableLemma>> formProvider, final Verb verb, LemmaProperty... properties) {
		super(firstForm, stem, formProvider, properties);

		this.verb = verb;
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
}
