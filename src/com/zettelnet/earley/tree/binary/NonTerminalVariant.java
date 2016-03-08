package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;

public class NonTerminalVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final StateCause.Complete<T, P> cause;

	public NonTerminalVariant(final BinarySyntaxTree<T, P> mainNode, final State<T, P> state, final StateCause.Complete<T, P> cause) {
		super(mainNode, state, cause);
		this.cause = cause;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return new NonTerminalBinarySyntaxTree<>(cause.getChildState());
	}
}
