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
 * Represents the <strong>fourth conjugation</strong>, also known as
 * <strong>i-conjugation</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class FourthConjugation extends AbstractConjugation {

	private static class Linkings extends MapMorphProvider {
		public Linkings() {
			super(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));

			// Present Indicative Active

			put("i", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("_i", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("i", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
			put("_i", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
			put("_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
			put("iu", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);

			// Imperfect Indicative Active

			put("i_eba", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("i_eb_a", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("i_eba", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("i_eb_a", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("i_eb_a", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
			put("i_eba", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);

			// Future I Indicative Active

			put("ia", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("i_es", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("iet", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
			put("i_emus", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
			put("i_etis", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
			put("ient", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);

			// Present Subjunctive Active

			put("ia", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("i_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("ia", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("i_a", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("i_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
			put("ia", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);

			// Imperfect Subjunctive Active

			put("_ire", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ir_e", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ire", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ir_e", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ir_e", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
			put("_ire", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);

			// Present Indicative Passive

			put("i", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_i", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_i", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_i", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
			put("_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
			put("iu", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);

			// Imperfect Indicative Passive

			put("i_eba", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("i_eb_a", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("i_eba", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("i_eb_a", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("i_eb_a", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
			put("i_eba", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);

			// Future I Indicative Passive

			put("ia", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("i_e", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("i_e", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
			put("i_e", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
			put("i_e", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
			put("ie", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);

			// Present Subjunctive Passive

			put("ia", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("i_a", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("i_a", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("i_a", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("i_a", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
			put("ia", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);

			// Imperfect Subjunctive Passive

			put("_ire", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ir_e", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ir_e", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ir_e", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ir_e", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
			put("_ire", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);

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

			put("_i", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Active);
			put("_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Active);
			put("_i", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Passive);
			put("_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Passive);
		}
	}

	public static final MorphProvider LINKINGS = new Linkings();
	public static final MorphProvider ENDINGS = new VerbEndings();

	public FourthConjugation() {
		super(LINKINGS, ENDINGS);
	}
}
