package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.SimpleGrammar;
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
		Terminal<String> term = new AnyTokenTerminal<>("a");

		ParameterManager<String, DefaultParameter> parameterManager = new DefaultParameterManager<>();

		SimpleGrammar<String, DefaultParameter> grammar = new SimpleGrammar<>(delta, parameterManager);

		grammar.addProduction(
				delta, 1);
		grammar.addProduction(
				delta, 1, delta, term);

		GrammarParser<String, DefaultParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());

		List<String> tokens = new ArrayList<>();
		tokens.add("a");

		ParseResult<String, DefaultParameter> result = parser.parse(tokens);

		new ChartSetPrinter<String, DefaultParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		System.out.println(result.getSyntaxTree());
	}
}
