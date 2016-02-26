package com.zettelnet.earley.test.latin;

import java.util.Arrays;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
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
import com.zettelnet.latin.Form;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Finiteness;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Valency;
import com.zettelnet.latin.lemma.Lemma;

public final class LatinGrammar {

	public static Grammar<Token, FormParameter> makeGrammar() {
		// Symbols

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");

		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> nounForm = new SimpleNonTerminal<>("NF");
		NonTerminal<Token> attribute = new SimpleNonTerminal<>("Attr");
		NonTerminal<Token> attributeVar = new SimpleNonTerminal<>("Attr&lowast;");

		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");
		NonTerminal<Token> verbForm = new SimpleNonTerminal<>("VF");
		NonTerminal<Token> arguments = new SimpleNonTerminal<>("Args");
		NonTerminal<Token> adverbalPhrase = new SimpleNonTerminal<>("AP");
		NonTerminal<Token> adverbalPhraseVar = new SimpleNonTerminal<>("AP&lowast;");

		Terminal<Token> verb = new LemmaTerminal(Lemma.Type.Verb);
		Terminal<Token> noun = new LemmaTerminal(Lemma.Type.Noun);

		// Management

		ParameterManager<FormParameter> parameterManager = new FormParameterManager();
		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();

		Grammar<Token, FormParameter> grammar = new Grammar<>(sentence, parameterManager);
		grammar.setStartSymbolParameter(new SingletonParameterFactory<>(new FormParameter(Casus.Nominative, Person.Third, Finiteness.Finite)));

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
		// Args(pi : Kopula) -> NP(pi)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Copula)),
				new ParameterizedSymbol<>(nounPhrase, copy));
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
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		for (Casus casus : Arrays.asList(Casus.Nominative, Casus.Accusative)) {
			for (Tense tense : Arrays.asList(Tense.Present)) {
				grammar.addProduction(nounPhrase,
						new SingletonParameterFactory<>(new FormParameter(Form.nounForm(casus, null, null))),
						new ParameterizedSymbol<>(sentence, new SpecificParameterExpression<>(parameterManager, parameterizer,
								new FormParameter(Casus.Accusative, tense, Finiteness.Infinitive))));
			}
		}

		return grammar;
	}
}