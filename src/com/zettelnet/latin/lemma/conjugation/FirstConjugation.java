package com.zettelnet.latin.lemma.conjugation;

import java.util.Arrays;

import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.morph.MapMorphProvider;
import com.zettelnet.latin.morph.MorphProvider;

/**
 * Represents the <strong>first conjugation</strong>, also known as
 * <strong>a-conjugation</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class FirstConjugation extends AbstractConjugation {

	private static class Linkings extends MapMorphProvider {
		public Linkings() {
			super(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));

			// Present Indicative Active

			put("", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("a", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("_a", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
			put("_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
			put("a", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);

			// Imperfect Indicative Active

			put("_aba", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_ab_a", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_aba", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_ab_a", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_ab_a", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_aba", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);

			// Future I Indicative Active

			put("_ab", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("_abi", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("_abi", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("_abi", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
			put("_abi", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
			put("_abu", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);

			// Present Subjunctive Active

			put("e", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("_e", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("e", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("_e", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("_e", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("e", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);

			// Imperfect Subjunctive Active

			put("_are", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ar_e", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_are", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ar_e", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ar_e", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_are", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);

			// Present Indicative Passive

			put("", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_a", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_a", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
			put("a", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);

			// Imperfect Indicative Passive

			put("_aba", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_ab_a", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_ab_a", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_ab_a", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_ab_a", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_aba", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);

			// Future I Indicative Passive

			put("_ab", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_abe", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_abi", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_abi", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_abi", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_abu", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);

			// Present Subjunctive Passive

			put("e", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_e", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_e", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_e", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_e", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("e", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);

			// Imperfect Subjunctive Passive

			put("_are", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ar_e", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ar_e", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ar_e", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ar_e", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_are", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);

			// Perfect Indicative Active

			put("", Person.First, Numerus.Singular, Tense.Perfect, Mood.Indicative, Voice.Active);
			put("", Person.Second, Numerus.Singular, Tense.Perfect, Mood.Indicative, Voice.Active);
			put("", Person.Third, Numerus.Singular, Tense.Perfect, Mood.Indicative, Voice.Active);
			put("", Person.First, Numerus.Plural, Tense.Perfect, Mood.Indicative, Voice.Active);
			put("", Person.Second, Numerus.Plural, Tense.Perfect, Mood.Indicative, Voice.Active);
			put("", Person.Third, Numerus.Plural, Tense.Perfect, Mood.Indicative, Voice.Active);

			// Pluperfect Indicative Active

			put("", Person.First, Numerus.Singular, Tense.Pluperfect, Mood.Indicative, Voice.Active);
			put("", Person.Second, Numerus.Singular, Tense.Pluperfect, Mood.Indicative, Voice.Active);
			put("", Person.Third, Numerus.Singular, Tense.Pluperfect, Mood.Indicative, Voice.Active);
			put("", Person.First, Numerus.Plural, Tense.Pluperfect, Mood.Indicative, Voice.Active);
			put("", Person.Second, Numerus.Plural, Tense.Pluperfect, Mood.Indicative, Voice.Active);
			put("", Person.Third, Numerus.Plural, Tense.Pluperfect, Mood.Indicative, Voice.Active);

			// Future II Indicative Active

			put("", Person.First, Numerus.Singular, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
			put("", Person.Second, Numerus.Singular, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
			put("", Person.Third, Numerus.Singular, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
			put("", Person.First, Numerus.Plural, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
			put("", Person.Second, Numerus.Plural, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
			put("", Person.Third, Numerus.Plural, Tense.FuturePerfect, Mood.Indicative, Voice.Active);

			// Perfect Subjunctive Active

			put("", Person.First, Numerus.Singular, Tense.Perfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Second, Numerus.Singular, Tense.Perfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Third, Numerus.Singular, Tense.Perfect, Mood.Subjunctive, Voice.Active);
			put("", Person.First, Numerus.Plural, Tense.Perfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Second, Numerus.Plural, Tense.Perfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Third, Numerus.Plural, Tense.Perfect, Mood.Subjunctive, Voice.Active);

			// Pluperfect Subjunctive Active

			put("", Person.First, Numerus.Singular, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Second, Numerus.Singular, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Third, Numerus.Singular, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
			put("", Person.First, Numerus.Plural, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Second, Numerus.Plural, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
			put("", Person.Third, Numerus.Plural, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);

			// Imperative

			put("_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Active);
			put("_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Active);
			put("_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Passive);
			put("_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Passive);
		}
	}

	private static class Endings extends VerbEndings {
		public Endings() {
			super();

			put("_o", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("or", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
		}
	}

	public static final MorphProvider LINKINGS = new Linkings();
	public static final MorphProvider ENDINGS = new Endings();

	public FirstConjugation() {
		super(LINKINGS, ENDINGS);
	}
}
