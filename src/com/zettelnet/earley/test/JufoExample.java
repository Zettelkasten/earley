package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.DummyLemma;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.LatinGrammar;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Comparison;
import com.zettelnet.latin.form.Finiteness;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Valency;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;

public class JufoExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {

		Grammar<Token, FormParameter> grammar = LatinGrammar.makeGrammar();

		EarleyParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());

		// lemmas

		Lemma dominusL = new DummyLemma(Lemma.Type.Noun);
		Lemma servaL = new DummyLemma(Lemma.Type.Noun);
		Lemma servusL = new DummyLemma(Lemma.Type.Noun);
		Lemma plaustrumL = new DummyLemma(Lemma.Type.Noun);
		Lemma iumentumL = new DummyLemma(Lemma.Type.Noun);
		Lemma forumL = new DummyLemma(Lemma.Type.Noun);
		Lemma cantoL = new DummyLemma(Lemma.Type.Verb);
		Lemma rideoL = new DummyLemma(Lemma.Type.Verb);
		Lemma amoL = new DummyLemma(Lemma.Type.Verb);
		Lemma trahoL = new DummyLemma(Lemma.Type.Verb);
		Lemma loquorL = new DummyLemma(Lemma.Type.Verb);
		Lemma doL = new DummyLemma(Lemma.Type.Verb);
		Lemma nonL = new DummyLemma(Lemma.Type.Adverb);
		Lemma iamL = new DummyLemma(Lemma.Type.Adverb);
		Lemma LatineL = new DummyLemma(Lemma.Type.Adverb);
		Lemma tandemL = new DummyLemma(Lemma.Type.Adverb);
		Lemma liberL = new DummyLemma(Lemma.Type.Adjective);
		Lemma possumL = new DummyLemma(Lemma.Type.Verb);
		Lemma sumL = new DummyLemma(Lemma.Type.Verb);
		Lemma voloL = new DummyLemma(Lemma.Type.Verb);

		// tokens

		Token dominus = new Token("dominus",
				new Determination(dominusL, Casus.Nominative, Numerus.Singular, Genus.Masculine));
		Token domino = new Token("domino",
				new Determination(dominusL, Casus.Dative, Numerus.Singular, Genus.Masculine),
				new Determination(dominusL, Casus.Ablative, Numerus.Singular, Genus.Masculine));
		Token serva = new Token("serva",
				new Determination(servaL, Casus.Nominative, Numerus.Singular, Genus.Feminine),
				new Determination(servaL, Casus.Ablative, Numerus.Singular, Genus.Feminine),
				new Determination(servaL, Casus.Vocative, Numerus.Singular, Genus.Feminine));
		Token servi = new Token("servi",
				new Determination(servusL, Casus.Genitive, Numerus.Singular, Genus.Masculine),
				new Determination(servusL, Casus.Nominative, Numerus.Plural, Genus.Masculine),
				new Determination(servusL, Casus.Vocative, Numerus.Plural, Genus.Masculine));
		Token servus = new Token("servus",
				new Determination(servusL, Casus.Nominative, Numerus.Singular, Genus.Masculine));
		Token forum = new Token("forum",
				new Determination(forumL, Casus.Nominative, Numerus.Singular, Genus.Masculine),
				new Determination(forumL, Casus.Vocative, Numerus.Singular, Genus.Neuter),
				new Determination(forumL, Casus.Accusative, Numerus.Singular, Genus.Neuter),
				new Determination(forumL, Casus.Genitive, Numerus.Plural, Genus.Neuter));
		Token cantat = new Token("cantat",
				new Determination(cantoL, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));
		Token iumentum = new Token("iumentum",
				new Determination(iumentumL, Casus.Nominative, Numerus.Singular, Genus.Neuter),
				new Determination(iumentumL, Casus.Accusative, Numerus.Singular, Genus.Neuter),
				new Determination(iumentumL, Casus.Vocative, Numerus.Singular, Genus.Neuter));
		Token servae = new Token("servae",
				new Determination(servaL, Casus.Genitive, Numerus.Singular, Genus.Feminine),
				new Determination(servaL, Casus.Dative, Numerus.Singular, Genus.Feminine),
				new Determination(servaL, Casus.Nominative, Numerus.Plural, Genus.Feminine),
				new Determination(servaL, Casus.Vocative, Numerus.Plural, Genus.Feminine));
		Token plaustrum = new Token("plaustrum",
				new Determination(plaustrumL, Casus.Nominative, Numerus.Singular, Genus.Neuter),
				new Determination(plaustrumL, Casus.Accusative, Numerus.Singular, Genus.Neuter),
				new Determination(plaustrumL, Casus.Vocative, Numerus.Singular, Genus.Neuter));
		Token trahit = new Token("trahit",
				new Determination(trahoL, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));
		Token amat = new Token("amat",
				new Determination(amoL, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));
		Token doT = new Token("do",
				new Determination(doL, Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.AccusativeDative));
		Token ridet = new Token("ridet",
				new Determination(rideoL, Tense.Present, Voice.Active, Finiteness.Finite, Valency.Null));
		Token ridere = new Token("ridere",
				new Determination(rideoL, Tense.Present, Voice.Active, Finiteness.Infinitive, Valency.Null));
		Token loqui = new Token("loqui",
				new Determination(loquorL, Tense.Present, Voice.Active, Finiteness.Infinitive, Valency.Null));
		Token non = new Token("non",
				new Determination(nonL, Comparison.Positive));
		Token iam = new Token("iam",
				new Determination(iamL, Comparison.Positive));
		Token tandem = new Token("tandem",
				new Determination(tandemL, Comparison.Positive));
		Token liber = new Token("liber",
				new Determination(liberL, Casus.Nominative, Numerus.Singular, Genus.Masculine, Comparison.Positive),
				new Determination(liberL, Casus.Vocative, Numerus.Singular, Genus.Masculine, Comparison.Positive));
		Token Latine = new Token("Latine",
				new Determination(LatineL, Comparison.Positive));
		Token possum = new Token("possum",
				new Determination(possumL, Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));
		Token est = new Token("est",
				new Determination(sumL, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Copula));
		Token esse = new Token("esse",
				new Determination(sumL, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Infinitive, Valency.Copula));
		Token cantare = new Token("cantare",
				new Determination(cantoL, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Infinitive, Valency.Accusative));
		Token vult = new Token("vult",
				new Determination(voloL, Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));

		// "Ich kann nicht mehr Lateinisch sprechen"
//		List<Token> tokens = Arrays.asList(Latine, loqui, non, iam, possum);

		// "Ich gebe dem Herrn einen Karren"
		List<Token> tokens = Arrays.asList(doT, domino, plaustrum);

		// "Der Sklave will endlich frei sein"
		// List<Token> tokens = Arrays.asList(servus, tandem, liber, esse,
		// vult);

		EarleyParseResult<Token, FormParameter> result = parser.parse(tokens);

		JufoHelper.present(result, tokens);
	}
}
