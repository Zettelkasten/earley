package com.zettelnet.earley;

import java.util.ArrayList;
import java.util.Arrays;
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
	private final ParameterFactory<P> keyParameter;

	private final List<ParameterizedSymbol<T, P>> values;
	private final List<Symbol<T>> symbolValues; // values, but without parameter
												// expressions

	private static <T, P extends Parameter> List<ParameterizedSymbol<T, P>> makeProductionSymbols(ParameterManager<P> manager, Symbol<T>[] symbols) {
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

	@SafeVarargs
	public Production(final Grammar<T, P> grammar, final NonTerminal<T> left, final Symbol<T>... right) {
		this(left, grammar.getParameterManager(), makeProductionSymbols(grammar.getParameterManager(), right));
	}

	@SafeVarargs
	public Production(final NonTerminal<T> left, final ParameterFactory<P> keyParameter, final ParameterizedSymbol<T, P>... right) {
		this.key = left;
		this.keyParameter = keyParameter;
		this.values = Arrays.asList(right);
		this.symbolValues = makeUnparameterizedSymbols(values);
	}

	private Production(final NonTerminal<T> left, final ParameterFactory<P> keyParameter, final List<ParameterizedSymbol<T, P>> right) {
		this.key = left;
		this.keyParameter = keyParameter;
		this.values = right;
		this.symbolValues = makeUnparameterizedSymbols(values);
	}

	public NonTerminal<T> key() {
		return key;
	}

	public P keyParameter() {
		return keyParameter.makeParameter();
	}

	public List<Symbol<T>> values() {
		return symbolValues;
	}

	public int size() {
		return values.size();
	}

	public Symbol<T> get(int position) {
		return values.get(position).getSymbol();
	}

	public ParameterExpression<T, P> getParameterExpression(int position) {
		return values.get(position).getParameterExpression();
	}
}
