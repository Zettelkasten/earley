package com.zettelnet.german.lemma.simple.declension;

import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.GermanLemma;

public interface DeclinableGermanLemma extends GermanLemma {

	String getNominativeSingular();

	String getGenitiveSingular();

	String getNominativePlural();

	GermanGenus getGenus();
}
