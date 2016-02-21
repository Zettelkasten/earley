package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.AnyTokenTerminal;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public class RecursiveExample {

	public static void main(String[] args) throws FileNotFoundException {

		NonTerminal<String> delta = new SimpleNonTerminal<>("&Delta;");
		NonTerminal<String> gamma = new SimpleNonTerminal<>("&Gamma;");

		Terminal<String> term = new AnyTokenTerminal<>("a");

		ParameterManager<DefaultParameter> parameterManager = new DefaultParameterManager();

		Grammar<String, DefaultParameter> grammar = new Grammar<>(gamma, parameterManager);

		grammar.addProduction(
				gamma,
				delta);
		// Attr(pi) -> epsilon
		grammar.addProduction(
				delta);
		// Attr(pi) -> Attr(pi) Attr(pi)
		grammar.addProduction(
				delta, gamma, gamma);
		// Attr(pi) -> NP(Gen)
		grammar.addProduction(
				delta,
				term);

		GrammarParser<String, DefaultParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());

		List<String> tokens = new ArrayList<>();
		tokens.add("a");

		ParseResult<String, DefaultParameter> result = parser.parse(tokens);

		new ChartSetPrinter<String, DefaultParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		System.out.println(result.getSyntaxTree());
	}
}
