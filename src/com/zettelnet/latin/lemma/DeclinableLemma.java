package com.zettelnet.latin.lemma;

import java.util.Set;

import com.zettelnet.latin.form.Genus;

public interface DeclinableLemma extends Lemma {

	String getFirstForm(Genus genus);

	String getStem();

	Set<Genus> getGenus();
}
