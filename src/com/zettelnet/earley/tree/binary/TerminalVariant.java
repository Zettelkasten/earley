package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;

public class TerminalVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;
	private final StateCause.Scan<T, P> cause;

	public TerminalVariant(final State<T, P> state, final StateCause.Scan<T, P> cause) {
		super(state, cause);
		this.state = state;
		this.cause = cause;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return new TerminalBinarySyntaxTree<>((Terminal<T>) state.last(), cause.getToken(), cause.getTokenParameter());
	}
}
