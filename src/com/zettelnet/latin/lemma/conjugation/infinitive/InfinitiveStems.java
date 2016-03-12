package com.zettelnet.latin.lemma.conjugation.infinitive;

import java.util.Arrays;

import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.VerbStem;

public class InfinitiveStems extends MapFormValueProvider<VerbStem> {
	
	public InfinitiveStems() {
		super(Arrays.asList(Tense.class, Voice.class));

		put(VerbStem.Present, Tense.Present, Voice.Active);
		put(VerbStem.Present, Tense.Present, Voice.Passive);
		put(VerbStem.Perfect, Tense.Perfect, Voice.Active);
	}
}