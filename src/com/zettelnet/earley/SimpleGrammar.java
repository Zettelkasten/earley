package com.zettelnet.earley;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public class SimpleGrammar<T, P extends Parameter> implements Grammar<T, P> {

	private final Map<NonTerminal<T>, Set<Production<T, P>>> nonTerminals;
	private final Set<Terminal<T>> terminals;

	private final Set<Production<T, P>> productions;

	private NonTerminal<T> startSymbol;
	private ParameterFactory<T, P> startSymbolParameter;

	private final ParameterManager<T, P> parameterManager;

	public SimpleGrammar(final NonTerminal<T> startSymbol, final ParameterManager<T, P> parameterManager) {
		this(startSymbol, parameterManager, parameterManager);
	}

	public SimpleGrammar(final NonTerminal<T> startSymbol, final ParameterFactory<T, P> startSymbolParameter, final ParameterManager<T, P> parameterManager) {
		this(startSymbol, startSymbolParameter, parameterManager, new HashSet<>());
	}

	public SimpleGrammar(final NonTerminal<T> startSymbol, final ParameterFactory<T, P> startSymbolParameter, final ParameterManager<T, P> parameterManager, final Set<Production<T, P>> productions) {
		this.startSymbol = startSymbol;
		this.startSymbolParameter = startSymbolParameter;

		this.parameterManager = parameterManager;

		this.productions = new HashSet<>();
		this.nonTerminals = new HashMap<>();
		this.terminals = new HashSet<>();

		for (Production<T, P> production : productions) {
			addProduction(production);
		}
	}

	public void addProduction(Production<T, P> production) {
		this.productions.add(production);
		
		NonTerminal<T> key = production.key();
		if (!nonTerminals.containsKey(key)) {
			nonTerminals.put(key, new HashSet<>());
		}
		nonTerminals.get(key).add(production);

		for (Symbol<T> symbol : production.values()) {
			if (symbol instanceof Terminal) {
				terminals.add((Terminal<T>) symbol);
			}
		}
	}

	public final Production<T, P> addProduction(NonTerminal<T> left) {
		Production<T, P> production = new Production<>(this, left);
		addProduction(production);
		return production;
	}

	@SafeVarargs
	public final Production<T, P> addProduction(NonTerminal<T> left, Symbol<T>... right) {
		Production<T, P> production = new Production<>(this, left, right);
		addProduction(production);
		return production;
	}

	@SafeVarargs
	public final Production<T, P> addProduction(NonTerminal<T> left, ParameterizedSymbol<T, P>... right) {
		return addProduction(left, parameterManager, right);
	}

	@SafeVarargs
	public final Production<T, P> addProduction(NonTerminal<T> left, ParameterFactory<T, P> keyParameter, ParameterizedSymbol<T, P>... right) {
		Production<T, P> production = new Production<>(left, keyParameter, right);
		addProduction(production);
		return production;
	}

	@Override
	public Set<NonTerminal<T>> getNonTerminals() {
		return nonTerminals.keySet();
	}

	@Override
	public Set<Terminal<T>> getTerminals() {
		return terminals;
	}

	@Override
	public Set<Production<T, P>> getProductions() {
		return productions;
	}

	@Override
	public Set<Production<T, P>> getProductions(NonTerminal<T> key) {
		Set<Production<T, P>> set = nonTerminals.get(key);
		if (set == null) {
			return Collections.emptySet();
		} else {
			return set;
		}
	}

	@Override
	public NonTerminal<T> getStartSymbol() {
		return startSymbol;
	}

	public void setStartSymbol(NonTerminal<T> startSymbol) {
		this.startSymbol = startSymbol;
	}

	@Override
	public ParameterFactory<T, P> getStartSymbolParameter() {
		return startSymbolParameter;
	}

	public void setStartSymbolParameter(ParameterFactory<T, P> startSymbolParameter) {
		this.startSymbolParameter = startSymbolParameter;
	}

	@Override
	public ParameterManager<T, P> getParameterManager() {
		return parameterManager;
	}

	@Override
	public ParameterExpression<T, P> getStartSymbolParameterExpression() {
		return new CopyParameterExpression<T, P>(this, null);
	}
}
