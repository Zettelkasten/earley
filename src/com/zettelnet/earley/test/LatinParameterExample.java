package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
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
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaTerminal;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.SimpleLemma;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.FormParameterManager;
import com.zettelnet.latin.param.FormParameterizer;
import com.zettelnet.latin.token.Determination;
import com.zettelnet.latin.token.Token;

public class LatinParameterExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");

		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> nounForm = new SimpleNonTerminal<>("NF");
		NonTerminal<Token> attribute = new SimpleNonTerminal<>("Attr");
		NonTerminal<Token> attributeVar = new SimpleNonTerminal<>("AttrVar");

		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");
		NonTerminal<Token> verbForm = new SimpleNonTerminal<>("VF");
		NonTerminal<Token> arguments = new SimpleNonTerminal<>("Args");
		NonTerminal<Token> adverbalPhrase = new SimpleNonTerminal<>("AP");
		NonTerminal<Token> adverbalPhraseVar = new SimpleNonTerminal<>("APVar");

		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb);
		Terminal<Token> noun = new LemmaTerminal(LemmaType.Noun);

		ParameterManager<FormParameter> parameterManager = new FormParameterManager();
		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();

		SimpleGrammar<Token, FormParameter> grammar = new SimpleGrammar<>(sentence, parameterManager);
		grammar.setStartSymbolParameter(new SingletonParameterFactory<>(new FormParameter(Casus.Nominative, null, null, Person.Third, null, null, null, null, Finiteness.Finite)));

		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, parameterizer);
		ParameterExpression<Token, FormParameter> any = new AnyParameterExpression<>(parameterManager);

		// S(pi : !NullVal !Imp) -> NP(pi) VP(pi) -> TODO
		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(nounPhrase, copy),
				new ParameterizedSymbol<>(verbPhrase, copy));
		// S(pi) -> VP(pi)
		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(verbPhrase, copy));

		// // VP(pi) -> VF(pi) Args(pi)* AP*
		grammar.addProduction(
				verbPhrase,
				new ParameterizedSymbol<>(verbForm, copy),
				new ParameterizedSymbol<>(arguments, copy),
				new ParameterizedSymbol<>(adverbalPhraseVar, any));
		// VF(pi) -> v(pi) -> TODO
		grammar.addProduction(
				verbForm,
				new ParameterizedSymbol<>(verb, copy));
		// Args -> epsilon -> TODO
		grammar.addProduction(
				arguments);
		// Args(pi : Kopula) -> NP(pi) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, copy));
		// Args(pi : GenVal) -> NP(Gen) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Genitive))));
		// Args(pi : DatVal) -> NP(Dat) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Dative))));
		// Args(pi : AkkVal) -> NP(Akk) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative))));
		// Args(pi : AkkDatVal) -> NP(Akk) NP(Dat) -> TODO
		// grammar.addProduction(
		// arguments,
		// new ParameterizedSymbol<>(nounPhrase, new
		// SpecificParameterExpression<>(parameterManager, parameterizer, new
		// FormParameter(Casus.Accusative, null, null)))),
		// new ParameterizedSymbol<>(nounPhrase, new
		// SpecificParameterExpression<>(parameterManager, parameterizer, new
		// FormParameter(Casus.Dative, null, null)))));
		// AP -> epsilon
		grammar.addProduction(
				adverbalPhraseVar);
		grammar.addProduction(
				adverbalPhraseVar,
				adverbalPhrase,
				adverbalPhraseVar);

		// NP(pi) -> NF(pi) Attr(pi)*
		grammar.addProduction(
				nounPhrase,
				new ParameterizedSymbol<>(nounForm, copy),
				new ParameterizedSymbol<>(attributeVar, copy));
		// NF(pi) -> n(pi)
		grammar.addProduction(
				nounForm,
				new ParameterizedSymbol<>(noun, copy));
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
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Genitive))));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		for (Casus casus : Arrays.asList(Casus.Nominative, Casus.Accusative)) {
			for (Tense tense : Arrays.asList(Tense.Present, Tense.Perfect, Tense.Future)) {
				grammar.addProduction(nounPhrase,
						new SingletonParameterFactory<>(new FormParameter(casus)),
						new ParameterizedSymbol<>(sentence, new SpecificParameterExpression<>(parameterManager, parameterizer,
								new FormParameter(Casus.Accusative, tense, Finiteness.Infinitive))));
			}
		}

		EarleyParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());

		// lemmas

		Lemma serva = new SimpleLemma(LemmaType.Noun);
		Lemma servus = new SimpleLemma(LemmaType.Noun);
		Lemma plaustrum = new SimpleLemma(LemmaType.Noun);
		Lemma canto = new SimpleLemma(LemmaType.Verb);
		Lemma rideo = new SimpleLemma(LemmaType.Verb);

		// tokens

		List<Token> tokens = new ArrayList<>();
		tokens.add(new Token("serva", new Determination(serva, Casus.Nominative, Numerus.Singular, Genus.Feminine)));
		tokens.add(new Token("servi", new Determination(servus, Casus.Genitive, Numerus.Singular, Genus.Masculine)));
		tokens.add(new Token("cantat", new Determination(canto, Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite)));
		tokens.add(new Token("ridere", new Determination(rideo, Tense.Present, Voice.Active, Finiteness.Infinitive)));
		// tokens.add(new Token("plaustrum", new Determination(plaustrum,
		// Casus.Accusative, Numerus.Singular, Genus.Neuter))));

		EarleyParseResult<Token, FormParameter> result = parser.parse(tokens);

		new ChartSetPrinter<Token, FormParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));

		System.out.println(result.getBinarySyntaxTree());

		System.out.println(result.getSyntaxTree());
	}
}
