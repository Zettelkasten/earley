package com.zettelnet.latin.lemma.simple.declension;

import java.util.Set;

import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.Lemma;

public interface DeclinableLemma extends Lemma {

	String getFirstForm(Genus genus);

	String getStem();

	Set<Genus> getGenus();
}
