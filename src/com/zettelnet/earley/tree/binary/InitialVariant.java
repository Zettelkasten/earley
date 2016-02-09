package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;

public class InitialVariant<T, P extends Parameter> implements BinarySyntaxTreeVariant<T, P> {

	private final NonTerminal<T> rootSymbol;
	private final State<T, P> state;

	public InitialVariant(final NonTerminal<T> rootSymbol, final State<T, P> state) {
		this.rootSymbol = rootSymbol;
		this.state = state;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return null;
	}

	@Override
	public NonTerminal<T> getSymbol() {
		return rootSymbol;
	}

	@Override
	public Production<T, P> getChildProduction() {
		return state.getProduction();
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return new StateSyntaxTree<>(state);
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
