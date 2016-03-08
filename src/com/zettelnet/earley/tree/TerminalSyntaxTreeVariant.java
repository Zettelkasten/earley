package com.zettelnet.earley.tree;

import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;

public class TerminalSyntaxTreeVariant<T, P extends Parameter> implements SyntaxTreeVariant<T, P> {

	private final SyntaxTree<T, P> mainTree;
	
	private final P parameter;

	public TerminalSyntaxTreeVariant(final SyntaxTree<T, P> mainTree, final P parameter) {
		this.mainTree = mainTree;
		this.parameter = parameter;
	}
	
	@Override
	public SyntaxTree<T, P> getMainTree() {
		return mainTree;
	}

	@Override
	public Production<T, P> getProduction() {
		return null;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public List<SyntaxTree<T, P>> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[var ");
		str.append(getParameter().toString().replace(' ', '_'));
		str.append("]");
		return str.toString();
	}
}
