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

public class Grammar<T, P extends Parameter> {

	private final Map<NonTerminal<T>, Set<Production<T, P>>> nonTerminals;
	private final Set<Terminal<T>> terminals;

	private final Set<Production<T, P>> productions;

	private final NonTerminal<T> startSymbol;
	private final ParameterFactory<P> startSymbolParameter;

	private final ParameterManager<P> parameterManager;

	public Grammar(final NonTerminal<T> startSymbol, final ParameterManager<P> parameterManager) {
		this(startSymbol, parameterManager, parameterManager, new HashSet<>());
	}

	public Grammar(final NonTerminal<T> startSymbol, final ParameterFactory<P> startSymbolParameter, final ParameterManager<P> parameterManager, final Set<Production<T, P>> productions) {
		this.productions = productions;
		this.startSymbol = startSymbol;
		this.startSymbolParameter = startSymbolParameter;

		this.parameterManager = parameterManager;

		this.nonTerminals = new HashMap<>();
		this.terminals = new HashSet<>();

		for (Production<T, P> production : this.productions) {
			addProduction(production);
		}
	}

	public void addProduction(Production<T, P> production) {
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

	@SafeVarargs
	public final void addProduction(NonTerminal<T> left, ParameterizedSymbol<T, P>... right) {
		addProduction(left, parameterManager, right);
	}

	@SafeVarargs
	public final void addProduction(NonTerminal<T> left, ParameterFactory<P> keyParameter, ParameterizedSymbol<T, P>... right) {
		addProduction(new Production<T, P>(left, keyParameter, right));
	}

	public Set<NonTerminal<T>> getNonTerminals() {
		return nonTerminals.keySet();
	}

	public Set<Terminal<T>> getTerminals() {
		return terminals;
	}

	public Set<Production<T, P>> getProductions() {
		return productions;
	}

	public Set<Production<T, P>> getProductions(NonTerminal<T> key) {
		Set<Production<T, P>> set = nonTerminals.get(key);
		if (set == null) {
			return Collections.emptySet();
		} else {
			return set;
		}
	}

	public NonTerminal<T> getStartSymbol() {
		return startSymbol;
	}

	public ParameterFactory<P> getStartSymbolParameter() {
		return startSymbolParameter;
	}

	public ParameterManager<P> getParameterManager() {
		return parameterManager;
	}

	public ParameterExpression<T, P> getStartSymbolParameterExpression() {
		return new CopyParameterExpression<T, P>(this, null);
	}
}
