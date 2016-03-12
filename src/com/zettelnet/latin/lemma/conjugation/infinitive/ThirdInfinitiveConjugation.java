package com.zettelnet.latin.lemma.conjugation.infinitive;

import java.util.Arrays;

import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.VerbStem;

public class ThirdInfinitiveConjugation extends AbstractInfinitiveConjugation {

	private static class FirstFormEndings extends MapFormValueProvider<String> {
		public FirstFormEndings() {
			super(Arrays.asList(Tense.class, Voice.class));

			put("ere", Tense.Present, Voice.Active);
			put("er_i", Tense.Present, Voice.Passive);
			put("evisse", Tense.Perfect, Voice.Active);
		}
	}

	private static class StemEndings extends MapFormValueProvider<String> {
		public StemEndings() {
			super(Arrays.asList(Tense.class, Voice.class));

			put("iend", Tense.Present, Voice.Active);
		}
	}

	public static final FormValueProvider<String> FIRST_FORM_ENDINGS = new FirstFormEndings();
	public static final FormValueProvider<String> STEM_ENDINGS = new StemEndings();
	public static final FormValueProvider<VerbStem> STEM_TYPES = new InfinitiveStems();

	public ThirdInfinitiveConjugation() {
		super(FIRST_FORM_ENDINGS, STEM_ENDINGS, STEM_TYPES);
	}
}
