package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.symbol.AnyTokenTerminal;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public class SameLastCharExample {

	public static class LastCharParameter implements Parameter {

		private final char lastChar;

		public LastCharParameter() {
			this.lastChar = 0;
		}

		public LastCharParameter(char lastChar) {
			this.lastChar = lastChar;
		}

		public char getLastChar() {
			return lastChar;
		}

		@Override
		public String toString() {
			return (lastChar == 0 ? "null" : String.valueOf(lastChar));
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + lastChar;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			LastCharParameter other = (LastCharParameter) obj;
			if (lastChar != other.lastChar) {
				return false;
			}
			return true;
		}

	}

	public static class LastCharParameterManager implements ParameterManager<LastCharParameter> {

		private final LastCharParameter origin = new LastCharParameter();

		@Override
		public LastCharParameter makeParameter() {
			return origin;
		}

		@Override
		public LastCharParameter copyParameter(LastCharParameter origin) {
			return origin;
		}

		@Override
		public LastCharParameter copyParameter(LastCharParameter origin, LastCharParameter with) {
			if (origin.lastChar == 0) {
				return with;
			} else {
				return origin;
			}
		}

		@Override
		public boolean isCompatible(LastCharParameter parent, LastCharParameter other) {
			if (parent.getLastChar() == 0) {
				return true;
			} else {
				return parent.getLastChar() == other.getLastChar();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		// this example grammar only allows strings with the same last character
		// oh, and btw it tests epsilon symbols

		NonTerminal<String> sentence = new SimpleNonTerminal<>("S");
		Terminal<String> word = new AnyTokenTerminal<>("w");

		Grammar<String, LastCharParameter> grammar = new Grammar<>(sentence, new LastCharParameterManager());

		ParameterExpression<String, LastCharParameter> copy = new CopyParameterExpression<>(grammar, (token, terminal) -> {
			return Arrays.asList(new LastCharParameter(token.charAt(token.length() - 1)));
		});
		// ParameterExpression<String, LastCharParameter> any = new
		// AnyParameterExpression<>();

		grammar.addProduction(sentence, new SingletonParameterFactory<>(new LastCharParameter()),
				new ParameterizedSymbol<>(sentence, copy),
				new ParameterizedSymbol<>(word, copy));
		grammar.addProduction(sentence);

		EarleyParser<String, LastCharParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());

		List<String> tokens = Arrays.asList("Yo Ho" /*
													 * Ko Mo Lo Sho Kawho Mio
													 * Riko Nono Mio Rho Kodo"
													 */.split(" "));
		ParseResult<String, LastCharParameter> result = parser.parse(tokens);

		new ChartSetPrinter<String, LastCharParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		System.out.println(result.getTreeForest());
	}
}
