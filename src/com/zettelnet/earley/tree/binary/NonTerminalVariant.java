package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;

public class NonTerminalVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;
	private final StateCause.Complete<T, P> cause;

	public NonTerminalVariant(final State<T, P> state, final StateCause.Complete<T, P> cause) {
		this.state = state;
		this.cause = cause;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return new StateSyntaxTree<>(cause.getPreState());
	}

	@Override
	public NonTerminal<T> getSymbol() {
		return (NonTerminal<T>) state.last();
	}
	
	@Override
	public T getToken() {
		return null;
	}

	@Override
	public Production<T, P> getChildProduction() {
		return state.getProduction();
	}

	@Override
	public P getChildParameter() {
		return state.getParameter();
	}
	
	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return new StateSyntaxTree<>(cause.getChildState());
	}
}
