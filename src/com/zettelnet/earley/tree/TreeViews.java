package com.zettelnet.earley.tree;

import java.util.Iterator;
import java.util.function.Function;

import com.zettelnet.earley.param.Parameter;

public class TreeViews {

	public static final <T, P extends Parameter> Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> bestProbability() {
		return (SyntaxTree<T, P> tree) -> {
			Iterator<SyntaxTreeVariant<T, P>> i = tree.getVariantsByProbability().iterator();
			return i.hasNext() ? i.next() : null;
		};
	}

	public static final <T, P extends Parameter> Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> any() {
		return (SyntaxTree<T, P> tree) -> {
			Iterator<SyntaxTreeVariant<T, P>> i = tree.getVariants().iterator();
			return i.hasNext() ? i.next() : null;
		};
	}
}
