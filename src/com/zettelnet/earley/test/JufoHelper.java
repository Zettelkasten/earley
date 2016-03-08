package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.latin.demo.string.DrawTree;

public class JufoHelper {

	public static <T, P extends Parameter> void present(EarleyParseResult<T, P> result, List<T> tokens) {
		try {
			new ChartSetPrinter<T, P>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(result.getBinarySyntaxTree());

		System.out.println(result.getSyntaxTree());
		System.out.println(toStringBold(result.getSyntaxTree()));

		 DrawTree.draw(new String[0], toStringBold(result.getSyntaxTree()));
	}

	public static <T, P extends Parameter> String toString(SyntaxTree<T, P> tree) {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(tree.getRootSymbol());
		if (!tree.isTerminal()) {
			SyntaxTreeVariant<T, P> variant = tree.getVariant(Collections.<Integer> emptyList().iterator());
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				str.append(" ");
				str.append(toString(child));
			}
		}
		str.append("]");

		return str.toString();
	}

	public static <T, P extends Parameter> String toStringBold(SyntaxTree<T, P> tree) {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(tree.getRootSymbol());
		if (!tree.isTerminal()) {
			SyntaxTreeVariant<T, P> variant = tree.getVariant(Collections.<Integer> emptyList().iterator());
			 str.append("(");
			 str.append(variant.getParameter().toString().replace(' ', '_'));
			 str.append(")");
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				str.append(" ");
				str.append(toStringBold(child));
			}
		} else {
			str.append(" #b_");
			str.append(tree.getToken());
		}
		str.append("]");

		return str.toString();
	}
}
