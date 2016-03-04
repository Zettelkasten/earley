package com.zettelnet.earley.test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.PredicateTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public class MultipleVariantTest {

	public static void main(String[] args) {

		// 1. Initialize Symbols

		NonTerminal<String> start = new SimpleNonTerminal<>("Start");
		NonTerminal<String> left = new SimpleNonTerminal<>("Left");
		NonTerminal<String> middle = new SimpleNonTerminal<>("Middle");
		NonTerminal<String> right = new SimpleNonTerminal<>("Right");

		Terminal<String> a = new PredicateTerminal<>("a", (String str) -> {
			return true;
		});
		Terminal<String> b = new PredicateTerminal<>("b", (String str) -> {
			return true;
		});
		Terminal<String> c = new PredicateTerminal<>("c", (String str) -> {
			return true;
		});
		Terminal<String> d = new PredicateTerminal<>("d", (String str) -> {
			return true;
		});

		// 2. Initialize Grammar

		Grammar<String, DefaultParameter> grammar = new Grammar<>(start, new DefaultParameterManager());

		// 3. Add Productions

		grammar.addProduction(start, left, middle, right);
		grammar.addProduction(left, a);
		grammar.addProduction(middle, b);
		grammar.addProduction(middle, c);
		grammar.addProduction(right, d);

		// 4. Create Parser

		EarleyParser<String, DefaultParameter> parser = new EarleyParser<>(grammar);

		// 5. Initialize Tokens

		List<String> tokens = Arrays.asList("0", "1", "2");

		// 5. Parse

		EarleyParseResult<String, DefaultParameter> result = parser.parse(tokens);

		try {
			new ChartSetPrinter<String, DefaultParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(result.getBinarySyntaxTree());
		System.out.println(result.getSyntaxTree());
	}
}
