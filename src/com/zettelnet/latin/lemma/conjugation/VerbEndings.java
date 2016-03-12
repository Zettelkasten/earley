package com.zettelnet.latin.lemma.conjugation;

import java.util.Arrays;

import com.zettelnet.latin.form.MapFormProvider;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;

public class VerbEndings extends MapFormProvider<String> {

	public VerbEndings() {
		super(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));

		put("_o", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
		put("s", Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
		put("t", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active);
		put("mus", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
		put("tis", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);
		put("nt", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Active);

		put("m", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
		put("s", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
		put("t", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Active);
		put("mus", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
		put("tis", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);
		put("nt", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Active);

		put("m", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
		put("s", Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
		put("t", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Active);
		put("mus", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
		put("tis", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);
		put("nt", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Active);

		put("m", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
		put("s", Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
		put("t", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Active);
		put("mus", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
		put("tis", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);
		put("nt", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Active);

		put("m", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
		put("s", Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
		put("t", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
		put("mus", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
		put("tis", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);
		put("nt", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Active);

		put("or", Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
		put(new String[] { "ris", "re" }, Person.Second, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
		put("tur", Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Passive);
		put("mur", Person.First, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
		put("min_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);
		put("ntur", Person.Third, Numerus.Plural, Tense.Present, Mood.Indicative, Voice.Passive);

		put("r", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
		put(new String[] { "ris", "re" }, Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
		put("tur", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Indicative, Voice.Passive);
		put("mur", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
		put("min_i", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);
		put("ntur", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Indicative, Voice.Passive);

		put("r", Person.First, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
		put(new String[] { "ris", "re" }, Person.Second, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
		put("tur", Person.Third, Numerus.Singular, Tense.Future, Mood.Indicative, Voice.Passive);
		put("mur", Person.First, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
		put("min_i", Person.Second, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);
		put("ntur", Person.Third, Numerus.Plural, Tense.Future, Mood.Indicative, Voice.Passive);

		put("r", Person.First, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
		put(new String[] { "ris", "re" }, Person.Second, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
		put("tur", Person.Third, Numerus.Singular, Tense.Present, Mood.Subjunctive, Voice.Passive);
		put("mur", Person.First, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
		put("min_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);
		put("ntur", Person.Third, Numerus.Plural, Tense.Present, Mood.Subjunctive, Voice.Passive);

		put("r", Person.First, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
		put(new String[] { "ris", "re" }, Person.Second, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
		put("tur", Person.Third, Numerus.Singular, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
		put("mur", Person.First, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
		put("min_i", Person.Second, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);
		put("ntur", Person.Third, Numerus.Plural, Tense.Imperfect, Mood.Subjunctive, Voice.Passive);

		put("_i", Person.First, Numerus.Singular, Tense.Perfect, Mood.Indicative, Voice.Active);
		put("ist_i", Person.Second, Numerus.Singular, Tense.Perfect, Mood.Indicative, Voice.Active);
		put("it", Person.Third, Numerus.Singular, Tense.Perfect, Mood.Indicative, Voice.Active);
		put("imus", Person.First, Numerus.Plural, Tense.Perfect, Mood.Indicative, Voice.Active);
		put("istis", Person.Second, Numerus.Plural, Tense.Perfect, Mood.Indicative, Voice.Active);
		put(new String[] { "_erunt", "_ere" }, Person.Third, Numerus.Plural, Tense.Perfect, Mood.Indicative, Voice.Active);

		put("eram", Person.First, Numerus.Singular, Tense.Pluperfect, Mood.Indicative, Voice.Active);
		put("er_as", Person.Second, Numerus.Singular, Tense.Pluperfect, Mood.Indicative, Voice.Active);
		put("erat", Person.Third, Numerus.Singular, Tense.Pluperfect, Mood.Indicative, Voice.Active);
		put("er_amus", Person.First, Numerus.Plural, Tense.Pluperfect, Mood.Indicative, Voice.Active);
		put("er_atis", Person.Second, Numerus.Plural, Tense.Pluperfect, Mood.Indicative, Voice.Active);
		put("erant", Person.Third, Numerus.Plural, Tense.Pluperfect, Mood.Indicative, Voice.Active);

		put("er_o", Person.First, Numerus.Singular, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
		put("eris", Person.Second, Numerus.Singular, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
		put("erit", Person.Third, Numerus.Singular, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
		put("erimus", Person.First, Numerus.Plural, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
		put("eritis", Person.Second, Numerus.Plural, Tense.FuturePerfect, Mood.Indicative, Voice.Active);
		put("erint", Person.Third, Numerus.Plural, Tense.FuturePerfect, Mood.Indicative, Voice.Active);

		put("erim", Person.First, Numerus.Singular, Tense.Perfect, Mood.Subjunctive, Voice.Active);
		put(new String[] { "er_is", "eris" }, Person.Second, Numerus.Singular, Tense.Perfect, Mood.Subjunctive, Voice.Active);
		put("erit", Person.Third, Numerus.Singular, Tense.Perfect, Mood.Subjunctive, Voice.Active);
		put(new String[] { "er_imus", "erimus" }, Person.First, Numerus.Plural, Tense.Perfect, Mood.Subjunctive, Voice.Active);
		put(new String[] { "er_itis", "eritis" }, Person.Second, Numerus.Plural, Tense.Perfect, Mood.Subjunctive, Voice.Active);
		put("erint", Person.Third, Numerus.Plural, Tense.Perfect, Mood.Subjunctive, Voice.Active);

		put("issem", Person.First, Numerus.Singular, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
		put("iss_es", Person.Second, Numerus.Singular, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
		put("isset", Person.Third, Numerus.Singular, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
		put("iss_emus", Person.First, Numerus.Plural, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
		put("iss_etis", Person.Second, Numerus.Plural, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);
		put("issent", Person.Third, Numerus.Plural, Tense.Pluperfect, Mood.Subjunctive, Voice.Active);

		put("", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Active);
		put("te", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Active);
		put("re", Person.Second, Numerus.Singular, Tense.Present, Mood.Imperative, Voice.Passive);
		put("min_i", Person.Second, Numerus.Plural, Tense.Present, Mood.Imperative, Voice.Passive);
	}

}
