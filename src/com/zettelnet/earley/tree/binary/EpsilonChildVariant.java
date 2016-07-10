package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;

public class EpsilonChildVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;

	public EpsilonChildVariant(final BinarySyntaxTree<T, P> mainNode, final State<T, P> state, final StateCause.Epsilon<T, P> cause) {
		super(mainNode, state, cause);

		this.state = state;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return new EpsilonBinarySyntaxTree<T, P>((NonTerminal<T>) state.getProduction().get(state.getCurrentPosition() - 1));
	}
}
