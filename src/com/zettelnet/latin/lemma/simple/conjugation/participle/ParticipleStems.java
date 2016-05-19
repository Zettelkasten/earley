package com.zettelnet.latin.lemma.simple.conjugation.participle;

import java.util.Arrays;

import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugationStem;

public class ParticipleStems extends MapFormValueProvider<ConjugationStem> {
	
	public ParticipleStems() {
		super(Arrays.asList(Tense.class, Voice.class));

		put(ConjugationStem.Present, Tense.Present, Voice.Active);
		put(ConjugationStem.Supine, Tense.Perfect, Voice.Passive);
		put(ConjugationStem.Supine, Tense.Future, Voice.Active);
		put(ConjugationStem.Present, Tense.Future, Voice.Passive);
	}
}