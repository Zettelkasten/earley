package com.zettelnet.earley.translate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public class ConcreteTranslationTree<T, P extends Parameter, U, Q extends Parameter> implements TranslationTree<T, P, U, Q> {

	private final Symbol<U> symbol;
	private final ParameterTranslator<P, Q> parameterTranslator;
	
	private final List<TranslationTree<T, P, U, Q>> children;

	@SafeVarargs
	public ConcreteTranslationTree(final NonTerminal<U> symbol, final ParameterTranslator<P, Q> parameterTranslator, TranslationTree<T, P, U, Q>... children) {
		this.symbol = symbol;
		this.parameterTranslator = parameterTranslator;
		
		this.children = Arrays.asList(children);
	}
	
	public ConcreteTranslationTree(final Terminal<U> symbol, final ParameterTranslator<P, Q> parameterTranslator) {
		this.symbol = symbol;
		this.parameterTranslator = parameterTranslator;
		
		this.children = Collections.emptyList();
	}
	
	@Override
	public Symbol<U> getRootSymbol() {
		return symbol;
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
	public ParameterTranslator<P, Q> getParameterTranslator() {
		return parameterTranslator;
	}

	@Override
	public List<TranslationTree<T, P, U, Q>> getChildren() {
		return children;
	}
}
