package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.param.property.PropertyFormParameterizer;
import com.zettelnet.earley.param.property.PropertyParameter;
import com.zettelnet.earley.param.property.PropertyParameterManager;
import com.zettelnet.earley.param.property.SpecificPropertyParameterExpression;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.DummyLemma;
import com.zettelnet.earley.test.latin.LemmaTerminal;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;

public class LatinPropertyExample {

	public static void main(String[] args) throws FileNotFoundException {

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");
		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");

		Terminal<Token> verb = new LemmaTerminal(LemmaType.Verb);
		Terminal<Token> noun = new LemmaTerminal(LemmaType.Noun);

		PropertyParameterManager<FormProperty> parameterManager = new PropertyParameterManager<>(Form.ALL_PROPERTIES);
		TokenParameterizer<Token, PropertyParameter<FormProperty>> parameterizer = new PropertyFormParameterizer(parameterManager);

		Grammar<Token, PropertyParameter<FormProperty>> grammar = new Grammar<>(sentence, parameterManager);
		grammar.setStartSymbolParameter(parameterManager.makeParameterFactory(Form.nounForm(Casus.Nominative, null, null)));

		ParameterExpression<Token, PropertyParameter<FormProperty>> copy = new CopyParameterExpression<>(grammar, parameterizer);

		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(nounPhrase, copy),
				new ParameterizedSymbol<>(verbPhrase, copy));

		grammar.addProduction(nounPhrase,
				new ParameterizedSymbol<>(noun, copy));

		grammar.addProduction(nounPhrase,
				new ParameterizedSymbol<>(noun, copy),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, parameterizer, parameterManager.makeParameter(Form.nounForm(Casus.Genitive, null, null)))));

		grammar.addProduction(verbPhrase,
				new ParameterizedSymbol<>(verb, copy),
				new ParameterizedSymbol<>(nounPhrase, new SpecificPropertyParameterExpression<>(parameterManager, parameterizer, Form.nounForm(Casus.Accusative, null, null))));

		GrammarParser<Token, PropertyParameter<FormProperty>> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());

		// lemmas

		Lemma serva = new DummyLemma(LemmaType.Noun);
		Lemma servus = new DummyLemma(LemmaType.Noun);
		Lemma canto = new DummyLemma(LemmaType.Verb);

		// tokens

		List<Token> tokens = new ArrayList<>();
		tokens.add(new Token("iumentum",
				new Determination(serva, Form.nounForm(Casus.Nominative, Numerus.Singular, Genus.Neuter)),
				new Determination(serva, Form.nounForm(Casus.Accusative, Numerus.Singular, Genus.Neuter)),
				new Determination(serva, Form.nounForm(Casus.Vocative, Numerus.Singular, Genus.Neuter))));
		tokens.add(new Token("servae",
				new Determination(servus, Form.nounForm(Casus.Genitive, Numerus.Singular, Genus.Feminine)),
				new Determination(servus, Form.nounForm(Casus.Dative, Numerus.Singular, Genus.Feminine)),
				new Determination(servus, Form.nounForm(Casus.Accusative, Numerus.Plural, Genus.Feminine)),
				new Determination(servus, Form.nounForm(Casus.Vocative, Numerus.Plural, Genus.Feminine))));
		tokens.add(new Token("plaustrum",
				new Determination(serva, Form.nounForm(Casus.Nominative, Numerus.Singular, Genus.Neuter)),
				new Determination(serva, Form.nounForm(Casus.Accusative, Numerus.Singular, Genus.Neuter))));
		tokens.add(new Token("trahit",
				new Determination(canto, Form.verbForm(Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active))));

		ParseResult<Token, PropertyParameter<FormProperty>> result = parser.parse(tokens);

		new ChartSetPrinter<Token, PropertyParameter<FormProperty>>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		System.out.println(result.getSyntaxTree());
	}
}
