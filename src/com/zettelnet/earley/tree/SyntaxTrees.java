package com.zettelnet.earley.tree;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class SyntaxTrees {

	private final static NumberFormat percentFormat;
	static {
		percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(1);
	}

	public static Comparator<SyntaxTreeVariant<?, ?>> PROBABILITY_COMPARATOR = (SyntaxTreeVariant<?, ?> a, SyntaxTreeVariant<?, ?> b) -> {
		return -Double.compare(a.getProbability(), b.getProbability());
	};

	public static class FormatConfiguration {

		private boolean showParameters = false;
		private boolean showProbabilities = false;

		private boolean useAdvancedBrackets = true;
		private boolean allowSpaces = false;

		private boolean indentedTree = false;

		public FormatConfiguration() {
		}

		public FormatConfiguration showParameters(boolean showParameters) {
			this.showParameters = showParameters;
			return this;
		}

		public FormatConfiguration showProbabilities(boolean showProbabilities) {
			this.showProbabilities = showProbabilities;
			return this;
		}

		public FormatConfiguration useAdvancedBrackets(boolean useAdvancedBrackets) {
			this.useAdvancedBrackets = useAdvancedBrackets;
			return this;
		}

		public FormatConfiguration allowSpaces(boolean allowSpaces) {
			this.allowSpaces = allowSpaces;
			return this;
		}

		public FormatConfiguration indentedTree(boolean indentedTree) {
			this.indentedTree = indentedTree;
			return this;
		}

		public boolean isShowParameters() {
			return showParameters;
		}

		public boolean isShowProbabilities() {
			return showProbabilities;
		}

		public boolean isUseAdvancedBrackets() {
			return useAdvancedBrackets;
		}

		public boolean isAllowSpaces() {
			return allowSpaces;
		}

		public boolean isIndentedTree() {
			return indentedTree;
		}
	}

	public static final FormatConfiguration COMPACT = new FormatConfiguration().showParameters(false).showProbabilities(false).useAdvancedBrackets(true).allowSpaces(true);
	public static final FormatConfiguration DETAILED = new FormatConfiguration().showParameters(true).showProbabilities(true).useAdvancedBrackets(true).allowSpaces(true);
	public static final FormatConfiguration INDENTED = new FormatConfiguration().showParameters(true).showProbabilities(true).useAdvancedBrackets(true).allowSpaces(true).indentedTree(true);

	public static final FormatConfiguration COMPACT_TREE = new FormatConfiguration().showParameters(false).showProbabilities(false).useAdvancedBrackets(false).allowSpaces(false);
	public static final FormatConfiguration DETAILED_TREE = new FormatConfiguration().showParameters(true).showProbabilities(true).useAdvancedBrackets(false).allowSpaces(false);

	public static <T, P extends Parameter> String toString(SyntaxTree<T, P> tree) {
		return toString(tree, COMPACT);
	}

	public static <T, P extends Parameter> String toString(SyntaxTree<T, P> tree, FormatConfiguration formatOptions) {
		StringBuilder str = new StringBuilder();

		if (formatOptions.isUseAdvancedBrackets()) {
			str.append("{");
		} else {
			str.append("[vars");
		}

		Iterable<SyntaxTreeVariant<T, P>> variants = tree.getVariants();
		for (SyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(toString(variant, formatOptions));
		}

		if (formatOptions.isUseAdvancedBrackets()) {
			str.append("}");
		} else {
			str.append("]");
		}
		return str.toString();
	}

	public static <T, P extends Parameter> String toString(SyntaxTreeVariant<T, P> variant) {
		return toString(variant, COMPACT);
	}

	public static <T, P extends Parameter> String toString(SyntaxTreeVariant<T, P> variant, FormatConfiguration formatOptions) {
		StringBuilder str = new StringBuilder();

		if (formatOptions.isUseAdvancedBrackets()) {
			str.append("[");
		} else {
			str.append("[var/");
		}
		str.append(symbolName(variant.getRootSymbol()));

		if (formatOptions.isShowParameters()) {
			str.append("/");
			if (formatOptions.isAllowSpaces()) {
				str.append(variant.getParameter());

			} else {
				str.append(variant.getParameter().toString().replace(' ', '_'));
			}
		}
		if (formatOptions.isShowProbabilities()) {
			str.append("/");
			str.append(percentFormat.format(variant.getProbability()));
		}

		if (variant.isTerminal()) {
			str.append("=");
			str.append(variant.getToken());
			str.append("]");
			return str.toString();
		} else {
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				str.append(" ");
				str.append(child);
			}
			str.append("]");
			return str.toString();
		}
	}

	public static <T, P extends Parameter> List<T> traverse(SyntaxTree<T, P> tree, Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> variantFunction) {
		SyntaxTreeVariant<T, P> variant = variantFunction.apply(tree);
		if (variant.isTerminal()) {
			return Arrays.asList(variant.getToken());
		} else {
			List<T> childrenTokens = new ArrayList<>();
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				childrenTokens.addAll(traverse(child, variantFunction));
			}
			return childrenTokens;
		}
	}

	public static <T, P extends Parameter> String getTreeView(SyntaxTree<T, P> tree, Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> variantFunction) {
		return getTreeView(tree, variantFunction, COMPACT);
	}

	public static <T, P extends Parameter> String getTreeView(SyntaxTree<T, P> tree, Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> variantFunction, FormatConfiguration formatOptions) {
		return getTreeView(tree, variantFunction, formatOptions, 0);
	}

	private static <T, P extends Parameter> String getTreeView(SyntaxTree<T, P> tree, Function<SyntaxTree<T, P>, SyntaxTreeVariant<T, P>> variantFunction, FormatConfiguration formatOptions, int indent) {
		SyntaxTreeVariant<T, P> variant = variantFunction.apply(tree);

		StringBuilder str = new StringBuilder();
		indent(str, indent);
		str.append("[");
		str.append(symbolName(variant.getRootSymbol()));

		if (formatOptions.isShowParameters()) {
			str.append("/");
			if (formatOptions.isAllowSpaces()) {
				str.append(variant.getParameter());

			} else {
				str.append(variant.getParameter().toString().replace(' ', '_'));
			}
		}
		if (formatOptions.isShowProbabilities()) {
			str.append("/");
			str.append(percentFormat.format(variant.getProbability()));
		}

		if (!variant.isTerminal()) {
			for (SyntaxTree<T, P> child : variant.getChildren()) {
				if (formatOptions.isIndentedTree()) {
					str.append(System.lineSeparator());
				} else {
					str.append(" ");
				}
				str.append(getTreeView(child, variantFunction, formatOptions, indent + 2));
			}
			if (formatOptions.isIndentedTree()) {
				str.append(System.lineSeparator());
				indent(str, indent);
			}
		} else {
			str.append(" ");
			str.append(variant.getToken());
		}
		str.append("]");

		return str.toString();
	}

	private static <T> String symbolName(Symbol<T> symbol) {
		return symbol.toString().replace('[', '{').replace(']', '}');
	}

	private static StringBuilder indent(StringBuilder str, int amount) {
		while (amount > 0) {
			str.append(' ');
			amount--;
		}
		return str;
	}
}
