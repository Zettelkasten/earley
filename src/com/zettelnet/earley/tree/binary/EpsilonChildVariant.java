package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;

public class EpsilonChildVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	public EpsilonChildVariant(final BinarySyntaxTree<T, P> mainNode, final State<T, P> state, final StateCause.Epsilon<T, P> cause) {
		super(mainNode, state, cause);
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
}
