package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;

public abstract class AbstractBinarySyntaxTreeVariant<T, P extends Parameter> implements BinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;
	private final StateCause<T, P> cause;

	public AbstractBinarySyntaxTreeVariant(final State<T, P> state, final StateCause<T, P> cause) {
		this.state = state;
		this.cause = cause;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		if (cause.getPreState().getCurrentPosition() == 0) {
			return null;
		} else {
			return new NonTerminalBinarySyntaxTree<>(cause.getPreState());
		}
	}

	@Override
	public boolean isFirst() {
		return state.getCurrentPosition() == state.getProduction().size();
	}

	@Override
	public Production<T, P> getProduction() {
		return state.getProduction();
	}

	@Override
	public P getParameter() {
		return state.getParameter();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[var ");
		if (isFirst()) {
			Production<T, P> production = getProduction();
			if (production != null) {
				str.append(production);
				str.append(" ");
			}
		}

		BinarySyntaxTree<T, P> preNode = getPreNode();
		if (preNode != null) {
			str.append(preNode);
			str.append(" ");
		}
		BinarySyntaxTree<T, P> childNode = getChildNode();
		if (childNode != null) {
			str.append(childNode);
			str.append(" ");
		}
		str.append("]");
		return str.toString();
	}
}
