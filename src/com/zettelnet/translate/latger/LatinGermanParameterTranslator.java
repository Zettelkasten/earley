package com.zettelnet.translate.latger;

import com.zettelnet.earley.translate.ParameterTranslator;
import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanDegree;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanMood;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.GermanPerson;
import com.zettelnet.german.form.GermanTense;
import com.zettelnet.german.form.GermanVoice;
import com.zettelnet.german.lemma.property.GermanFiniteness;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Degree;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.FormParameterManager;
import com.zettelnet.latin.param.MappingTableParameterTranslator;

public final class LatinGermanParameterTranslator {

	private LatinGermanParameterTranslator() {
	}

	public static <T, U> ParameterTranslator<T, FormParameter, U, FormParameter> makeInstance(final FormParameterManager<U> targetParameterManager) {
		final MappingTableParameterTranslator<T, U> translator = new MappingTableParameterTranslator<>(targetParameterManager);

		translator.registerMapping(Casus.Nominative, GermanCasus.Nominative);
		translator.registerMapping(Casus.Genitive, GermanCasus.Genitive);
		translator.registerMapping(Casus.Dative, GermanCasus.Dative);
		translator.registerMapping(Casus.Accusative, GermanCasus.Accusative);

		translator.registerMapping(Person.First, GermanPerson.First);
		translator.registerMapping(Person.Second, GermanPerson.Second);
		translator.registerMapping(Person.Third, GermanPerson.Third);

		translator.registerMapping(Numerus.Singular, GermanNumerus.Singular);
		translator.registerMapping(Numerus.Plural, GermanNumerus.Plural);

		translator.registerMapping(Genus.Masculine, GermanGenus.Masculine);
		translator.registerMapping(Genus.Feminine, GermanGenus.Feminine);
		translator.registerMapping(Genus.Neuter, GermanGenus.Neuter);

		translator.registerMapping(Mood.Indicative, GermanMood.Indicative);
		translator.registerMapping(Mood.Subjunctive, GermanMood.Subjunctive1);
		translator.registerMapping(Mood.Imperative, GermanMood.Imperative);

		translator.registerMapping(Tense.Present, GermanTense.Present);
		translator.registerMapping(Tense.Imperfect, GermanTense.Past);
		translator.registerMapping(Tense.Future, GermanTense.Future);
		translator.registerMapping(Tense.Perfect, GermanTense.Perfect);
		translator.registerMapping(Tense.Pluperfect, GermanTense.Pluperfect);
		translator.registerMapping(Tense.FuturePerfect, GermanTense.FuturePerfect);

		translator.registerMapping(Voice.Active, GermanVoice.Active);
		translator.registerMapping(Voice.Passive, GermanVoice.Passive);

		translator.registerMapping(Degree.Positive, GermanDegree.Positive);
		translator.registerMapping(Degree.Comparative, GermanDegree.Comparative);
		translator.registerMapping(Degree.Superlative, GermanDegree.Superlative);

		translator.registerMapping(Finiteness.Finite, GermanFiniteness.Finite);
		translator.registerMapping(Finiteness.Infinitive, GermanFiniteness.Infinitive);
		translator.registerMapping(Finiteness.Participle, GermanFiniteness.Participle);

		return translator;
	}
}
