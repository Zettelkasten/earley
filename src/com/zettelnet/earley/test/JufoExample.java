package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.AnyParameterExpression;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.FormParameterManager;
import com.zettelnet.earley.test.latin.FormParameterizer;
import com.zettelnet.earley.test.latin.LemmaTerminal;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Comparison;
import com.zettelnet.latin.form.Form;
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

public class JufoExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		// Symbols

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");

		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> nounForm = new SimpleNonTerminal<>("NF");
		NonTerminal<Token> attribute = new SimpleNonTerminal<>("Attr");
		NonTerminal<Token> attributeVar = new SimpleNonTerminal<>("Attr*");

		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");
		NonTerminal<Token> verbForm = new SimpleNonTerminal<>("VF");
		NonTerminal<Token> arguments = new SimpleNonTerminal<>("Args");
		NonTerminal<Token> adverbalPhrase = new SimpleNonTerminal<>("AP");
		NonTerminal<Token> adverbalPhraseVar = new SimpleNonTerminal<>("AP*");

		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb);
		Terminal<Token> noun = new LemmaTerminal(LemmaType.Noun);
		Terminal<Token> adverb = new LemmaTerminal(LemmaType.Adverb);
		Terminal<Token> adjective = new LemmaTerminal(LemmaType.Adjective);

		// Management

		ParameterManager<FormParameter> parameterManager = new FormParameterManager();
		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();

		SimpleGrammar<Token, FormParameter> grammar = new SimpleGrammar<>(sentence, parameterManager);
		grammar.setStartSymbolParameter(new SingletonParameterFactory<>(new FormParameter(Casus.Nominative, Finiteness.Finite)));

		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, parameterizer);
		ParameterExpression<Token, FormParameter> any = new AnyParameterExpression<>(parameterManager);

		// Productions

		// S(pi : !NullVal !Imp) -> NP(pi) VP(pi) -> TODO
		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(nounPhrase, copy),
				new ParameterizedSymbol<>(verbPhrase, copy));
		// S(pi) -> VP(pi)
		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(verbPhrase, copy));

		// VP(pi) -> VF(pi) Args(pi) AP*
		grammar.addProduction(
				verbPhrase,
				new ParameterizedSymbol<>(verbForm, copy),
				new ParameterizedSymbol<>(arguments, copy),
				new ParameterizedSymbol<>(adverbalPhraseVar, any));
		// VF(pi) -> v(pi) -> TODO
		grammar.addProduction(
				verbForm,
				new ParameterizedSymbol<>(verb, copy));
		// Args(pi : NullVal) -> epsilon
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Null)));
		// Args(pi : SingleVal) -> epsilon
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Single)));
		// Args(pi : Kopula) -> NP(pi)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Copula)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Nominative, null, null)))));
		// Args(pi : GenVal) -> NP(Gen)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Genitive)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Genitive, null, null)))));
		// Args(pi : DatVal) -> NP(Dat)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Dative)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Dative, null, null)))));
		// Args(pi : AkkVal) -> NP(Akk)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Accusative)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Accusative, null, null)))));
		// Args(pi : AkkDatVal) -> NP(Akk) NP(Dat)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.AccusativeDative)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Accusative, null, null)))),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Dative, null, null)))));

		// AP var
		grammar.addProduction(
				adverbalPhraseVar);
		grammar.addProduction(
				adverbalPhraseVar,
				adverbalPhrase,
				adverbalPhraseVar);

		// AP -> Adv
		grammar.addProduction(adverbalPhrase,
				adverb);

		// NP(pi : Fin) -> NF(pi) Attr(pi)*
		grammar.addProduction(
				nounPhrase,
				new SingletonParameterFactory<>(new FormParameter(Finiteness.Finite)),
				new ParameterizedSymbol<>(nounForm, copy),
				new ParameterizedSymbol<>(attributeVar, copy));
		// NF(pi) -> n(pi)
		grammar.addProduction(
				nounForm,
				new ParameterizedSymbol<>(noun, copy));
		// NF(pi) -> adj(pi)
		grammar.addProduction(
				nounForm,
				new ParameterizedSymbol<>(adjective, copy));
		// attribute varargs
		grammar.addProduction(
				attributeVar);
		grammar.addProduction(
				attributeVar,
				new ParameterizedSymbol<>(attribute, copy),
				new ParameterizedSymbol<>(attributeVar, copy));
		// Attr(pi) -> NF(pi)
		grammar.addProduction(
				attribute,
				new ParameterizedSymbol<>(nounForm, copy));
		// Attr(pi) -> NP(Gen)
		grammar.addProduction(
				attribute,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Form.nounForm(Casus.Genitive, null, null, null)))));
		// NP(pi : Nom / Akk) -> S(pi : Inf Präs/Perf/Fut Akk)
		for (Casus casus : Arrays.asList(Casus.Nominative, Casus.Accusative)) {
			for (Tense tense : Arrays.asList(Tense.Present)) {
				grammar.addProduction(nounPhrase,
						new SingletonParameterFactory<>(new FormParameter(Form.nounForm(casus, null, null))),
						new ParameterizedSymbol<>(sentence, new SpecificParameterExpression<>(parameterManager, parameterizer,
								new FormParameter(Casus.Accusative, tense, Finiteness.Infinitive))));
			}
		}

		EarleyParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());

		// lemmas

		Lemma dominusL = new SimpleLemma(LemmaType.Noun);
		Lemma servaL = new SimpleLemma(LemmaType.Noun);
		Lemma servusL = new SimpleLemma(LemmaType.Noun);
		Lemma plaustrumL = new SimpleLemma(LemmaType.Noun);
		Lemma iumentumL = new SimpleLemma(LemmaType.Noun);
		Lemma forumL = new SimpleLemma(LemmaType.Noun);
		Lemma cantoL = new SimpleLemma(LemmaType.Verb);
		Lemma rideoL = new SimpleLemma(LemmaType.Verb);
		Lemma amoL = new SimpleLemma(LemmaType.Verb);
		Lemma trahoL = new SimpleLemma(LemmaType.Verb);
		Lemma loquorL = new SimpleLemma(LemmaType.Verb);
		Lemma doL = new SimpleLemma(LemmaType.Verb);
		Lemma nonL = new SimpleLemma(LemmaType.Adverb);
		Lemma iamL = new SimpleLemma(LemmaType.Adverb);
		Lemma LatineL = new SimpleLemma(LemmaType.Adverb);
		Lemma tandemL = new SimpleLemma(LemmaType.Adverb);
		Lemma liberL = new SimpleLemma(LemmaType.Adjective);
		Lemma possumL = new SimpleLemma(LemmaType.Verb);
		Lemma sumL = new SimpleLemma(LemmaType.Verb);
		Lemma voloL = new SimpleLemma(LemmaType.Verb);

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
				new Determination(cantoL, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Infinitive));
		Token vult = new Token("vult",
				new Determination(voloL, Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative));

		// "Ich kann nicht mehr Lateinisch sprechen"
		 List<Token> tokens = Arrays.asList(possum, loqui, Latine);

		// "Ich gebe dem Herrn einen Karren"
		// List<Token> tokens = Arrays.asList(doT, domino, plaustrum);

		// "Der Sklave will endlich frei sein"
		// List<Token> tokens = Arrays.asList(servus, tandem, liber, esse,
		// vult);

		// "Er will singen"
//		List<Token> tokens = Arrays.asList(cantare, vult);

		EarleyParseResult<Token, FormParameter> result = parser.parse(tokens);

		
		JufoHelper.present(result, tokens);

		System.out.println("-");
		
		for (SyntaxTreeVariant<Token, FormParameter> variant : result.getSyntaxTree().getVariants().iterator().next().getChildren().get(0).getVariants()) {
			System.out.println(variant);
		}
	}
}
