package com.zettelnet.latin.lemma.simple;

import com.zettelnet.latin.lemma.DeclinableLemma;

public interface Supine extends DeclinableLemma {

	@Override
	Verb getDerivedFrom();
}
