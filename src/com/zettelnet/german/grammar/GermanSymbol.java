package com.zettelnet.german.grammar;

import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanComparison;
import com.zettelnet.german.form.GermanDeterminerType;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanMood;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.GermanPerson;
import com.zettelnet.german.form.GermanTense;
import com.zettelnet.german.form.GermanVoice;
import com.zettelnet.german.lemma.GermanLemmaTerminal;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.lemma.property.GermanFiniteness;
import com.zettelnet.german.token.GermanToken;
import com.zettelnet.latin.lemma.property.Meaning;
import com.zettelnet.latin.param.PropertyTypeRegistry;
import com.zettelnet.latin.param.SimplePropertyTypeRegistry;

public final class GermanSymbol {

	private GermanSymbol() {
	}

	public static final NonTerminal<GermanToken> Sentence = new SimpleNonTerminal<>("S");
	public static final NonTerminal<GermanToken> NounPhrase = new SimpleNonTerminal<>("NP");
	public static final NonTerminal<GermanToken> NounPhraseOpt = new SimpleNonTerminal<>("[NP]");
	public static final NonTerminal<GermanToken> NounForm = new SimpleNonTerminal<>("NF");
	public static final NonTerminal<GermanToken> AdjectivePhrase = new SimpleNonTerminal<>("AP");
	public static final NonTerminal<GermanToken> AdjectivePhraseOpt = new SimpleNonTerminal<>("[AP]");

	public static final NonTerminal<GermanToken> VerbPhrase = new SimpleNonTerminal<>("VP");
	public static final NonTerminal<GermanToken> VerbForm = new SimpleNonTerminal<>("VF");
	public static final NonTerminal<GermanToken> Arguments = new SimpleNonTerminal<>("Args");
	public static final NonTerminal<GermanToken> AdverbalPhrase = new SimpleNonTerminal<>("AdvP");
	public static final NonTerminal<GermanToken> AdverbalPhraseVar = new SimpleNonTerminal<>("AdvP*");

	public static final Terminal<GermanToken> Verb = new GermanLemmaTerminal(GermanLemmaType.Verb);
	public static final Terminal<GermanToken> Noun = new GermanLemmaTerminal(GermanLemmaType.Noun);
	public static final Terminal<GermanToken> Article = new GermanLemmaTerminal(GermanLemmaType.Article);
	public static final Terminal<GermanToken> Adverb = new GermanLemmaTerminal(GermanLemmaType.Adverb);
	public static final Terminal<GermanToken> Adjective = new GermanLemmaTerminal(GermanLemmaType.Adjective);
	public static final Terminal<GermanToken> Conjunction = new GermanLemmaTerminal(GermanLemmaType.Conjunction);
	public static final Terminal<GermanToken> Subjunction = new GermanLemmaTerminal(GermanLemmaType.Subjunction);
	public static final Terminal<GermanToken> Infinitive = new GermanLemmaTerminal(GermanLemmaType.Infinitive);
	public static final Terminal<GermanToken> Participle = new GermanLemmaTerminal(GermanLemmaType.Participle);
	public static final Terminal<GermanToken> Preposition = new GermanLemmaTerminal(GermanLemmaType.Preposition);
	public static final Terminal<GermanToken> Interjection = new GermanLemmaTerminal(GermanLemmaType.Interjection);
	public static final Terminal<GermanToken> Pronoun = new GermanLemmaTerminal(GermanLemmaType.Pronoun);
	public static final NonTerminal<GermanToken> PronounOpt = new SimpleNonTerminal<>("[pron]");

	public static final PropertyTypeRegistry<GermanToken> DEFAULT_PROPERTY_TYPES = makeDefaultPropertyTypes();

	private static PropertyTypeRegistry<GermanToken> makeDefaultPropertyTypes() {
		SimplePropertyTypeRegistry<GermanToken> registry = new SimplePropertyTypeRegistry<>();

		registry.register(Sentence, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanTense.TYPE, GermanFiniteness.TYPE);
		registry.register(NounPhrase, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, Meaning.TYPE, GermanDeterminerType.TYPE);
		registry.register(NounPhraseOpt, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, Meaning.TYPE, GermanDeterminerType.TYPE);
		registry.register(NounForm, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, Meaning.TYPE);
		registry.register(AdjectivePhrase, GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanDeterminerType.TYPE);
		registry.register(AdjectivePhraseOpt, GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanDeterminerType.TYPE);
		registry.register(VerbPhrase, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanMood.TYPE, GermanTense.TYPE, GermanVoice.TYPE, GermanFiniteness.TYPE, Meaning.TYPE);
		registry.register(VerbForm, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanMood.TYPE, GermanTense.TYPE, GermanVoice.TYPE, GermanFiniteness.TYPE, Meaning.TYPE);
		registry.register(Arguments);
		registry.register(AdverbalPhrase, GermanMood.TYPE, Meaning.TYPE);
		registry.register(AdverbalPhraseVar, GermanMood.TYPE);
		registry.register(PronounOpt, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, Meaning.TYPE);

		registry.register(Verb, GermanPerson.TYPE, GermanNumerus.TYPE, GermanMood.TYPE, GermanTense.TYPE, GermanVoice.TYPE, Meaning.TYPE);
		registry.register(Noun, GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, Meaning.TYPE);
		registry.register(Article, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE);
		registry.register(Adverb, GermanComparison.TYPE, Meaning.TYPE);
		registry.register(Adjective, GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanComparison.TYPE, Meaning.TYPE);
		registry.register(Conjunction, Meaning.TYPE);
		registry.register(Subjunction, Meaning.TYPE);
		registry.register(Infinitive, Meaning.TYPE);
		registry.register(Participle, GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanTense.TYPE, GermanVoice.TYPE, Meaning.TYPE);
		registry.register(Preposition, Meaning.TYPE);
		registry.register(Interjection, Meaning.TYPE);
		registry.register(Pronoun, GermanCasus.TYPE, GermanPerson.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, Meaning.TYPE);

		return registry;
	}
}
