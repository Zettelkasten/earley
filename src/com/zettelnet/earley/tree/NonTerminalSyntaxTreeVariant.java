package com.zettelnet.earley.tree;

import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;

public class NonTerminalSyntaxTreeVariant<T, P extends Parameter> implements SyntaxTreeVariant<T, P> {

	private final Production<T, P> production;
	private final P parameter;
	
	private final List<SyntaxTree<T, P>> children;

	public NonTerminalSyntaxTreeVariant(final Production<T, P> production, final P parameter, final List<SyntaxTree<T, P>> children) {
		this.production = production;
		this.parameter = parameter;
		this.children = children;
	}

	@Override
	public Production<T, P> getProduction() {
		return production;
	}
	
	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public List<SyntaxTree<T, P>> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[var ");
		str.append(getParameter().toString().replace(' ', '_'));
		str.append(" ");
		for (SyntaxTree<T, P> child : children) {
			str.append(child);
			str.append(" ");
		}
		str.append("]");
		return str.toString();
	}
}
