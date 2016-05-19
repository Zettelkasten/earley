package com.zettelnet.latin.lemma.simple;

import com.zettelnet.latin.lemma.DeclinableLemma;

public interface Gerund extends DeclinableLemma {

	@Override
	Verb getDerivedFrom();
}
