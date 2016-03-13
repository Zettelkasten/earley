package com.zettelnet.latin.lemma.participle;

import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.verb.Verb;

public interface Participle extends DeclinableLemma {

	@Override
	Verb getDerivedFrom();
}
