package com.zettelnet.earley.test.latin;

import static com.zettelnet.earley.test.latin.LatinHelper.copy;
import static com.zettelnet.earley.test.latin.LatinHelper.key;
import static com.zettelnet.earley.test.latin.LatinHelper.specify;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.param.AnyParameterExpression;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;

public final class LatinGrammar {

	private LatinGrammar() {
	}
	
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
		grammar.setStartSymbolParameter(key(Casus.Nominative, Finiteness.Finite));

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
				key(Finiteness.Finite),
				new ParameterizedSymbol<>(verb, copy));
		// TODO
		grammar.addProduction(
				verbForm,
				key(Finiteness.Infinitive),
				new ParameterizedSymbol<>(infinitive, specify(parameterManager, parameterizer, Casus.Nominative)));

		// Args(pi : NullVal) -> epsilon
		grammar.addProduction(
				arguments,
				key(Valency.Null));
		// Args(pi : SingleVal) -> epsilon
		grammar.addProduction(
				arguments,
				key(Valency.Single));
		// Args(pi : Kopula) -> NP(pi)
		grammar.addProduction(
				arguments,
				key(Valency.Copula),
				new ParameterizedSymbol<>(nounPhrase, copy));
		// Args(pi : GenVal) -> NP(Gen)
		grammar.addProduction(
				arguments,
				key(Valency.Genitive),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Genitive)));
		// Args(pi : DatVal) -> NP(Dat)
		grammar.addProduction(
				arguments,
				key(Valency.Dative),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		// Args(pi : AkkVal) -> NP(Akk)
		grammar.addProduction(
				arguments,
				key(Valency.Accusative),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)));
		// Args(pi : AkkDatVal) -> NP(Akk) NP(Dat)
		grammar.addProduction(
				arguments,
				key(Valency.AccusativeDative),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));

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
				key(Finiteness.Finite),
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
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Genitive)));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		grammar.addProduction(
				nounPhrase,
				key(Casus.Nominative, Casus.Accusative),
				new ParameterizedSymbol<>(sentence, specify(parameterManager, parameterizer, Casus.Accusative, Tense.Present, Tense.Perfect, Tense.Future, Finiteness.Infinitive)));

		// coordinations

		// NP(pi : Pl) -> NP(?) conj NP(?)
		grammar.addProduction(
				nounPhrase,
				key(Numerus.Plural),
				new ParameterizedSymbol<>(nounPhrase, copy(parameterizer, Casus.class)),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(nounPhrase, copy(parameterizer, Casus.class)));

		// VP(pi) -> VP(pi) conj VP(pi)
		grammar.addProduction(
				verbPhrase,
				new ParameterizedSymbol<>(verbPhrase, copy(parameterizer, Casus.class, Numerus.class, Genus.class, Finiteness.class)),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(verbPhrase, copy(parameterizer, Casus.class, Numerus.class, Genus.class, Finiteness.class)));

		return grammar;
	}
}
