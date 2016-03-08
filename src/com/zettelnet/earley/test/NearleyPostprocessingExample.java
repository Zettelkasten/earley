package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.SimpleGrammar;
import com.zettelnet.earley.param.DefaultParameter;
import com.zettelnet.earley.param.DefaultParameterManager;
import com.zettelnet.earley.process.ProcessableProduction;
import com.zettelnet.earley.process.ProcessingManager;
import com.zettelnet.earley.symbol.MatchTerminal;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.PredicateTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

/**
 * c.f. <a href=
 * "https://github.com/Hardmath123/nearley/blob/master/examples/calculator/arithmetic.ne">
 * this</a>
 * 
 * @author Zettelkasten
 *
 */
public class NearleyPostprocessingExample {

	public static <T> Terminal<T> lit(T value) {
		return new MatchTerminal<T>(value);
	}

	public static void main(String[] args) throws FileNotFoundException {
		NonTerminal<Character> main = new SimpleNonTerminal<>("Main");
		NonTerminal<Character> parentheses = new SimpleNonTerminal<>("P");
		NonTerminal<Character> exponent = new SimpleNonTerminal<>("E");
		NonTerminal<Character> multiplication = new SimpleNonTerminal<>("MD");
		NonTerminal<Character> addition = new SimpleNonTerminal<>("AS");
		NonTerminal<Character> number = new SimpleNonTerminal<>("N");
		NonTerminal<Character> floatNumber = new SimpleNonTerminal<>("float");
		NonTerminal<Character> intNumber = new SimpleNonTerminal<>("int");
		NonTerminal<Character> intNumberNull = new SimpleNonTerminal<>("int*");
		NonTerminal<Character> whitespace = new SimpleNonTerminal<>("_");

		Terminal<Character> digit = new PredicateTerminal<>("#", (Character c) -> {
			return Character.isDigit(c);
		});

		SimpleGrammar<Character, DefaultParameter> grammar = new SimpleGrammar<>(main, new DefaultParameterManager());

		Collection<ProcessableProduction<Character, DefaultParameter, Double>> productions = new HashSet<>();

		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				main,
				whitespace, addition, whitespace),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1));
				}));

		// Parentheses
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				parentheses,
				lit('('), addition, whitespace, lit(')'), whitespace),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				parentheses,
				number),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// Exponents
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				exponent,
				parentheses, whitespace, lit('^'), whitespace, exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return Math.pow(manager.process(tree.getChildren().get(1)), manager.process(tree.getChildren().get(4)));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				exponent,
				parentheses),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// Multiplication and devision
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				multiplication,
				multiplication, whitespace, lit('*'), whitespace, exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) * manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				multiplication,
				multiplication, whitespace, lit('/'), whitespace, exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) / manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				multiplication,
				exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// Addition and subtraction
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				addition,
				addition, whitespace, lit('+'), whitespace, multiplication),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) + manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				addition,
				addition, whitespace, lit('-'), whitespace, multiplication),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) - manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				addition,
				multiplication),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// A number or a function of a number
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				number,
				floatNumber),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// TODO
		grammar.addProduction(number,
				lit('s'), lit('i'), lit('n'), whitespace, parentheses);
		grammar.addProduction(number,
				lit('c'), lit('o'), lit('s'), whitespace, parentheses);
		grammar.addProduction(number,
				lit('t'), lit('a'), lit('n'), whitespace, parentheses);

		grammar.addProduction(number,
				lit('a'), lit('s'), lit('i'), lit('n'), whitespace, parentheses);
		grammar.addProduction(number,
				lit('a'), lit('c'), lit('o'), lit('s'), whitespace, parentheses);
		grammar.addProduction(number,
				lit('a'), lit('t'), lit('a'), lit('n'), whitespace, parentheses);

		grammar.addProduction(number,
				lit('p'), lit('i'));
		grammar.addProduction(number,
				lit('e'));
		grammar.addProduction(number,
				lit('s'), lit('q'), lit('r'), lit('t'), whitespace, parentheses);
		grammar.addProduction(number,
				lit('l'), lit('n'), whitespace, parentheses);

		// Number with a decimal point in it
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				floatNumber,
				intNumber, lit('.'), intNumber),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));
		grammar.addProduction(floatNumber,
				intNumber, lit('.'), intNumber);
		grammar.addProduction(floatNumber,
				intNumber);

		// Number without a decimal point
		grammar.addProduction(intNumber,
				digit, intNumberNull);
		grammar.addProduction(intNumberNull);
		grammar.addProduction(intNumberNull,
				digit, intNumberNull);

		// None, one or more whitespaces
		grammar.addProduction(whitespace,
				lit(' '), whitespace);
		grammar.addProduction(whitespace);

		GrammarParser<Character, DefaultParameter> parser = new EarleyParser<>(grammar);

		String input = "sin(45)*2/pi^2";
		List<Character> tokens = input.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

		System.out.println("Parsing " + input);

		ParseResult<Character, DefaultParameter> result = parser.parse(tokens);

		new ChartSetPrinter<Character, DefaultParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));

		System.out.println(result.getSyntaxTree());
	}
}
