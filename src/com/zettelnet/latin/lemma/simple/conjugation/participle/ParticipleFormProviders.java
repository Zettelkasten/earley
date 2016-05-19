package com.zettelnet.latin.lemma.simple.conjugation.participle;

import java.util.Arrays;
import java.util.Map;

import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.simple.declension.Declension;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class ParticipleFormProviders extends MapFormValueProvider<Map<Genus, FormProvider<DeclinableLemma>>> {
	public ParticipleFormProviders() {
		super(Arrays.asList(Tense.class, Voice.class));

		put(Genus.makeMap(Declension.Third, Declension.Third, Declension.Third), Tense.Present, Voice.Active);
		put(Declension.FirstAndSecond, Tense.Perfect, Voice.Passive);
		put(Declension.FirstAndSecond, Tense.Future, Voice.Active);
		put(Declension.FirstAndSecond, Tense.Future, Voice.Passive);
	}
}
