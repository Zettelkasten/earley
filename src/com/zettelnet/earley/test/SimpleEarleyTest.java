package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.NonTerminal;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.ParseTree;
import com.zettelnet.earley.PredicateTerminal;
import com.zettelnet.earley.SimpleNonTerminal;
import com.zettelnet.earley.Terminal;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;

public class SimpleEarleyTest {

	public static void main(String[] args) throws FileNotFoundException {
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

		NonTerminal<String> term = new SimpleNonTerminal<>("T");
		NonTerminal<String> sum = new SimpleNonTerminal<>("S");

		Grammar<String, DefaultParameter> grammar = new Grammar<>(term, new DefaultParameterManager());

		grammar.addProduction(term, sum);
		grammar.addProduction(sum, term, operator, term);
		grammar.addProduction(term, number);

		EarleyParser<String, DefaultParameter> parser = new EarleyParser<>(grammar);

		List<String> tokens = Arrays.asList("1 + 2 + 3".split(" "));

		ParseResult<String, DefaultParameter> result = parser.parse(tokens);

		new ChartSetPrinter<String, DefaultParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		for (ParseTree<String> tree : result.getTreeForest()) {
			System.out.println(tree);
		}
	}
}
