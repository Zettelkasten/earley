package com.zettelnet.latin.lemma.simple.conjugation.infinitive;

import java.util.Arrays;

import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugationStem;

public class InfinitiveStems extends MapFormValueProvider<ConjugationStem> {
	
	public InfinitiveStems() {
		super(Arrays.asList(Tense.class, Voice.class));

		put(ConjugationStem.Present, Tense.Present, Voice.Active);
		put(ConjugationStem.Present, Tense.Present, Voice.Passive);
		put(ConjugationStem.Perfect, Tense.Perfect, Voice.Active);
	}
}