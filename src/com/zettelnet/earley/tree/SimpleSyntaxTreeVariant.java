package com.zettelnet.earley.tree;

import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;

public class SimpleSyntaxTreeVariant<T, P extends Parameter> implements SyntaxTreeVariant<T, P> {

	private final Production<T, P> production;
	private final List<SyntaxTree<T, P>> children;

	public SimpleSyntaxTreeVariant(final Production<T, P> production, final List<SyntaxTree<T, P>> children) {
		this.production = production;
		this.children = children;
	}

	@Override
	public Production<T, P> getProduction() {
		return production;
	}

	@Override
	public List<SyntaxTree<T, P>> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[var ");
		for (SyntaxTree<T, P> child : children) {
			str.append(child);
			str.append(" ");
		}
		str.append("]");
		return str.toString();
	}
}
