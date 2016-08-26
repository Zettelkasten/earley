package com.zettelnet.german.lemma.simple.declension;

import com.zettelnet.german.lemma.GermanFormProvider;

public final class GermanDeclension {

	private GermanDeclension() {
	}

	public static final GermanFormProvider<DeclinableGermanLemma> Strong = new StrongGermanDeclension();
}
