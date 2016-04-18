package com.zettelnet.latin.lemma.gerund;

import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.verb.Verb;

public interface Gerund extends DeclinableLemma {

	@Override
	Verb getDerivedFrom();
}
