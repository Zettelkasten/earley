package com.zettelnet.latin.lemma;

public interface LemmaFactory<T extends Lemma> {

	T makeLemma();
}
