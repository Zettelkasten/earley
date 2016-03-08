package com.zettelnet.earley.test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
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
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.FormParameterManager;
import com.zettelnet.earley.test.latin.FormParameterizer;
import com.zettelnet.earley.test.latin.LemmaTerminal;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;

public class LatinTranslationTest {

	public static void main(String[] args) {
		new LatinTranslationTest();

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");
		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");

		Terminal<Token> noun = new LemmaTerminal(LemmaType.Noun);
		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb);

		ParameterManager<FormParameter> parameterManager = new FormParameterManager();
		ProcessableGrammar<Token, FormParameter, String> grammar = new ProcessableGrammar<>(sentence, parameterManager);
		grammar.setStartSymbolParameter(new SingletonParameterFactory<>(new FormParameter(Casus.Nominative, Finiteness.Finite)));

		TokenParameterizer<Token, FormParameter> parameterizer = new FormParameterizer();
		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, parameterizer);

		grammar.setProcessor(noun, (ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
			Lemma lemma = tree.getParameter().getCause().getLemma();
			Form form = tree.getParameter().toForm();
			return LatinRegistry.TRANSLATIONS.get(lemma) + (form.getNumerus() == Numerus.Plural ? "s" : "");
		});
		grammar.setProcessor(verb, (ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
			Lemma lemma = tree.getParameter().getCause().getLemma();
			Form form = tree.getParameter().toForm();
			return LatinRegistry.TRANSLATIONS.get(lemma) + (form.getNumerus() == Numerus.Plural ? "" : "s");
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
		grammar.addProduction(new Production<>(verbPhrase,
				new SingletonParameterFactory<>(new FormParameter(Valency.Single)),
				new ParameterizedSymbol<>(verb, copy)),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				});
		grammar.addProduction(new Production<>(verbPhrase,
				new SingletonParameterFactory<>(new FormParameter(Valency.Accusative)),
				new ParameterizedSymbol<>(verb, copy),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, new FormParameter(Casus.Accusative)))),
				(ProcessingManager<Token, FormParameter, String> manager, SyntaxTreeVariant<Token, FormParameter> tree) -> {
					return manager.process(tree.getChildren().get(0)) + " " + manager.process(tree.getChildren().get(1));
				});

		String raw = "carmina dicent servum";

		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(LatinRegistry.INSTANCE);
		GrammarParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());
		List<Token> tokens = tokenizer.tokenize(raw);
		ParseResult<Token, FormParameter> result = parser.parse(tokens);

		try {
			new ChartSetPrinter<>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		} catch (IOException e) {
		}

		System.out.println(result.getSyntaxTree());
		System.out.println(grammar.process(result.getSyntaxTree()));

	}
}
