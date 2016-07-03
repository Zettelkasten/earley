package com.zettelnet.latin.grammar;

import static com.zettelnet.latin.grammar.LatinHelper.copy;
import static com.zettelnet.latin.grammar.LatinHelper.key;
import static com.zettelnet.latin.grammar.LatinHelper.makeOptional;
import static com.zettelnet.latin.grammar.LatinHelper.specify;
import static com.zettelnet.latin.grammar.LatinHelper.translate;
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
import com.zettelnet.earley.param.ParameterManager;
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

public final class LatinGrammar {

	private static final SimpleGrammar<Token, FormParameter> grammar;
	private static final SimpleTranslationSet<Token, FormParameter, GermanToken, FormParameter> toGerman;

	private LatinGrammar() {
	}

	static {
		// Management

		ParameterManager<Token, FormParameter> parameterManager = new FormParameterManager<>(LatinSymbol.DEFAULT_PROPERTY_TYPES);
		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();

		grammar = new SimpleGrammar<>(Sentence, parameterManager);
		grammar.setStartSymbolParameter(key(Casus.Nominative, Finiteness.Finite));

		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, parameterizer);
		ParameterExpression<Token, FormParameter> any = new AnyParameterExpression<>(parameterManager);

		toGerman = new SimpleTranslationSet<>();
		ParameterTranslator<Token, FormParameter, FormParameter> germanize = null;

		// Productions
		Production<Token, FormParameter> prod = null;

		// S(pi : !NullVal !Imp) -> NP(pi) VP(pi) -> TODO
		prod = grammar.addProduction(Sentence,
				new ParameterizedSymbol<>(NounPhrase, copy),
				new ParameterizedSymbol<>(VerbPhrase, copy));
		// S(pi) => S(~pi) { ~NP ~VP }
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Sentence, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));

		// S(pi) -> VP(pi)
		prod = grammar.addProduction(Sentence,
				new ParameterizedSymbol<>(VerbPhrase, copy));

		// VP(pi) -> VF(pi) Args(pi) AdvP*
		prod = grammar.addProduction(
				VerbPhrase,
				new ParameterizedSymbol<>(VerbForm, copy),
				new ParameterizedSymbol<>(Arguments, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, any));
		// VP(pi) => VP(~pi) { ~VF ~AdvP* ~Args }
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.VerbPhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(2)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));

		// VF(pi) -> v(pi)
		prod = grammar.addProduction(
				VerbForm,
				key(Finiteness.Finite),
				new ParameterizedSymbol<>(Verb, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		prod = grammar.addProduction(
				VerbForm,
				key(Finiteness.Infinitive),
				new ParameterizedSymbol<>(Infinitive, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		prod = grammar.addProduction(
				VerbForm,
				key(Finiteness.Participle),
				new ParameterizedSymbol<>(Participle, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		prod = grammar.addProduction(
				VerbForm,
				key(Finiteness.Gerund),
				new ParameterizedSymbol<>(Gerund, copy));
		prod = grammar.addProduction(
				VerbForm,
				key(Finiteness.Supine),
				new ParameterizedSymbol<>(Supine, copy));

		// Args(pi : NullVal) -> epsilon
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Null));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize)));
		// Args(pi : SingleVal) -> epsilon
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Single));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize)));
		// Args(pi : Kopula) -> NP(pi)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Copula),
				new ParameterizedSymbol<>(NounPhrase, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// Args(pi : GenVal) -> NP(Gen)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Genitive),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Genitive)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// Args(pi : DatVal) -> NP(Dat)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Dative),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// Args(pi : AkkVal Akt) -> NP(Akk)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Accusative, Voice.Active),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// Args(pi : AkkVal Pass) -> epsilon
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Accusative, Voice.Passive));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize)));
		// Args(pi : AkkDatVal Akt) -> NP(Akk) NP(Dat)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.AccusativeDative, Voice.Active),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Accusative)),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));
		// Args(pi : AkkDatVal Pass) -> NP(Dat)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.AccusativeDative, Voice.Passive),
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Dative)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// Args(pi : InfVal) -> VP(Inf Ind)
		prod = grammar.addProduction(
				Arguments,
				key(Valency.Infinitive),
				new ParameterizedSymbol<>(VerbPhrase, specify(parameterManager, parameterizer, Finiteness.Infinitive, Mood.Indicative)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Arguments, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));

		// AdvP var
		prod = grammar.addProduction(
				AdverbalPhraseVar);
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhraseVar, germanize)));
		prod = grammar.addProduction(
				AdverbalPhraseVar,
				AdverbalPhrase,
				AdverbalPhraseVar);
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhraseVar, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1)),
						new AbstractTranslationTree<>(new PositionReference<>(2))))));

		// AdvP -> Adv
		prod = grammar.addProduction(
				AdverbalPhrase,
				Adverb);
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// AdvP(pi) -> subj(pi) S(pi)
		prod = grammar.addProduction(
				AdverbalPhrase,
				new ParameterizedSymbol<>(Subjunction, copy),
				new ParameterizedSymbol<>(Sentence, new CoordinativeFormParameterExpression<>(parameterManager, parameterizer).with(new SubjunctionMoodPropertyExpression())));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdverbalPhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));
		// AdvP -> NP(Abl)
		prod = grammar.addProduction(
				AdverbalPhrase,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Ablative)));
		// AdvP -> NP(Voc)
		prod = grammar.addProduction(
				AdverbalPhrase,
				new ParameterizedSymbol<>(NounPhrase, specify(parameterManager, parameterizer, Casus.Vocative)));

		// NP(pi : Fin) -> NF(pi) [AP(pi)] [NP(Gen)] [NP(pi)]
		prod = grammar.addProduction(
				NounPhrase,
				new ParameterizedSymbol<>(NounForm, copy),
				new ParameterizedSymbol<>(AdjectivePhraseOpt, copy),
				new ParameterizedSymbol<>(NounPhraseOpt, specify(parameterManager, parameterizer, Casus.Genitive)),
				new ParameterizedSymbol<>(NounPhraseOpt, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.NounPhrase, germanize, translate(
						new ConcreteTranslationTree<>(GermanSymbol.Article, germanize),
						new AbstractTranslationTree<>(new PositionReference<>(1)),
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(3)),
						new AbstractTranslationTree<>(new PositionReference<>(2))))));

		// NF(pi) -> n(pi)
		prod = grammar.addProduction(
				NounForm,
				new ParameterizedSymbol<>(Noun, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.NounForm, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));
		// NP(pi : Nom / Akk) -> S(pi : Inf Pr�s/Perf/Fut Akk)
		prod = grammar.addProduction(
				NounPhrase,
				key(Casus.Nominative, Casus.Accusative),
				new ParameterizedSymbol<>(Sentence, specify(parameterManager, parameterizer, Casus.Accusative, Tense.Present, Tense.Perfect, Tense.Future, Finiteness.Infinitive)));
		// TODO
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.NounPhrase, germanize, translate(
						new ConcreteTranslationTree<>(GermanSymbol.Conjunction, germanize),
						new AbstractTranslationTree<>(new PositionReference<>(0))))));

		// AP(pi) -> adj(pi) AdvP(pi)*
		prod = grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(Adjective, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));
		// AP(pi) -> pron(pi) AdvP(pi)*
		prod = grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(Pronoun, copy),
				new ParameterizedSymbol<>(AdverbalPhraseVar, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));
		// AP(pi) -> VP(Participle)
		prod = grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(VerbPhrase, new IndividualFormParameterExpression<>(parameterManager, parameterizer).specify(Finiteness.Participle).copy(Casus.TYPE, Numerus.TYPE, Genus.TYPE)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0))))));

		// coordinations

		// NP(pi : Pl) -> NP(casus[pi]) conj NP(casus[pi])
		prod = grammar.addProduction(
				NounPhrase,
				key(Numerus.Plural),
				new ParameterizedSymbol<>(NounPhrase, copy(parameterManager, parameterizer, Casus.TYPE)),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(NounPhrase, copy(parameterManager, parameterizer, Casus.TYPE)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.NounPhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1)),
						new AbstractTranslationTree<>(new PositionReference<>(2))))));

		// AP(pi) -> AP(?) [conj] AP(?)
		prod = grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(AdjectivePhrase, copy),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(AdjectivePhrase, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1)),
						new AbstractTranslationTree<>(new PositionReference<>(2))))));
		prod = grammar.addProduction(
				AdjectivePhrase,
				new ParameterizedSymbol<>(AdjectivePhrase, copy),
				new ParameterizedSymbol<>(AdjectivePhrase, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.AdjectivePhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1))))));

		// VP(pi) -> VP(pi) conj VP(pi)
		prod = grammar.addProduction(
				VerbPhrase,
				new ParameterizedSymbol<>(VerbPhrase, copy(parameterManager, parameterizer, Casus.TYPE, Numerus.TYPE, Genus.TYPE, Finiteness.TYPE)),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(VerbPhrase, copy(parameterManager, parameterizer, Casus.TYPE, Numerus.TYPE, Genus.TYPE, Finiteness.TYPE)));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.VerbPhrase, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1)),
						new AbstractTranslationTree<>(new PositionReference<>(2))))));

		// VF(pi) -> VF(pi) conj VF(pi)
		prod = grammar.addProduction(
				VerbForm,
				new ParameterizedSymbol<>(VerbForm, copy),
				new ParameterizedSymbol<>(Conjunction, any),
				new ParameterizedSymbol<>(VerbForm, copy));
		toGerman.addTranslation(prod, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.VerbForm, germanize, translate(
						new AbstractTranslationTree<>(new PositionReference<>(0)),
						new AbstractTranslationTree<>(new PositionReference<>(1)),
						new AbstractTranslationTree<>(new PositionReference<>(2))))));
		
		makeOptional(grammar, NounPhraseOpt, NounPhrase, copy, GermanSymbol.NounPhraseOpt, GermanSymbol.NounPhrase, toGerman, germanize);
		makeOptional(grammar, AdjectivePhraseOpt, AdjectivePhrase, copy, GermanSymbol.AdjectivePhraseOpt, GermanSymbol.AdjectivePhrase, toGerman, germanize);
		makeOptional(grammar, PronounOpt, Pronoun, copy, GermanSymbol.PronounOpt, GermanSymbol.Pronoun, toGerman, germanize);
		
		// Terminal translations
		toGerman.addTranslation(Verb, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Verb, germanize)));
		toGerman.addTranslation(Noun, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Noun, germanize)));
		toGerman.addTranslation(Adverb, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Adverb, germanize)));
		toGerman.addTranslation(Adjective, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Adjective, germanize)));
		toGerman.addTranslation(Conjunction, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Conjunction, germanize)));
		toGerman.addTranslation(Subjunction, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Subjunction, germanize)));
		toGerman.addTranslation(Infinitive, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Infinitive, germanize)));
		toGerman.addTranslation(Participle, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Participle, germanize)));
		toGerman.addTranslation(Preposition, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Preposition, germanize)));
		toGerman.addTranslation(Interjection, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Interjection, germanize)));
		toGerman.addTranslation(Pronoun, parameterManager, translate(
				new ConcreteTranslationTree<>(GermanSymbol.Pronoun, germanize)));
	}

	public static Grammar<Token, FormParameter> makeGrammar() {
		return grammar;
	}

	public static TranslationSet<Token, FormParameter, GermanToken, FormParameter> makeGermanTranslations() {
		return toGerman;
	}
}
