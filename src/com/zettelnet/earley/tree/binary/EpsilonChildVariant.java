package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;

public class EpsilonChildVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final StateCause.Epsilon<T, P> cause;

	public EpsilonChildVariant(final StateCause.Epsilon<T, P> cause) {
		this.cause = cause;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return new StateSyntaxTree<>(cause.getPreState());
	}

	@Override
	public NonTerminal<T> getSymbol() {
		return null;
	}

	@Override
	public T getToken() {
		return null;
	}

	@Override
	public boolean isFirst() {
		return false;
	}

	@Override
	public Production<T, P> getProduction() {
		return null;
	}

	@Override
	public P getParameter() {
		return null;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
}
