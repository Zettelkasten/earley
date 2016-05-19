package com.zettelnet.latin.lemma.simple;

import com.zettelnet.latin.lemma.Lemma;

public interface Verb extends Lemma {

	String getStem(VerbStem stemType);
}
