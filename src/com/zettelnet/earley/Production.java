package com.zettelnet.earley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.param.AnyParameterExpression;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;

public class Production<T, P extends Parameter> {

	private final NonTerminal<T> key;
	private final ParameterFactory<T, P> keyParameter;

	private final List<ParameterizedSymbol<T, P>> values;
	private final List<Symbol<T>> symbolValues; // values, but without parameter
												// expressions

	private static <T, P extends Parameter> List<ParameterizedSymbol<T, P>> makeProductionSymbols(ParameterManager<T, P> manager, Symbol<T>[] symbols) {
		List<ParameterizedSymbol<T, P>> list = new ArrayList<>(symbols.length);
		for (Symbol<T> symbol : symbols) {
			list.add(new ParameterizedSymbol<T, P>(symbol, new AnyParameterExpression<T, P>(manager)));
		}
		return list;
	}

	private static <T, P extends Parameter> List<Symbol<T>> makeUnparameterizedSymbols(List<ParameterizedSymbol<T, P>> symbols) {
		List<Symbol<T>> list = new ArrayList<>(symbols.size());
		for (ParameterizedSymbol<T, P> parameterizedSymbol : symbols) {
			list.add(parameterizedSymbol.getSymbol());
		}
		return list;
	}

	public Production(final Grammar<T, P> grammar, final NonTerminal<T> left) {
		this(left, grammar.getParameterManager(), Collections.emptyList());
	}

	@SafeVarargs
	public Production(final Grammar<T, P> grammar, final NonTerminal<T> left, final Symbol<T>... right) {
		this(left, grammar.getParameterManager(), makeProductionSymbols(grammar.getParameterManager(), right));
	}

	@SafeVarargs
	public Production(final Grammar<T, P> grammar, final NonTerminal<T> left, final ParameterizedSymbol<T, P>... right) {
		this(left, grammar.getParameterManager(), right);
	}

	@SafeVarargs
	public Production(final NonTerminal<T> left, final ParameterFactory<T, P> keyParameter, final ParameterizedSymbol<T, P>... right) {
		this(left, keyParameter, Arrays.asList(right));
	}

	private Production(final NonTerminal<T> left, final ParameterFactory<T, P> keyParameter, final List<ParameterizedSymbol<T, P>> right) {
		this.key = left;
		this.keyParameter = keyParameter;
		this.values = right;
		this.symbolValues = makeUnparameterizedSymbols(values);
	}

	public NonTerminal<T> key() {
		return key;
	}

	public P keyParameter() {
		return keyParameter.makeParameter(key);
	}

	public List<Symbol<T>> values() {
		return symbolValues;
	}

	public int size() {
		return values.size();
	}

	public boolean isEpsilon() {
		return values.isEmpty();
	}

	public Symbol<T> get(int position) {
		return values.get(position).getSymbol();
	}

	public ParameterExpression<T, P> getParameterExpression(int position) {
		return values.get(position).getParameterExpression();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(key());
		str.append("(pi : ");
		str.append(keyParameter());
		str.append(")");
		str.append(" ->");

		if (values.size() == 0) {
			str.append(" [empty]");
		} else {
			for (int i = 0; i < values.size(); i++) {
				str.append(" ");
				str.append(get(i));
				str.append("(");
				str.append(getParameterExpression(i));
				str.append(")");
			}
		}

		return str.toString();
	}
}
