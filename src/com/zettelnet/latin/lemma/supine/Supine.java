package com.zettelnet.latin.lemma.supine;

import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.verb.Verb;

public interface Supine extends DeclinableLemma {

	@Override
	Verb getDerivedFrom();
}
