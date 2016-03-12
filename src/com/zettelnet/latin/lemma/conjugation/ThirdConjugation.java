package com.zettelnet.latin.lemma.conjugation;

import java.util.Arrays;

import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.MapFormProvider;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;

/**
 * Represents the <strong>third conjugation</strong>, also known as
 * <strong>consonantal conjugation</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class ThirdConjugation extends AbstractConjugation {

	private static class Linkings extends MapFormProvider<String> {
		public Linkings() {
			super(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));

			// Present Indicative Active

			put("", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("i", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("i", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("i", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
			put("i", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
			put("u", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);

			// Imperfect Indicative Active

			put("_eba", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_eb_a", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_eba", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_eb_a", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_eb_a", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("_eba", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);

			// Future I Indicative Active

			put("a", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("_e", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("e", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("_e", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
			put("_e", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
			put("e", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);

			// Present Subjunctive Active

			put("a", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("a", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("_a", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("a", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);

			// Imperfect Subjunctive Active

			put("ere", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("er_e", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("ere", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("er_e", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("er_e", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("ere", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);

			// Present Indicative Passive

			put("", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("e", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("i", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("i", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
			put("i", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
			put("u", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);

			// Imperfect Indicative Passive

			put("_eba", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_eb_a", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_eb_a", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_eb_a", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_eb_a", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("_eba", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);

			// Future I Indicative Passive

			put("a", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_e", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_e", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_e", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
			put("_e", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
			put("e", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);

			// Present Subjunctive Passive

			put("a", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_a", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_a", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("a", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);

			// Imperfect Subjunctive Passive

			put("ere", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("er_e", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("er_e", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("er_e", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("er_e", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("ere", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);

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

			put("e", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Active);
			put("i", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Active);
			put("e", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Passive);
			put("i", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Passive);
		}
	}

	public static final FormValueProvider<String> LINKINGS = new Linkings();
	public static final FormValueProvider<String> ENDINGS = new VerbEndings();

	public ThirdConjugation() {
		super(LINKINGS, ENDINGS);
	}
}
