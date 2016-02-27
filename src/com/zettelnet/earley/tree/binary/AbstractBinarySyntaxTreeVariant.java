package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public abstract class AbstractBinarySyntaxTreeVariant<T, P extends Parameter> implements BinarySyntaxTreeVariant<T, P> {

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
		if (isTerminal()) {
			Symbol<T> symbol = getSymbol();
			if (symbol != null) {
				str.append("[");
				str.append(symbol);
				T token = getToken();
				if (token != null) {
					str.append(" ");
					str.append(token);
				}
				str.append("] ");
			}
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
