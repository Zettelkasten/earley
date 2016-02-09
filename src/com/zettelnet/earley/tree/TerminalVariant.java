package com.zettelnet.earley.tree;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.Terminal;
import com.zettelnet.earley.param.Parameter;

public class TerminalVariant<T, P extends Parameter> implements BinarySyntaxTreeVariant<T, P> {

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
		return (Terminal<T>) state.getProduction().get(state.getCurrentPosition() - 1);
	}

	@Override
	public Production<T, P> getChildProduction() {
		return null;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
	
	@Override
	public String toString() {
		return "TerminalVariant";
	}
}