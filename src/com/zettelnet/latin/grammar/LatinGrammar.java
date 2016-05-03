package com.zettelnet.latin.grammar;

import static com.zettelnet.latin.grammar.LatinSymbol.Adjective;
import static com.zettelnet.latin.grammar.LatinSymbol.AdjectivePhrase;
import static com.zettelnet.latin.grammar.LatinSymbol.AdjectivePhraseOpt;
import static com.zettelnet.latin.grammar.LatinSymbol.Adverb;
import static com.zettelnet.latin.grammar.LatinSymbol.AdverbalPhrase;
import static com.zettelnet.latin.grammar.LatinSymbol.AdverbalPhraseVar;
import static com.zettelnet.latin.grammar.LatinSymbol.Arguments;
import static com.zettelnet.latin.grammar.LatinSymbol.Conjunction;
import static com.zettelnet.latin.grammar.LatinSymbol.Infinitive;
import static com.zettelnet.latin.grammar.LatinSymbol.Noun;
import static com.zettelnet.latin.grammar.LatinSymbol.NounForm;
import static com.zettelnet.latin.grammar.LatinSymbol.NounPhrase;
import static com.zettelnet.latin.grammar.LatinSymbol.NounPhraseOpt;
import static com.zettelnet.latin.grammar.LatinSymbol.Participle;
import static com.zettelnet.latin.grammar.LatinSymbol.Sentence;
import static com.zettelnet.latin.grammar.LatinSymbol.Verb;
import static com.zettelnet.latin.grammar.LatinSymbol.VerbForm;
import static com.zettelnet.latin.grammar.LatinSymbol.VerbPhrase;
import static com.zettelnet.latin.grammar.LatinHelper.copy;
import static com.zettelnet.latin.grammar.LatinHelper.key;
import static com.zettelnet.latin.grammar.LatinHelper.makeOptional;
import static com.zettelnet.latin.grammar.LatinHelper.specify;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.param.AnyParameterExpression;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.FormParameterManager;
import com.zettelnet.latin.param.FormParameterizer;
import com.zettelnet.latin.param.individual.IndividualFormParameterExpression;
import com.zettelnet.latin.token.Token;

public final class LatinGrammar {

	private LatinGrammar() {
	}

	public static Grammar<Token, FormParameter> makeGrammar() {
		// Management

		ParameterManager<FormParameter> parameterManager = new FormParameterManager();
		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();

		SimpleGrammar<Token, FormParameter> grammar = new SimpleGrammar<>(Sentence, parameterManager);
		grammar.setStartSymbolParameter(key(Casus.Nominative, Finiteness.Finite));

		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, parameterizer);
		ParameterExpression<Token, FormParameter> any = new AnyParameterExpression<>(parameterManager);

		// Productions

		// S(pi : !NullVal !Imp) -> NP(pi) VP(pi) -> TODO
		grammar.addProduction(Sentence,
				new ParameterizedSymbol<>(NounPhrase, copy),
				new ParameterizedSymbol<>(VerbPhrase, copy));
		// S(pi) -> VP(pi)
		grammar.addProduction(Sentence,
				new ParameterizedSymbol<>(VerbPhrase, copy));

		// VP(pi) -> VF(pi) Args(pi) AdvP*
		grammar.addProduction(
				VerbPhrase,
				new ParameterizedSymbol<>(VerbForm, copy),
				new ParameterizedSymbol<>(Arguments, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, any));

		// VF(pi) -> v(pi)
		grammar.addProduction(
				VerbForm,
				key(Finiteness.Finite),
				new ParameterizedSymbol<>(Verb, copy));
		// TODO
		grammar.addProduction(
				VerbForm,
				key(Finiteness.Infinitive),
				new ParameterizedSymbol<>(Infinitive, specify(parameterManager, parameterizer, Casus.Nominative)));
		grammar.addProduction(
				VerbForm,
				key(Finiteness.Participle),
				new ParameterizedSymbol<>(Participle, copy));

		// Args(pi : NullVal) -> epsilon
		grammar.addProduction(
				Arguments,
				key(Valency.Null));
		// Args(pi : SingleVal) -> epsilon
		grammar.addProduction(
				Arguments,
				key(Valency.Single));
		// Args(pi : Kopula) -> NP(pi)
		grammar.addProduction(
				Arguments,
				key(Valency.Copula),
				new ParameterizedSymbol<>(NounPhrase, copy));
		// Args(pi : GenVal) -> NP(Gen)
		grammar.addProduction(
				Arguments,
				key(Valency.Genitive),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Genitive)));
		// Args(pi : DatVal) -> NP(Dat)
		grammar.addProduction(
				Arguments,
				key(Valency.Dative),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		// Args(pi : AkkVal Akt) -> NP(Akk)
		grammar.addProduction(
				Arguments,
				key(Valency.Accusative, Voice.Active),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)));
		// Args(pi : AkkVal Pass) -> epsilon
		grammar.addProduction(
				Arguments,
				key(Valency.Accusative, Voice.Passive));
		// Args(pi : AkkDatVal Akt) -> NP(Akk) NP(Dat)
		grammar.addProduction(
				Arguments,
				key(Valency.AccusativeDative, Voice.Active),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		// Args(pi : AkkDatVal Pass) -> NP(Dat)
		grammar.addProduction(
				Arguments,
				key(Valency.AccusativeDative, Voice.Passive),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));

		// AdvP var
		grammar.addProduction(
				AdverbalPhraseVar);
		grammar.addProduction(
				AdverbalPhraseVar,
				AdverbalPhrase,
				AdverbalPhraseVar);

		// AdvP -> Adv
		grammar.addProduction(AdverbalPhrase,
				Adverb);

		// NP(pi : Fin) -> NF(pi) [AP(pi)] [NP(Gen)] [NP(pi)] [pron(pi)*] TODO
		grammar.addProduction(
				NounPhrase,
				key(Finiteness.Finite),
				new ParameterizedSymbol<>(NounForm, copy),
				new ParameterizedSymbol<>(AdjectivePhraseOpt, copy),
				new ParameterizedSymbol<>(NounPhraseOpt, specify(parameterManager, parameterizer, Casus.Genitive)),
				new ParameterizedSymbol<>(NounPhraseOpt, copy));
		// NF(pi) -> n(pi)
		grammar.addProduction(
				NounForm,
				new ParameterizedSymbol<>(Noun, copy));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		grammar.addProduction(
				NounPhrase,
				key(Casus.Nominative, Casus.Accusative),
				new ParameterizedSymbol<>(Sentence, specify(parameterManager, parameterizer, Casus.Accusative, Tense.Present, Tense.Perfect, Tense.Future, Finiteness.Infinitive)));

		// AP(pi) -> adj(pi) AdvP(pi)*
		grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(Adjective, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, copy));
		// AP(pi) -> VP(Participle)
		grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(VerbPhrase, new IndividualFormParameterExpression<>(parameterizer).specify(Finiteness.Participle).copy(Casus.class, Numerus.class, Genus.class)));

		// coordinations

		// NP(pi : Pl) -> NP(casus[pi]) conj NP(casus[pi])
		grammar.addProduction(
				NounPhrase,
				key(Numerus.Plural),
				new ParameterizedSymbol<>(NounPhrase, copy(parameterizer, Casus.class)),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(NounPhrase, copy(parameterizer, Casus.class)));

		// AP(pi) -> AP(?) conj AP(?)
		grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(AdjectivePhrase, copy),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(AdjectivePhrase, copy));
		grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(AdjectivePhrase, copy),
				new ParameterizedSymbol<>(AdjectivePhrase, copy));

		// VP(pi) -> VP(pi) conj VP(pi)
		grammar.addProduction(
				VerbPhrase,
				new ParameterizedSymbol<>(VerbPhrase, copy(parameterizer, Casus.class, Numerus.class, Genus.class, Finiteness.class)),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(VerbPhrase, copy(parameterizer, Casus.class, Numerus.class, Genus.class, Finiteness.class)));

		makeOptional(grammar, NounPhraseOpt, NounPhrase, copy);
		makeOptional(grammar, AdjectivePhraseOpt, AdjectivePhrase, copy);

		return grammar;
	}
}