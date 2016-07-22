package com.zettelnet.earley.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.param.Parameter;

public class SyntaxTrees {

	public static <T, P extends Parameter> String toString(SyntaxTree<T, P> tree) {
		StringBuilder str = new StringBuilder();
		str.append("[");
		Iterable<SyntaxTreeVariant<T, P>> variants = tree.getVariants();
		for (SyntaxTreeVariant<T, P> variant : variants) {
			str.append(variant);
			str.append(" ");
		}
		str.append("]");
		return str.toString();
	}

	public static <T, P extends Parameter> String toString(SyntaxTreeVariant<T, P> variant) {
		if (variant.isTerminal()) {
			StringBuilder str = new StringBuilder();
			str.append("[var ");
			str.append(variant.getRootSymbol());
			str.append(" ");
			str.append(variant.getToken());
			str.append(" ");
			str.append(variant.getParameter().toString().replace(' ', '_'));
			str.append("]");
			return str.toString();
		} else {
			StringBuilder str = new StringBuilder();
			str.append("[var ");
			str.append(variant.getRootSymbol());
			str.append(" ");
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				str.append(child);
				str.append(" ");
			}
			str.append("]");
			return str.toString();
		}
	}
	
	public static <T, P extends Parameter> List<T> traverse(SyntaxTree<T, P> tree) {
		SyntaxTreeVariant<T, P> variant = tree.getVariants().iterator().next();
		if (variant.isTerminal()) {
			return Arrays.asList(variant.getToken());
		} else {
			List<T> childrenTokens = new ArrayList<>();
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				childrenTokens.addAll(traverse(child));
			}
			return childrenTokens;
		}
	}
}
