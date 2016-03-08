package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.LemmaTerminal;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.SimpleLemma;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;

public class PlakatExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		// Symbols
		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");

		NonTerminal<Token> nounPhraseNom = new SimpleNonTerminal<>("NP(Nom)");
		NonTerminal<Token> nounPhraseAkk = new SimpleNonTerminal<>("NP(Akk)");

		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");

		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb, "v");
		Terminal<Token> nounNom = new LemmaTerminal(LemmaType.Noun, "n(Nom)");
		Terminal<Token> nounAkk = new LemmaTerminal(LemmaType.Noun, "n(Akk)");

		// Management

		SimpleGrammar<Token, DefaultParameter> grammar = new SimpleGrammar<>(sentence, new DefaultParameterManager());

		// Produktionen

		grammar.addProduction(sentence,
				nounPhraseNom,
				verbPhrase);

		grammar.addProduction(nounPhraseNom,
				nounNom);
		grammar.addProduction(nounPhraseAkk,
				nounAkk);

		grammar.addProduction(verbPhrase,
				nounPhraseAkk,
				verb);

		// lemmas

		Lemma dominus = new SimpleLemma(LemmaType.Noun);
		Lemma serva = new SimpleLemma(LemmaType.Noun);
		Lemma servus = new SimpleLemma(LemmaType.Noun);
		Lemma plaustrum = new SimpleLemma(LemmaType.Noun);
		Lemma iumentum = new SimpleLemma(LemmaType.Noun);
		Lemma forum = new SimpleLemma(LemmaType.Noun);
		Lemma canto = new SimpleLemma(LemmaType.Verb);
		Lemma rideo = new SimpleLemma(LemmaType.Verb);
		Lemma amo = new SimpleLemma(LemmaType.Verb);
		Lemma traho = new SimpleLemma(LemmaType.Verb);

		// tokens

		Token dominusT = new Token("dominus",
				new Determination(dominus, Casus.Nominative, Numerus.Singular, Genus.Masculine));
		Token servaT = new Token("serva",
				new Determination(serva, Casus.Nominative, Numerus.Singular, Genus.Feminine),
				new Determination(serva, Casus.Ablative, Numerus.Singular, Genus.Feminine),
				new Determination(serva, Casus.Vocative, Numerus.Singular, Genus.Feminine));
		Token serviT = new Token("servi",
				new Determination(servus, Casus.Genitive, Numerus.Singular, Genus.Masculine),
				new Determination(servus, Casus.Nominative, Numerus.Plural, Genus.Masculine),
				new Determination(servus, Casus.Vocative, Numerus.Plural, Genus.Masculine));
		Token forumT = new Token("forum",
				new Determination(forum, Casus.Nominative, Numerus.Singular, Genus.Masculine),
				new Determination(forum, Casus.Vocative, Numerus.Singular, Genus.Neuter),
				new Determination(forum, Casus.Accusative, Numerus.Singular, Genus.Neuter),
				new Determination(forum, Casus.Genitive, Numerus.Plural, Genus.Neuter));
		Token cantatT = new Token("cantat",
				new Determination(canto, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite));

		Token iumentumT = new Token("iumentum",
				new Determination(iumentum, Casus.Nominative, Numerus.Singular, Genus.Neuter),
				new Determination(iumentum, Casus.Accusative, Numerus.Singular, Genus.Neuter),
				new Determination(iumentum, Casus.Vocative, Numerus.Singular, Genus.Neuter));
		Token servaeT = new Token("servae",
				new Determination(serva, Casus.Genitive, Numerus.Singular, Genus.Feminine),
				new Determination(serva, Casus.Dative, Numerus.Singular, Genus.Feminine),
				new Determination(serva, Casus.Nominative, Numerus.Plural, Genus.Feminine),
				new Determination(serva, Casus.Vocative, Numerus.Plural, Genus.Feminine));
		Token plaustrumT = new Token("plaustrum",
				new Determination(plaustrum, Casus.Nominative, Numerus.Singular, Genus.Neuter),
				new Determination(plaustrum, Casus.Accusative, Numerus.Singular, Genus.Neuter),
				new Determination(plaustrum, Casus.Vocative, Numerus.Singular, Genus.Neuter));
		Token trahitT = new Token("trahit",
				new Determination(traho, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));
		Token amatT = new Token("amat",
				new Determination(amo, Person.Third, Numerus.Singular, Valency.Accusative));
		Token ridetT = new Token("ridet",
				new Determination(rideo, Tense.Present, Voice.Active, Finiteness.Finite, Valency.Null));
		Token ridereT = new Token("ridere",
				new Determination(rideo, Tense.Present, Voice.Active, Finiteness.Infinitive, Valency.Null));

		// parse

		List<Token> tokens = Arrays.asList(dominusT, forumT, amatT);

		EarleyParser<Token, DefaultParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());
		EarleyParseResult<Token, DefaultParameter> result = parser.parse(tokens);

		JufoHelper.present(result, tokens);
	}
}
