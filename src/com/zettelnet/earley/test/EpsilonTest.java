package com.zettelnet.earley.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.symbol.AnyTokenTerminal;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public class EpsilonTest {

	public static void main(String[] args) {

		NonTerminal<String> a = new SimpleNonTerminal<>("A");
		NonTerminal<String> b = new SimpleNonTerminal<>("B");
		Terminal<String> t = new AnyTokenTerminal<>("t");

		SimpleGrammar<String, DefaultParameter> grammar = new SimpleGrammar<>(a, new DefaultParameterManager());

		grammar.addProduction(new Production<>(grammar, a, b, b, b));
		grammar.addProduction(new Production<>(grammar, b));
		grammar.addProduction(new Production<>(grammar, b, t));

		EarleyParser<String, DefaultParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());
		EarleyParseResult<String, DefaultParameter> result = parser.parse(Arrays.asList("lol", "rofl", "xd"));

		try {
			result.printChartSetsHtml(new PrintStream(new File("E:\\temp.html")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(result.isComplete());
		System.out.println(result.getBinarySyntaxTree());
		System.out.println(result.getSyntaxTree());
	}
}
