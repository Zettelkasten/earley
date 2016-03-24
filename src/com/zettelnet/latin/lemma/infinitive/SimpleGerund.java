package com.zettelnet.latin.lemma.infinitive;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.verb.Verb;

public class SimpleGerund extends AbstractDeclinableLemma implements Infinitive {

	private final Verb verb;
	private final Derivation derivation;
	
	public SimpleGerund(String firstForm, String stem, FormProvider<DeclinableLemma> formProvider, Genus genus, Verb verb, Derivation derivation, LemmaProperty... properties) {
		super(firstForm, stem, formProvider, genus, properties);
		
		this.verb = verb;
		this.derivation = derivation;
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Infinitive;
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
	public Derivation getDerivationKind() {
		return derivation;
	}
}
