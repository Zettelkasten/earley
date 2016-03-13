package com.zettelnet.latin.lemma.infinitive;

import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.verb.Verb;

public interface Infinitive extends DeclinableLemma {

	@Override
	Verb getDerivedFrom();
}
