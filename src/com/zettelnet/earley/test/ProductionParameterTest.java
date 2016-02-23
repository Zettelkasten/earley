package com.zettelnet.earley.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.AnyTokenTerminal;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public class ProductionParameterTest {

	public static class IntParameterManager implements ParameterManager<IntParameter> {

		@Override
		public IntParameter makeParameter() {
			return new IntParameter(-1);
		}

		@Override
		public IntParameter copyParameter(IntParameter source) {
			return source;
		}

		@Override
		public IntParameter copyParameter(IntParameter source, IntParameter with) {
			return source.getValue() == -1 ? with : source;
		}

		@Override
		public boolean isCompatible(IntParameter parent, IntParameter child) {
			if (child.getValue() == -1) {
				return true;
			} else {
				return parent.getValue() == child.getValue();
			}
		}

	}

	public static class IntParameter implements Parameter {
		private final int val;

		public IntParameter(final int value) {
			this.val = value;
		}

		public int getValue() {
			return val;
		}

		@Override
		public String toString() {
			return val == -1 ? "?" : String.valueOf(val);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + val;
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
			IntParameter other = (IntParameter) obj;
			if (val != other.val) {
				return false;
			}
			return true;
		}

	}

	public static void main(String[] args) {

		NonTerminal<String> a = new SimpleNonTerminal<>("A");
		NonTerminal<String> b = new SimpleNonTerminal<>("B");
		Terminal<String> t = new AnyTokenTerminal<>("t");

		ParameterManager<IntParameter> parameterManager = new IntParameterManager();
		Grammar<String, IntParameter> grammar = new Grammar<>(a, new SingletonParameterFactory<>(new IntParameter(2)), parameterManager);
		TokenParameterizer<String, IntParameter> parameterizer = (String token, Terminal<String> terminal) -> {
			return Arrays.asList(new IntParameter(Integer.valueOf(token)));
		};

		ParameterExpression<String, IntParameter> copy = new CopyParameterExpression<>(parameterManager, parameterizer);
		grammar.addProduction(a, new ParameterizedSymbol<>(b, copy));
		grammar.addProduction(b, new SingletonParameterFactory<>(new IntParameter(1)), new ParameterizedSymbol<>(t, copy));

		EarleyParser<String, IntParameter> parser = new EarleyParser<>(grammar, new LinearInputPositionInitializer<>());
		EarleyParseResult<String, IntParameter> result = parser.parse(Arrays.asList("1"));

		try {
			result.printChartSetsHtml(new PrintStream(new File("E:\\temp.html")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(result.isComplete());
		System.out.println(result.getSyntaxTree());
	}
}
