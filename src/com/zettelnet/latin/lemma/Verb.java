package com.zettelnet.latin.lemma;

import com.zettelnet.latin.form.Tense;

public interface Verb extends Lemma {

	String getStem(Tense tense);
}
