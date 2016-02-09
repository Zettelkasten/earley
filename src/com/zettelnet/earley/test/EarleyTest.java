package com.zettelnet.earley.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.PredicateTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public class EarleyTest {

	public static void main(String[] args) {

		Terminal<String> number = new PredicateTerminal<>("num", (token) -> {
			try {
				double d = Double.parseDouble(token);
				return d >= 0;
			} catch (NumberFormatException e) {
				return false;
			}
		});
		Terminal<String> operator = new PredicateTerminal<>("op", (token) -> {
			return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
		});
		Terminal<String> equals = new PredicateTerminal<String>("eq", (token) -> {
			return token.equals("=");
		});
		Terminal<String> bracketOpen = new PredicateTerminal<String>("bL", (token) -> {
			return token.equals("(");
		});
		Terminal<String> bracketClose = new PredicateTerminal<String>("bR", (token) -> {
			return token.equals(")");
		});
		Terminal<String> negation = new PredicateTerminal<String>("neg", (token) -> {
			return token.equals("-");
		});
		Terminal<String> faktorial = new PredicateTerminal<String>("fak", (token) -> {
			return token.equals("!");
		});
		Terminal<String> function = new PredicateTerminal<>("func", (token) -> {
			return token.equals("sin") || token.equals("cos") || token.equals("tan") || token.equals("cot")
					|| token.equals("asin") || token.equals("acos") || token.equals("atan") || token.equals("acot")
					|| token.equals("exp") || token.equals("sqrt");
		});
		Terminal<String> constant = new PredicateTerminal<>("const", (token) -> {
			return token.equals("pi") || token.equals("e") || token.equals("i");
		});

		NonTerminal<String> start = new SimpleNonTerminal<>("S");
		NonTerminal<String> term = new SimpleNonTerminal<>("T");

		Grammar<String, DefaultParameter> grammar = new Grammar<>(start, new DefaultParameterManager());

		grammar.addProduction(new Production<>(grammar, start, term));
		grammar.addProduction(new Production<>(grammar, start, term, equals, term));
		grammar.addProduction(new Production<>(grammar, term, term, operator, term));
		grammar.addProduction(new Production<>(grammar, term, term, faktorial));
		grammar.addProduction(new Production<>(grammar, term, number));
		grammar.addProduction(new Production<>(grammar, term, constant));
		grammar.addProduction(new Production<>(grammar, term, negation, term));
		grammar.addProduction(new Production<>(grammar, term, bracketOpen, term, bracketClose));
		grammar.addProduction(new Production<>(grammar, term, function, term));
		EarleyParser<String, DefaultParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());
		EarleyParseResult<String, DefaultParameter> result = parser.parse(Arrays.asList("sqrt 23 + ( 7 / 2 * - 23 * cos pi / 2 ) / 3 ! - sin 1 / 2 = 23".split(" ")));

		System.out.println(result.isComplete());
		System.out.println(result.getSyntaxTree());
		try {
			result.printChartSetsHtml(new PrintStream(new File("E:\\temp.html")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
