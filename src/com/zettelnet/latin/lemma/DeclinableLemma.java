package com.zettelnet.latin.lemma;

import com.zettelnet.latin.form.Genus;

public interface DeclinableLemma extends Lemma {

	String getStem();

	Genus getGenus();
}
