package com.zettelnet.latin.grammar;

import static com.zettelnet.latin.grammar.LatinHelper.copy;
import static com.zettelnet.latin.grammar.LatinHelper.key;
import static com.zettelnet.latin.grammar.LatinHelper.specify;
import static com.zettelnet.latin.grammar.LatinHelper.vars;
import static com.zettelnet.latin.grammar.LatinHelper.makeOptional;
import static com.zettelnet.latin.grammar.LatinSymbol.Adjective;
import static com.zettelnet.latin.grammar.LatinSymbol.AdjectivePhrase;
import static com.zettelnet.latin.grammar.LatinSymbol.AdjectivePhraseOpt;
import static com.zettelnet.latin.grammar.LatinSymbol.Adverb;
import static com.zettelnet.latin.grammar.LatinSymbol.AdverbalPhrase;
import static com.zettelnet.latin.grammar.LatinSymbol.AdverbalPhraseVar;
import static com.zettelnet.latin.grammar.LatinSymbol.Arguments;
import static com.zettelnet.latin.grammar.LatinSymbol.Conjunction;
import static com.zettelnet.latin.grammar.LatinSymbol.Gerund;
import static com.zettelnet.latin.grammar.LatinSymbol.Infinitive;
import static com.zettelnet.latin.grammar.LatinSymbol.Interjection;
import static com.zettelnet.latin.grammar.LatinSymbol.Noun;
import static com.zettelnet.latin.grammar.LatinSymbol.NounForm;
import static com.zettelnet.latin.grammar.LatinSymbol.NounPhrase;
import static com.zettelnet.latin.grammar.LatinSymbol.NounPhraseOpt;
import static com.zettelnet.latin.grammar.LatinSymbol.Participle;
import static com.zettelnet.latin.grammar.LatinSymbol.Preposition;
import static com.zettelnet.latin.grammar.LatinSymbol.Pronoun;
import static com.zettelnet.latin.grammar.LatinSymbol.PronounOpt;
import static com.zettelnet.latin.grammar.LatinSymbol.Sentence;
import static com.zettelnet.latin.grammar.LatinSymbol.Subjunction;
import static com.zettelnet.latin.grammar.LatinSymbol.Supine;
import static com.zettelnet.latin.grammar.LatinSymbol.Verb;
import static com.zettelnet.latin.grammar.LatinSymbol.VerbForm;
import static com.zettelnet.latin.grammar.LatinSymbol.VerbPhrase;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.param.AnyParameterExpression;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.translate.AbstractTranslationTree;
import com.zettelnet.earley.translate.ConcreteTranslationTree;
import com.zettelnet.earley.translate.ParameterTranslator;
import com.zettelnet.earley.translate.PositionReference;
import com.zettelnet.earley.translate.SimpleTranslationSet;
import com.zettelnet.earley.translate.TranslationSet;
import com.zettelnet.german.grammar.GermanSymbol;
import com.zettelnet.german.token.GermanToken;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.FormParameterManager;
import com.zettelnet.latin.param.FormParameterizer;
import com.zettelnet.latin.param.coordinative.CoordinativeFormParameterExpression;
import com.zettelnet.latin.param.coordinative.SubjunctionMoodPropertyExpression;
import com.zettelnet.latin.param.individual.IndividualFormParameterExpression;
import com.zettelnet.latin.token.Token;
import com.zettelnet.translate.latger.LatinGermanParameterTranslator;

public final class LatinGrammar {

	private static final SimpleGrammar<Token, FormParameter> grammar;
	private static final SimpleTranslationSet<Token, FormParameter, GermanToken, FormParameter> toGerman;

	private LatinGrammar() {
	}

	static {
		// Management

		FormParameterManager<Token> parameterManager = new FormParameterManager<>(LatinSymbol.DEFAULT_PROPERTY_TYPES);
		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();

		grammar = new SimpleGrammar<>(Sentence, parameterManager, parameterizer);
		grammar.setStartSymbolParameter(key(Casus.Nominative, Finiteness.Finite));

		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar);
		ParameterExpression<Token, FormParameter> any = new AnyParameterExpression<>(parameterManager);

		toGerman = new SimpleTranslationSet<>();
		FormParameterManager<GermanToken> germanParameterManager = new FormParameterManager<>(GermanSymbol.DEFAULT_PROPERTY_TYPES);
		ParameterTranslator<Token, FormParameter, GermanToken, FormParameter> germanize = LatinGermanParameterTranslator.makeInstance(germanParameterManager);

		// Productions
		Production<Token, FormParameter> prod = null;

		// S(pi : !NullVal !Imp) -> NP(pi) VP(pi) -> TODO
		prod = grammar.addProduction(Sentence, 0.7,
				new ParameterizedSymbol<>(NounPhrase, copy),
				new ParameterizedSymbol<>(VerbPhrase, copy));
		// S(pi) => S(~pi) { ~NP ~VP }
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Sentence, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));

		// S(pi) -> VP(pi)
		prod = grammar.addProduction(Sentence, 0.3,
				new ParameterizedSymbol<>(VerbPhrase, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Sentence, germanize, 1,
						vars(new ConcreteTranslationTree<>(GermanSymbol.Pronoun, germanize, 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));

		// VP(pi) -> VF(pi) Args(pi) AdvP*
		prod = grammar.addProduction(
				VerbPhrase, 0.9,
				new ParameterizedSymbol<>(VerbForm, copy),
				new ParameterizedSymbol<>(Arguments, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, any));
		// VP(pi) => VP(~pi) { ~VF ~AdvP* ~Args }
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.VerbPhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(2), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));

		// VF(pi) -> v(pi)
		prod = grammar.addProduction(
				VerbForm, key(Finiteness.Finite), 0.9,
				new ParameterizedSymbol<>(Verb, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		prod = grammar.addProduction(
				VerbForm, key(Finiteness.Infinitive), 0.9,
				new ParameterizedSymbol<>(Infinitive, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		prod = grammar.addProduction(
				VerbForm, key(Finiteness.Participle), 0.9,
				new ParameterizedSymbol<>(Participle, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		prod = grammar.addProduction(
				VerbForm, key(Finiteness.Gerund), 0.9,
				new ParameterizedSymbol<>(Gerund, copy));
		prod = grammar.addProduction(
				VerbForm, key(Finiteness.Supine), 0.9,
				new ParameterizedSymbol<>(Supine, copy));

		// Args(pi : NullVal) -> epsilon
		prod = grammar.addProduction(
				Arguments, key(Valency.Null), 1);
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1)));
		// Args(pi : SingleVal) -> epsilon
		prod = grammar.addProduction(
				Arguments, key(Valency.Single), 1);
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1)));
		// Args(pi : Kopula) -> NP(pi)
		prod = grammar.addProduction(
				Arguments, key(Valency.Copula), 1,
				new ParameterizedSymbol<>(NounPhrase, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// Args(pi : GenVal) -> NP(Gen)
		prod = grammar.addProduction(
				Arguments, key(Valency.Genitive), 1,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Genitive)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// Args(pi : DatVal) -> NP(Dat)
		prod = grammar.addProduction(
				Arguments, key(Valency.Dative), 1,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Dative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// Args(pi : AkkVal Akt) -> NP(Akk)
		prod = grammar.addProduction(
				Arguments, key(Valency.Accusative, Voice.Active), 1,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Accusative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// Args(pi : AkkVal Pass) -> epsilon
		prod = grammar.addProduction(
				Arguments, key(Valency.Accusative, Voice.Passive), 1);
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1)));
		// Args(pi : AkkDatVal Akt) -> NP(Akk) NP(Dat)
		prod = grammar.addProduction(
				Arguments, key(Valency.AccusativeDative, Voice.Active), 1,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Accusative)),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Dative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));
		// Args(pi : AkkDatVal Pass) -> NP(Dat)
		prod = grammar.addProduction(
				Arguments, key(Valency.AccusativeDative, Voice.Passive), 1,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Dative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// Args(pi : InfVal) -> VP(Inf Ind)
		prod = grammar.addProduction(
				Arguments, key(Valency.Infinitive), 1,
				new ParameterizedSymbol<>(VerbPhrase, specify(parameterManager, Finiteness.Infinitive, Mood.Indicative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));

		// AdvP var
		prod = grammar.addProduction(
				AdverbalPhraseVar, 0.8);
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhraseVar, germanize, 1)));
		prod = grammar.addProduction(
				AdverbalPhraseVar, 0.2,
				AdverbalPhrase,
				AdverbalPhraseVar);
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhraseVar, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));

		// AdvP -> adv
		prod = grammar.addProduction(
				AdverbalPhrase, 0.4,
				new ParameterizedSymbol<>(Adverb, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// AdvP(pi) -> subj(pi) S(pi)
		prod = grammar.addProduction(
				AdverbalPhrase, 0.3,
				new ParameterizedSymbol<>(Subjunction, copy),
				new ParameterizedSymbol<>(Sentence, new CoordinativeFormParameterExpression<>(parameterManager).with(new SubjunctionMoodPropertyExpression())));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));
		// AdvP -> NP(Abl)
		prod = grammar.addProduction(
				AdverbalPhrase, 0.2,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, Casus.Ablative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhrase, germanize, 1,
						vars(new ConcreteTranslationTree<>(GermanSymbol.Preposition, germanize, 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// AdvP -> NP(Voc & ng[pi])
		prod = grammar.addProduction(
				AdverbalPhrase, 0.1,
				new ParameterizedSymbol<>(NounPhrase, new IndividualFormParameterExpression<>(parameterManager).copy(Numerus.TYPE, Genus.TYPE).specify(Casus.Vocative)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));

		// NP(pi : Fin) -> NF(pi) [AP(pi)] [NP(Gen)] [NP(pi)]
		prod = grammar.addProduction(
				NounPhrase, 0.8,
				new ParameterizedSymbol<>(NounForm, copy),
				new ParameterizedSymbol<>(AdjectivePhraseOpt, copy),
				new ParameterizedSymbol<>(NounPhraseOpt, specify(parameterManager, Casus.Genitive)),
				new ParameterizedSymbol<>(NounPhraseOpt, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.NounPhrase, germanize, 1,
						vars(new ConcreteTranslationTree<>(GermanSymbol.Article, germanize, 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(3), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(2), 1)))));

		// NF(pi) -> n(pi)
		prod = grammar.addProduction(
				NounForm, 1,
				new ParameterizedSymbol<>(Noun, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.NounForm, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		prod = grammar.addProduction(
				NounPhrase, key(Casus.Nominative, Casus.Accusative), 0.1,
				new ParameterizedSymbol<>(Sentence, specify(parameterManager, Casus.Accusative, Tense.Present, Tense.Perfect, Tense.Future, Finiteness.Infinitive)));
		// TODO
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.NounPhrase, germanize, 1,
						vars(new ConcreteTranslationTree<>(GermanSymbol.Conjunction, germanize, 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));

		// AP(pi) -> adj(pi) AdvP(pi)*
		prod = grammar.addProduction(
				AdjectivePhrase, 0.3,
				new ParameterizedSymbol<>(Adjective, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, copy));
		toGerman.addTranslation(prod, parameterManager, vars(
				new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));
		// AP(pi) -> pron(pi) AdvP(pi)*
		prod = grammar.addProduction(
				AdjectivePhrase, 0.3,
				new ParameterizedSymbol<>(Pronoun, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));
		// AP(pi) -> VP(Participle)
		prod = grammar.addProduction(
				AdjectivePhrase, 0.3,
				new ParameterizedSymbol<>(VerbPhrase, new IndividualFormParameterExpression<>(parameterManager).specify(Finiteness.Participle).copy(Casus.TYPE, Numerus.TYPE, Genus.TYPE)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)))));

		// coordinations

		// NP(pi : Pl) -> NP(casus[pi]) conj NP(casus[pi])
		prod = grammar.addProduction(
				NounPhrase, key(Numerus.Plural), 0.1,
				new ParameterizedSymbol<>(NounPhrase, copy(parameterManager, Casus.TYPE)),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(NounPhrase, copy(parameterManager, Casus.TYPE)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.NounPhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(2), 1)))));

		// AP(pi) -> AP(?) [conj] AP(?)
		prod = grammar.addProduction(
				AdjectivePhrase, 0.05,
				new ParameterizedSymbol<>(AdjectivePhrase, copy),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(AdjectivePhrase, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(2), 1)))));
		prod = grammar.addProduction(
				AdjectivePhrase, 0.05,
				new ParameterizedSymbol<>(AdjectivePhrase, copy),
				new ParameterizedSymbol<>(AdjectivePhrase, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)))));

		// VP(pi) -> VP(pi) conj VP(pi)
		prod = grammar.addProduction(
				VerbPhrase, 0.1,
				new ParameterizedSymbol<>(VerbPhrase, copy(parameterManager, Casus.TYPE, Numerus.TYPE, Genus.TYPE, Finiteness.TYPE)),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(VerbPhrase, copy(parameterManager, Casus.TYPE, Numerus.TYPE, Genus.TYPE, Finiteness.TYPE)));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.VerbPhrase, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(2), 1)))));

		// VF(pi) -> VF(pi) conj VF(pi)
		prod = grammar.addProduction(
				VerbForm, 0.1,
				new ParameterizedSymbol<>(VerbForm, copy),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(VerbForm, copy));
		toGerman.addTranslation(prod, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, 1,
						vars(new AbstractTranslationTree<>(new PositionReference<>(0), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(1), 1)),
						vars(new AbstractTranslationTree<>(new PositionReference<>(2), 1)))));

		makeOptional(grammar, NounPhraseOpt, NounPhrase, copy, GermanSymbol.NounPhraseOpt, GermanSymbol.NounPhrase, toGerman, germanize, 0.2);
		makeOptional(grammar, AdjectivePhraseOpt, AdjectivePhrase, copy, GermanSymbol.AdjectivePhraseOpt, GermanSymbol.AdjectivePhrase, toGerman, germanize, 0.2);
		makeOptional(grammar, PronounOpt, Pronoun, copy, GermanSymbol.PronounOpt, GermanSymbol.Pronoun, toGerman, germanize, 0.2);

		// Terminal translations
		toGerman.addTranslation(Verb, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Verb, germanize, 1)));
		toGerman.addTranslation(Noun, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Noun, germanize, 1)));
		toGerman.addTranslation(Adverb, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Adverb, germanize, 1)));
		toGerman.addTranslation(Adjective, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Adjective, germanize, 1)));
		toGerman.addTranslation(Conjunction, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Conjunction, germanize, 1)));
		toGerman.addTranslation(Subjunction, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Subjunction, germanize, 1)));
		toGerman.addTranslation(Infinitive, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Infinitive, germanize, 1)));
		toGerman.addTranslation(Participle, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Participle, germanize, 1)));
		toGerman.addTranslation(Preposition, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Preposition, germanize, 1)));
		toGerman.addTranslation(Interjection, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Interjection, germanize, 1)));
		toGerman.addTranslation(Pronoun, parameterManager,
				vars(new ConcreteTranslationTree<>(GermanSymbol.Pronoun, germanize, 1)));
	}

	public static Grammar<Token, FormParameter> makeGrammar() {
		return grammar;
	}

	public static TranslationSet<Token, FormParameter, GermanToken, FormParameter> makeGermanTranslations() {
		return toGerman;
	}
}
