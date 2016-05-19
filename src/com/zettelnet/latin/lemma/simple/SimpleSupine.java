package com.zettelnet.latin.lemma.simple;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.simple.declension.AbstractDeclinableLemma;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class SimpleSupine extends AbstractDeclinableLemma {

	private final Lemma verb;
	private final Derivation derivation;

	public SimpleSupine(String stem, FormProvider<DeclinableLemma> formProvider, Genus genus, Lemma verb, Derivation derivation, LemmaProperty... properties) {
		super(null, stem, formProvider, genus, properties);

		this.verb = verb;
		this.derivation = derivation;

		setProperties(getProperties().derive(verb.getProperties().values()).derive(Finiteness.Supine));
	}

	@Override
	public String getNominalForm() {
		return getForm(Form.withValues(Casus.Accusative, Numerus.Singular)).iterator().next();
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
	public Lemma getDerivedFrom() {
		return verb;
	}

	@Override
	public Derivation getDerivationKind() {
		return derivation;
	}
}
