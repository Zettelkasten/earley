package com.zettelnet.earley.process;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class ProcessableGrammar<T, P extends Parameter, R> implements Grammar<T, P>, ProcessingManager<T, P, R> {

	private final Map<NonTerminal<T>, Set<Production<T, P>>> nonTerminals;
	private final Map<Terminal<T>, Processor<T, P, R>> terminals;

	private final Map<Production<T, P>, Processor<T, P, R>> productions;

	private NonTerminal<T> startSymbol;
	private ParameterFactory<T, P> startSymbolParameter;

	private final ParameterManager<T, P> parameterManager;
	private final TokenParameterizer<T, P> parameterizer;

	public ProcessableGrammar(final NonTerminal<T> startSymbol, final ParameterManager<T, P> parameterManager, final TokenParameterizer<T, P> parameterizer) {
		this(startSymbol, parameterManager, parameterManager, parameterizer);
	}

	public ProcessableGrammar(final NonTerminal<T> startSymbol, final ParameterFactory<T, P> startSymbolParameter, final ParameterManager<T, P> parameterManager, final TokenParameterizer<T, P> parameterizer) {
		this(startSymbol, startSymbolParameter, parameterManager, parameterizer, new HashSet<>());
	}

	public ProcessableGrammar(final NonTerminal<T> startSymbol, final ParameterFactory<T, P> startSymbolParameter, final ParameterManager<T, P> parameterManager, final TokenParameterizer<T, P> parameterizer, final Set<ProcessableProduction<T, P, R>> productions) {
		this.startSymbol = startSymbol;
		this.startSymbolParameter = startSymbolParameter;

		this.parameterManager = parameterManager;
		this.parameterizer = parameterizer;

		this.productions = new HashMap<>();
		this.nonTerminals = new HashMap<>();
		this.terminals = new HashMap<>();

		for (ProcessableProduction<T, P, R> production : productions) {
			addProduction(production);
		}
	}

	public void addProduction(ProcessableProduction<T, P, R> processable) {
		addProduction(processable.getProduction(), processable.getProcessor());
	}

	public void addProduction(Production<T, P> production, Processor<T, P, R> processor) {
		this.productions.put(production, processor);

		NonTerminal<T> key = production.key();
		if (!nonTerminals.containsKey(key)) {
			nonTerminals.put(key, new HashSet<>());
		}
		nonTerminals.get(key).add(production);

		for (Symbol<T> symbol : production.values()) {
			if (symbol instanceof Terminal) {
				terminals.putIfAbsent((Terminal<T>) symbol, null);
			}
		}
	}
	
	public void setProcessor(Terminal<T> terminal, Processor<T, P, R> processor) {
		terminals.put(terminal, processor);
	}

	@Override
	public Set<NonTerminal<T>> getNonTerminals() {
		return nonTerminals.keySet();
	}

	@Override
	public Set<Terminal<T>> getTerminals() {
		return terminals.keySet();
	}

	@Override
	public Set<Production<T, P>> getProductions() {
		return productions.keySet();
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
	public TokenParameterizer<T, P> getParameterizer() {
		return parameterizer;
	}
	
	@Override
	public ParameterExpression<T, P> getStartSymbolParameterExpression() {
		return new CopyParameterExpression<T, P>(this);
	}

	@Override
	public R process(SyntaxTree<T, P> tree) {
		// choose variant: currently just use the first one
		SyntaxTreeVariant<T, P> variant = tree.getVariants().iterator().next();

		if (variant.isTerminal()) {
			return terminals.get(variant.getRootSymbol()).process(this, variant);
		} else {
			Production<T, P> production = variant.getProduction();
			Processor<T, P, R> processor = productions.get(production);

			return processor.process(this, variant);
		}
	}
}
