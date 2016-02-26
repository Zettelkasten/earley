package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;

public class TerminalVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;
	private final StateCause.Scan<T, P> cause;

	public TerminalVariant(final State<T, P> state, final StateCause.Scan<T, P> cause) {
		this.state = state;
		this.cause = cause;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return new StateSyntaxTree<>(cause.getPreState());
	}

	@Override
	public Terminal<T> getSymbol() {
		return (Terminal<T>) state.last();
	}
	
	@Override
	public T getToken() {
		return cause.getToken();
	}

	@Override
	public Production<T, P> getChildProduction() {
		return null;
	}
	
	@Override
	public P getChildParameter() {
		return null;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
}
