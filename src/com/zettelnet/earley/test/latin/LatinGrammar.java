package com.zettelnet.earley.test.latin;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.SimpleGrammar;
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
import com.zettelnet.earley.test.latin.individual.IndividualFormParameterExpression;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;

public final class LatinGrammar {

	public static Grammar<Token, FormParameter> makeGrammar() {
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
		Terminal<Token> conjunction = new LemmaTerminal(LemmaType.Conjunction);
		Terminal<Token> infinitive = new LemmaTerminal(LemmaType.Infinitive);

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

		// VF(pi) -> v(pi)
		grammar.addProduction(
				verbForm,
				new SingletonParameterFactory<>(new FormParameter(Finiteness.Finite)),
				new ParameterizedSymbol<>(verb, copy));
		// TODO
		grammar.addProduction(
				verbForm,
				new SingletonParameterFactory<>(new FormParameter(Finiteness.Infinitive)),
				new ParameterizedSymbol<>(infinitive, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Nominative))));

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
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Nominative))));
		// Args(pi : GenVal) -> NP(Gen)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Genitive)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Genitive))));
		// Args(pi : DatVal) -> NP(Dat)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Dative)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Dative))));
		// Args(pi : AkkVal) -> NP(Akk)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.Accusative)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative))));
		// Args(pi : AkkDatVal) -> NP(Akk) NP(Dat)
		grammar.addProduction(
				arguments,
				new SingletonParameterFactory<>(new FormParameter(Valency.AccusativeDative)),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative))),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Dative))));

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
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Genitive))));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		grammar.addProduction(
				nounPhrase,
				new SingletonParameterFactory<>(new FormParameter(Casus.Nominative, Casus.Accusative)),
				new ParameterizedSymbol<>(sentence, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative, Tense.Present, Tense.Perfect, Tense.Future, Finiteness.Infinitive))));

		// coordinations

		// NP(pi : Pl) -> NP(?) conj NP(?)
		grammar.addProduction(
				nounPhrase,
				new SingletonParameterFactory<>(new FormParameter(Numerus.Plural)),
				new ParameterizedSymbol<>(nounPhrase, new IndividualFormParameterExpression<>(parameterizer).copy(Casus.class)),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(nounPhrase, new IndividualFormParameterExpression<>(parameterizer).copy(Casus.class)));

		// VP(pi) -> VP(pi) conj VP(pi)
		grammar.addProduction(
				verbPhrase,
				new ParameterizedSymbol<>(verbPhrase, any),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(verbPhrase, any));

		return grammar;
	}
}
