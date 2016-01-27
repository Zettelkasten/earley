package com.zettelnet.earley.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.NonTerminal;
import com.zettelnet.earley.PredicateTerminal;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.SimpleNonTerminal;
import com.zettelnet.earley.Terminal;

public class SimpleEarleyTest {

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

		NonTerminal term = new SimpleNonTerminal("T");
		NonTerminal sum = new SimpleNonTerminal("S");

		Set<Production> productions = new HashSet<>();
		productions.add(new Production(term, sum));
		productions.add(new Production(sum, term, operator, term));
		productions.add(new Production(term, number));

		EarleyParser<String> parser = new EarleyParser<>(new Grammar(productions, term));
		EarleyParseResult<String> result = parser.parse(Arrays.asList("1 + 2 + 3".split(" ")));

		System.out.println(result.isComplete());
		result.printChartSets(System.out);
		try {
			result.printChartSetsHtml(new PrintStream(new File("E:\\temp.html")));
			System.out.println(result.getTreeForest());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
