package com.zettelnet.earley.translate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public class ConcreteTranslationTree<T, P extends Parameter, U, Q extends Parameter> implements TranslationTreeVariant<T, P, U, Q> {

	private final Symbol<U> symbol;
	private final boolean terminal;
	
	private final ParameterTranslator<T, P, U, Q> parameterTranslator;
	
	private final double probability;
	
	private final List<TranslationTree<T, P, U, Q>> children;

	@SafeVarargs
	public ConcreteTranslationTree(final NonTerminal<U> symbol, final ParameterTranslator<T, P, U, Q> parameterTranslator, final double probability, TranslationTree<T, P, U, Q>... children) {
		this.symbol = symbol;
		this.terminal = false;
		this.parameterTranslator = parameterTranslator;
		this.probability = probability;
		
		this.children = Arrays.asList(children);
	}
	
	public ConcreteTranslationTree(final Terminal<U> symbol, final ParameterTranslator<T, P, U, Q> parameterTranslator, final double probability) {
		this.symbol = symbol;
		this.terminal = true;
		this.parameterTranslator = parameterTranslator;
		this.probability = probability;
		
		this.children = Collections.emptyList();
	}
	
	@Override
	public Symbol<U> getRootSymbol() {
		return symbol;
	}

	@Override
	public boolean isTerminal() {
		return terminal;
	}
	
	@Override
	public boolean isAbstract() {
		return false;
	}

	@Override
	public AbstractReference<T, P> getAbstractReference() {
		return null;
	}

	@Override
	public ParameterTranslator<T, P, U, Q> getParameterTranslator() {
		return parameterTranslator;
	}

	@Override
	public List<TranslationTree<T, P, U, Q>> getChildren() {
		return children;
	}
	
	@Override
	public double getLocalProbability() {
		return probability;
	}
}
