package com.zettelnet.earley.test.latin;

import static com.zettelnet.earley.test.latin.LatinHelper.copy;
import static com.zettelnet.earley.test.latin.LatinHelper.key;
import static com.zettelnet.earley.test.latin.LatinHelper.makeOptional;
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
import com.zettelnet.latin.form.Voice;
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
		NonTerminal<Token> nounPhraseOpt = new SimpleNonTerminal<>("[NP]");
		NonTerminal<Token> nounForm = new SimpleNonTerminal<>("NF");
		NonTerminal<Token> adjectivePhrase = new SimpleNonTerminal<>("AP");
		NonTerminal<Token> adjectivePhraseOpt = new SimpleNonTerminal<>("[AP]");

		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");
		NonTerminal<Token> verbForm = new SimpleNonTerminal<>("VF");
		NonTerminal<Token> arguments = new SimpleNonTerminal<>("Args");
		NonTerminal<Token> adverbalPhrase = new SimpleNonTerminal<>("AdvP");
		NonTerminal<Token> adverbalPhraseVar = new SimpleNonTerminal<>("AdvP*");

		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb);
		Terminal<Token> noun = new LemmaTerminal(LemmaType.Noun);
		Terminal<Token> adverb = new LemmaTerminal(LemmaType.Adverb);
		Terminal<Token> adjective = new LemmaTerminal(LemmaType.Adjective);
		Terminal<Token> conjunction = new LemmaTerminal(LemmaType.Conjunction);
		Terminal<Token> infinitive = new LemmaTerminal(LemmaType.Infinitive);
		Terminal<Token> participle = new LemmaTerminal(LemmaType.Participle);

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

		// VP(pi) -> VF(pi) Args(pi) AdvP*
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
		grammar.addProduction(
				verbForm,
				key(Finiteness.Participle),
				new ParameterizedSymbol<>(participle, copy));

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
		// Args(pi : AkkVal Akt) -> NP(Akk)
		grammar.addProduction(
				arguments,
				key(Valency.Accusative, Voice.Active),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)));
		// Args(pi : AkkVal Pass) -> epsilon
		grammar.addProduction(
				arguments,
				key(Valency.Accusative, Voice.Passive));
		// Args(pi : AkkDatVal Akt) -> NP(Akk) NP(Dat)
		grammar.addProduction(
				arguments,
				key(Valency.AccusativeDative, Voice.Active),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		// Args(pi : AkkDatVal Pass) -> NP(Dat)
		grammar.addProduction(
				arguments,
				key(Valency.AccusativeDative, Voice.Passive),
				new ParameterizedSymbol<>(nounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));

		// AdvP var
		grammar.addProduction(
				adverbalPhraseVar);
		grammar.addProduction(
				adverbalPhraseVar,
				adverbalPhrase,
				adverbalPhraseVar);

		// AdvP -> Adv
		grammar.addProduction(adverbalPhrase,
				adverb);

		// NP(pi : Fin) -> NF(pi) [AP(pi)] [NP(Gen)] [NP(pi)] [pron(pi)*] TODO
		grammar.addProduction(
				nounPhrase,
				key(Finiteness.Finite),
				new ParameterizedSymbol<>(nounForm, copy),
				new ParameterizedSymbol<>(adjectivePhraseOpt, copy),
				new ParameterizedSymbol<>(nounPhraseOpt, specify(parameterManager, parameterizer, Casus.Genitive)),
				new ParameterizedSymbol<>(nounPhraseOpt, copy));
		// NF(pi) -> n(pi)
		grammar.addProduction(
				nounForm,
				new ParameterizedSymbol<>(noun, copy));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		grammar.addProduction(
				nounPhrase,
				key(Casus.Nominative, Casus.Accusative),
				new ParameterizedSymbol<>(sentence, specify(parameterManager, parameterizer, Casus.Accusative, Tense.Present, Tense.Perfect, Tense.Future, Finiteness.Infinitive)));

		// AP(pi) -> adj(pi) AdvP(pi)*
		grammar.addProduction(
				adjectivePhrase,
				new ParameterizedSymbol<>(adjective, copy),
				new ParameterizedSymbol<>(adverbalPhraseVar, copy));
		// AP(pi) -> VP(Participle)
		grammar.addProduction(
				adjectivePhrase,
				new ParameterizedSymbol<>(verbPhrase, specify(parameterManager, parameterizer, Finiteness.Participle)));

		// coordinations

		// NP(pi : Pl) -> NP(casus[pi]) conj NP(casus[pi])
		grammar.addProduction(
				nounPhrase,
				key(Numerus.Plural),
				new ParameterizedSymbol<>(nounPhrase, copy(parameterizer, Casus.class)),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(nounPhrase, copy(parameterizer, Casus.class)));

		// AP(pi) -> AP(?) conj AP(?)
		grammar.addProduction(
				adjectivePhrase,
				new ParameterizedSymbol<>(adjectivePhrase, copy),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(adjectivePhrase, copy));
		grammar.addProduction(
				adjectivePhrase,
				new ParameterizedSymbol<>(adjectivePhrase, copy),
				new ParameterizedSymbol<>(adjectivePhrase, copy));

		// VP(pi) -> VP(pi) conj VP(pi)
		grammar.addProduction(
				verbPhrase,
				new ParameterizedSymbol<>(verbPhrase, copy(parameterizer, Casus.class, Numerus.class, Genus.class, Finiteness.class)),
				new ParameterizedSymbol<>(conjunction, any),
				new ParameterizedSymbol<>(verbPhrase, copy(parameterizer, Casus.class, Numerus.class, Genus.class, Finiteness.class)));

		makeOptional(grammar, nounPhraseOpt, nounPhrase, copy);
		makeOptional(grammar, adjectivePhraseOpt, adjectivePhrase, copy);

		return grammar;
	}
}
