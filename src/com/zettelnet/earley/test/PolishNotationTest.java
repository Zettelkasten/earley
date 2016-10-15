package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.param.DefaultTokenParameterizer;
import com.zettelnet.earley.print.ChartSetPrinter;
import com.zettelnet.earley.symbol.MatchTerminal;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.PredicateTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTrees;
import com.zettelnet.earley.tree.TreeViews;

/**
 * The goal is to translate a parsed infix string (e.g. 1+2*3) and to translate
 * it into an postfix string (i.e. 123*+ or 12+3* if you do not consider order
 * of operators).
 * 
 * @author Zettelkasten
 *
 */
public class PolishNotationTest {

	public static void main(String[] args) throws FileNotFoundException {
		// 1. Define Grammar representing Infix notation

		Terminal<Character> plus = new MatchTerminal<>('+');
		Terminal<Character> times = new MatchTerminal<>('*');
		Terminal<Character> digit = new PredicateTerminal<>("n", Character::isDigit);

		NonTerminal<Character> infixExpression = new SimpleNonTerminal<>("E");

		SimpleGrammar<Character, DefaultParameter> infixGrammar = new SimpleGrammar<>(infixExpression, new DefaultParameterManager<>(), new DefaultTokenParameterizer<>());
		Production<Character, DefaultParameter> literal = infixGrammar.addProduction(infixExpression, 0.4, digit);
		Production<Character, DefaultParameter> sum = infixGrammar.addProduction(infixExpression, 0.3, infixExpression, plus, infixExpression);
		Production<Character, DefaultParameter> product = infixGrammar.addProduction(infixExpression, 0.3, infixExpression, times, infixExpression);

		// 2. Parse input string
		Tokenizer<Character> tokenizer = (String input) -> {
			List<Character> tokens = new ArrayList<>(input.length());
			for (int i = 0; i < input.length(); i++) {
				tokens.add(input.charAt(i));
			}
			return tokens;
		};
		GrammarParser<Character, DefaultParameter> parser = new EarleyParser<>(infixGrammar);
		List<Character> tokens = tokenizer.tokenize("1+2*3");
		ParseResult<Character, DefaultParameter> result = parser.parse(tokens);
		new ChartSetPrinter<>(result.getCharts(), tokens).print(new PrintStream("parse.html"));
		SyntaxTree<Character, DefaultParameter> tree = result.getSyntaxTree();

		System.out.println(SyntaxTrees.getTreeView(tree, TreeViews.bestProbability()));
	}
}
