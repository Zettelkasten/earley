package com.zettelnet.translate.latger;

import com.zettelnet.earley.translate.ParameterTranslator;
import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanComparison;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanMood;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.GermanPerson;
import com.zettelnet.german.form.GermanTense;
import com.zettelnet.german.form.GermanVoice;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Comparison;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.MappingTableParameterTranslator;
import com.zettelnet.latin.token.Token;

public class LatinGermanParameterTranslator {

	private final static ParameterTranslator<Token, FormParameter, FormParameter> INSTANCE = makeInstance();
	
	private LatinGermanParameterTranslator() {
	}
	
	public static ParameterTranslator<Token, FormParameter, FormParameter> getInstance() {
		return INSTANCE;
	}
	
	private static ParameterTranslator<Token, FormParameter, FormParameter> makeInstance() {
		final MappingTableParameterTranslator translator = new MappingTableParameterTranslator();

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
		
		translator.registerMapping(Comparison.Positive, GermanComparison.Positive);
		translator.registerMapping(Comparison.Comparative, GermanComparison.Comparative);
		translator.registerMapping(Comparison.Superlative, GermanComparison.Superlative);
		
		return translator;
	}
}
