package com.zettelnet.earley.tree;

import java.util.function.Function;

import com.zettelnet.earley.param.Parameter;

public class TreeViews {

	public static final <T, P extends Parameter> Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> bestProbability() {
		return (SyntaxTree<T, P> tree) -> {
			return tree.getVariantsByProbability().iterator().next();
		};
	}

	public static final <T, P extends Parameter> Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> any() {
		return (SyntaxTree<T, P> tree) -> {
			return tree.getVariants().iterator().next();
		};
	}
}
