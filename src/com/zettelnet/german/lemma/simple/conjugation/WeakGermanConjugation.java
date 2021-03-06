package com.zettelnet.german.lemma.simple.conjugation;

import java.util.Arrays;

import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanMood;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.GermanPerson;
import com.zettelnet.german.form.GermanTense;
import com.zettelnet.german.form.GermanVoice;
import com.zettelnet.german.form.MapGermanFormValueProvider;

public class WeakGermanConjugation extends AbstractGermanConjugation {

	private static class Endings extends MapGermanFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(GermanPerson.TYPE, GermanNumerus.TYPE, GermanTense.TYPE, GermanMood.TYPE, GermanVoice.TYPE));

			put("e", GermanPerson.First, GermanNumerus.Singular, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);
			put("st", GermanPerson.Second, GermanNumerus.Singular, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);
			put("t", GermanPerson.Third, GermanNumerus.Singular, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);
			put("en", GermanPerson.First, GermanNumerus.Plural, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);
			put("t", GermanPerson.Second, GermanNumerus.Plural, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);
			put("en", GermanPerson.Third, GermanNumerus.Plural, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);

			put("e", GermanPerson.First, GermanNumerus.Singular, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);
			put("est", GermanPerson.Second, GermanNumerus.Singular, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);
			put("e", GermanPerson.Third, GermanNumerus.Singular, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);
			put("en", GermanPerson.First, GermanNumerus.Plural, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);
			put("et", GermanPerson.Second, GermanNumerus.Plural, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);
			put("en", GermanPerson.Third, GermanNumerus.Plural, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);

			put("e", GermanPerson.First, GermanNumerus.Singular, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);
			put("est", GermanPerson.Second, GermanNumerus.Singular, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);
			put("e", GermanPerson.Third, GermanNumerus.Singular, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);
			put("en", GermanPerson.First, GermanNumerus.Plural, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);
			put("et", GermanPerson.Second, GermanNumerus.Plural, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);
			put("en", GermanPerson.Third, GermanNumerus.Plural, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);

			put("e", GermanPerson.First, GermanNumerus.Singular, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);
			put("est", GermanPerson.Second, GermanNumerus.Singular, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);
			put("e", GermanPerson.Third, GermanNumerus.Singular, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);
			put("en", GermanPerson.First, GermanNumerus.Plural, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);
			put("et", GermanPerson.Second, GermanNumerus.Plural, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);
			put("en", GermanPerson.Third, GermanNumerus.Plural, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);

			put(new String[] { "e", "" }, GermanPerson.Second, GermanNumerus.Singular, GermanTense.Present, GermanMood.Imperative, GermanVoice.Active);
			put("t", GermanPerson.Second, GermanNumerus.Plural, GermanTense.Present, GermanMood.Imperative, GermanVoice.Active);

		}
	}

	public static final GermanFormValueProvider<String> ENDINGS = new Endings();

	public WeakGermanConjugation() {
		super(ENDINGS);
	}
}
