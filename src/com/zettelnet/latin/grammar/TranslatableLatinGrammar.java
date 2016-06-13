package com.zettelnet.latin.grammar;

import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.process.ProcessableGrammar;
import com.zettelnet.earley.process.ProcessingManager;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.LatinRegistry;
import com.zettelnet.earley.test.LatinTranslationTest;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaTerminal;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.param.FormParameterManager;
import com.zettelnet.latin.param.FormParameterizer;
import com.zettelnet.latin.token.Token;

public class TranslatableLatinGrammar {

	public static ProcessableGrammar<Token, FormParameter, String> makeGrammar() {
		// debug for initialization
		new LatinTranslationTest();

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");
		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");
		NonTerminal<Token> verbForm = new SimpleNonTerminal<>("VF");

		Terminal<Token> noun = new LemmaTerminal(LemmaType.Noun);
		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb);
		Terminal<Token> infinitive = new LemmaTerminal(LemmaType.Infinitive);

		ParameterManager<Token, FormParameter> parameterManager = new FormParameterManager<>(LatinSymbol.DEFAULT_PROPERTY_TYPES);
		ProcessableGrammar<Token, FormParameter, String> grammar = new ProcessableGrammar<>(sentence, parameterManager);
		grammar.setStartSymbolParameter(new SingletonParameterFactory<>(new FormParameter(Casus.Nominative, Finiteness.Finite)));

		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();
		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, parameterizer);

		grammar.setProcessor(noun, (ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
			Lemma lemma = tree.getParameter().getCause().getLemma();
			Form form = tree.getParameter().toForm();
			return LatinRegistry.getTranslation(lemma) + (form.getNumerus() == Numerus.Plural ? "s" : "");
		});
		grammar.setProcessor(verb, (ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
			Lemma lemma = tree.getParameter().getCause().getLemma();
			Form form = tree.getParameter().toForm();
			return LatinRegistry.getTranslation(lemma) + (form.getNumerus() == Numerus.Plural ? "" : "s");
		});
		grammar.setProcessor(infinitive, (ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
			Lemma lemma = tree.getParameter().getCause().getLemma();
			Form form = tree.getParameter().toForm();
			return LatinRegistry.getTranslation(lemma) + (form.getNumerus() == Numerus.Plural ? "" : "s");
		});

		grammar.addProduction(new Production<>(grammar,
				sentence,
				new ParameterizedSymbol<>(nounPhrase, copy),
				new ParameterizedSymbol<>(verbPhrase, copy)),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0))
							+ " " + manager.process(tree.getChildren().get(1));
				});
		grammar.addProduction(new Production<>(grammar,
				nounPhrase,
				new ParameterizedSymbol<>(noun, copy)),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					Form form = tree.getParameter().toForm();
					return (form.getNumerus() == Numerus.Singular ? "a " : "") + manager.process(tree.getChildren().get(0));
				});
		grammar.addProduction(new Production<>(grammar,
				nounPhrase,
				new ParameterizedSymbol<>(noun, copy),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Genitive)))),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					Form form = tree.getParameter().toForm();
					return (form.getNumerus() == Numerus.Singular ? "a " : "")
							+ manager.process(tree.getChildren().get(1)) + "'s "
							+ manager.process(tree.getChildren().get(0));
				});
		grammar.addProduction(new Production<>(verbForm,
				new SingletonParameterFactory<>(new FormParameter(Finiteness.Finite)),
				new ParameterizedSymbol<>(verb, copy)),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				});
		grammar.addProduction(new Production<>(verbForm,
				new SingletonParameterFactory<>(new FormParameter(Finiteness.Infinitive)),
				new ParameterizedSymbol<>(infinitive, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Nominative)))),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				});
		grammar.addProduction(new Production<>(verbPhrase,
				new SingletonParameterFactory<>(new FormParameter(Valency.Single)),
				new ParameterizedSymbol<>(verbForm, copy)),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				});
		grammar.addProduction(new Production<>(verbPhrase,
				new SingletonParameterFactory<>(new FormParameter(Valency.Accusative)),
				new ParameterizedSymbol<>(verbForm, copy),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative)))),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0)) + " " + manager.process(tree.getChildren().get(1));
				});

		grammar.addProduction(new Production<>(nounPhrase,
				new SingletonParameterFactory<>(new FormParameter(Valency.Accusative)),
				new ParameterizedSymbol<>(sentence, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative, Finiteness.Infinitive)))),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return "that " + manager.process(tree.getChildren().get(0));
				});

		return grammar;
	}
}
