package com.zettelnet.latin.lemma.simple.conjugation.infinitive;

import java.util.Arrays;

import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.simple.VerbStem;

public class SecondInfinitiveConjugation extends AbstractInfinitiveConjugation {

	private static class FirstFormEndings extends MapFormValueProvider<String> {
		public FirstFormEndings() {
			super(Arrays.asList(Tense.class, Voice.class));

			put("_ere", Tense.Present, Voice.Active);
			put("_er_i", Tense.Present, Voice.Passive);
			put("_evisse", Tense.Perfect, Voice.Active);
		}
	}

	private static class StemEndings extends MapFormValueProvider<String> {
		public StemEndings() {
			super(Arrays.asList(Tense.class, Voice.class));

			put("end", Tense.Present, Voice.Active);
		}
	}

	public static final FormValueProvider<String> FIRST_FORM_ENDINGS = new FirstFormEndings();
	public static final FormValueProvider<String> STEM_ENDINGS = new StemEndings();
	public static final FormValueProvider<VerbStem> STEM_TYPES = new InfinitiveStems();

	public SecondInfinitiveConjugation() {
		super(FIRST_FORM_ENDINGS, STEM_ENDINGS, STEM_TYPES);
	}
}
