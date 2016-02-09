package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

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
		StringBuilder str = new StringBuilder();
		str.append("[var ");
		BinarySyntaxTree<T, P> preNode = getPreNode();
		if (preNode != null) {
			str.append(preNode);
			str.append(" ");
		}
		Symbol<T> symbol = getSymbol();
		if (symbol != null) {
			str.append(symbol);
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
