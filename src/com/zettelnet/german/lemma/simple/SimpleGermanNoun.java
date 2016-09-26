package com.zettelnet.german.lemma.simple;

import com.zettelnet.german.derivation.GermanDerivation;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.GermanFormProvider;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;
import com.zettelnet.german.lemma.simple.declension.AbstractDeclinableGermanLemma;
import com.zettelnet.german.lemma.simple.declension.DeclinableGermanLemma;

public class SimpleGermanNoun extends AbstractDeclinableGermanLemma {

	public SimpleGermanNoun(String nominativeSingular, String genitiveSingular, String nominativePlural, GermanFormProvider<DeclinableGermanLemma> formProvider, GermanGenus genus, GermanLemmaProperty... properties) {
		super(nominativeSingular, genitiveSingular, nominativePlural, formProvider, genus, properties);
	}

	@Override
	public GermanLemmaType getType() {
		return GermanLemmaType.Noun;
	}

	@Override
	public boolean isDerivation() {
		return false;
	}

	@Override
	public GermanLemma getDerivedFrom() {
		return null;
	}

	@Override
	public GermanDerivation getDerivationKind() {
		return null;
	}
}
