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

		SimpleGrammar<Character, DefaultParameter> grammar = new SimpleGrammar<>(main, new DefaultParameterManager<>());

		Collection<ProcessableProduction<Character, DefaultParameter, Double>> productions = new HashSet<>();

		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				main, 1,
				whitespace, addition, whitespace),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1));
				}));

		// Parentheses
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				parentheses, 1,
				lit('('), addition, whitespace, lit(')'), whitespace),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				parentheses, 1,
				number),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// Exponents
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				exponent, 1,
				parentheses, whitespace, lit('^'), whitespace, exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return Math.pow(manager.process(tree.getChildren().get(1)), manager.process(tree.getChildren().get(4)));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				exponent, 1,
				parentheses),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// Multiplication and devision
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				multiplication, 1,
				multiplication, whitespace, lit('*'), whitespace, exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) * manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				multiplication, 1,
				multiplication, whitespace, lit('/'), whitespace, exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) / manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				multiplication, 1,
				exponent),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// Addition and subtraction
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				addition, 1,
				addition, whitespace, lit('+'), whitespace, multiplication),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) + manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				addition, 1,
				addition, whitespace, lit('-'), whitespace, multiplication),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(1)) - manager.process(tree.getChildren().get(4));
				}));
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				addition, 1,
				multiplication),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// A number or a function of a number
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				number, 1,
				floatNumber),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));

		// TODO
		grammar.addProduction(number, 1,
				lit('s'), lit('i'), lit('n'), whitespace, parentheses);
		grammar.addProduction(number, 1,
				lit('c'), lit('o'), lit('s'), whitespace, parentheses);
		grammar.addProduction(number, 1,
				lit('t'), lit('a'), lit('n'), whitespace, parentheses);

		grammar.addProduction(number, 1,
				lit('a'), lit('s'), lit('i'), lit('n'), whitespace, parentheses);
		grammar.addProduction(number, 1,
				lit('a'), lit('c'), lit('o'), lit('s'), whitespace, parentheses);
		grammar.addProduction(number, 1,
				lit('a'), lit('t'), lit('a'), lit('n'), whitespace, parentheses);

		grammar.addProduction(number, 1,
				lit('p'), lit('i'));
		grammar.addProduction(number, 1,
				lit('e'));
		grammar.addProduction(number, 1,
				lit('s'), lit('q'), lit('r'), lit('t'), whitespace, parentheses);
		grammar.addProduction(number, 1,
				lit('l'), lit('n'), whitespace, parentheses);

		// Number with a decimal point in it
		productions.add(new ProcessableProduction<Character, DefaultParameter, Double>(new Production<>(grammar,
				floatNumber, 1,
				intNumber, lit('.'), intNumber),
				(ProcessingManager<Character, DefaultParameter, Double> manager, SyntaxTreeVariant<Character, DefaultParameter> tree) -> {
					return manager.process(tree.getChildren().get(0));
				}));
		grammar.addProduction(floatNumber, 1,
				intNumber, lit('.'), intNumber);
		grammar.addProduction(floatNumber, 1,
				intNumber);

		// Number without a decimal point
		grammar.addProduction(intNumber, 1,
				digit, intNumberNull);
		grammar.addProduction(intNumberNull, 1);
		grammar.addProduction(intNumberNull, 1,
				digit, intNumberNull);

		// None, one or more whitespaces
		grammar.addProduction(whitespace, 1,
				lit(' '), whitespace);
		grammar.addProduction(whitespace, 1);

		GrammarParser<Character, DefaultParameter> parser = new EarleyParser<>(grammar);

		String input = "sin(45)*2/pi^2";
		List<Character> tokens = input.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

		System.out.println("Parsing " + input);

		ParseResult<Character, DefaultParameter> result = parser.parse(tokens);

		new ChartSetPrinter<Character, DefaultParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));

		System.out.println(result.getSyntaxTree());
	}
}
