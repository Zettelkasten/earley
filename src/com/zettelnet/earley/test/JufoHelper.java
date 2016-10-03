package com.zettelnet.earley.test;

import java.util.Collections;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class JufoHelper {

	public static <T, P extends Parameter> void present(SyntaxTree<T, P> tree) {
//		DrawTree.draw(new String[0], toStringBold(tree));
//		DrawTree.draw(new String[0], SyntaxTrees.toString(tree, SyntaxTrees.COMPACT_TREE).replaceAll("\\[\\[", "[vars [").replaceAll("[ *]", " "));
	}

	public static <T, P extends Parameter> String toString(SyntaxTree<T, P> tree) {
		SyntaxTreeVariant<T, P> variant = tree.getVariant(Collections.<Integer> emptyList().iterator());

		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(variant.getRootSymbol());
		if (!variant.isTerminal()) {
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				str.append(" ");
				str.append(toString(child));
			}
		}
		str.append("]");

		return str.toString();
	}

	public static <T, P extends Parameter> String toStringBold(SyntaxTree<T, P> tree) {
		SyntaxTreeVariant<T, P> variant = tree.getVariants().iterator().next();

		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(variant.getRootSymbol());

		if (!variant.isTerminal()) {

			// str.append("(");
			// str.append(variant.getParameter().toString().replace(' ', '_'));
			// str.append(")");
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				str.append(" ");

				str.append(toStringBold(child));
			}
		} else {
			str.append(" #b_");
			str.append(variant.getToken());
		}
		str.append("]");

		return str.toString();
	}
}
