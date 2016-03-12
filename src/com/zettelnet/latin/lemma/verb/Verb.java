package com.zettelnet.latin.lemma.verb;

import com.zettelnet.latin.lemma.Lemma;

public interface Verb extends Lemma {

	String getStem(VerbStem stemType);
}
