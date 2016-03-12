package com.zettelnet.latin.lemma.conjugation.participle;

import java.util.Arrays;

import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.verb.VerbStem;

public class ParticipleStems extends MapFormValueProvider<VerbStem> {
	
	public ParticipleStems() {
		super(Arrays.asList(Tense.class, Voice.class));

		put(VerbStem.Present, Tense.Present, Voice.Active);
		put(VerbStem.Supine, Tense.Perfect, Voice.Passive);
		put(VerbStem.Supine, Tense.Future, Voice.Active);
		put(VerbStem.Present, Tense.Future, Voice.Passive);
	}
}