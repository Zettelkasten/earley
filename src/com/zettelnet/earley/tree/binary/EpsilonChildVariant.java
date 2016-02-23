package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;

public class EpsilonChildVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;
	private final StateCause.Epsilon<T, P> cause;

	public EpsilonChildVariant(final State<T, P> state, final StateCause.Epsilon<T, P> cause) {
		this.state = state;
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
	public Production<T, P> getChildProduction() {
		return cause.getChildProduction();
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
}
