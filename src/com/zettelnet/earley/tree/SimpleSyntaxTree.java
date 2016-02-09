package com.zettelnet.earley.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public class SimpleSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final Symbol<T> root;
	private final Collection<SyntaxTreeVariant<T, P>> variants;

	public SimpleSyntaxTree(final Symbol<T> root) {
		this.root = root;
		this.variants = new ArrayList<>();
	}

	@Override
	public Symbol<T> getRootSymbol() {
		return root;
	}

	@Override
	public boolean isTerminal() {
		return root instanceof Terminal;
	}

	@Override
	public Collection<SyntaxTreeVariant<T, P>> getVariants() {
		return variants;
	}

	public SimpleSyntaxTree<T, P> addVariant(SyntaxTreeVariant<T, P> variant) {
		this.variants.add(variant);
		return this;
	}

	public static class Variant<T, P extends Parameter> implements SyntaxTreeVariant<T, P> {

		private final Production<T, P> production;
		private final List<SyntaxTree<T, P>> children;

		public Variant(final Production<T, P> production) {
			this.production = production;
			this.children = new ArrayList<>();
		}

		@Override
		public Production<T, P> getProduction() {
			return production;
		}

		@Override
		public List<SyntaxTree<T, P>> getChildren() {
			return children;
		}

		public Variant<T, P> addChild(SyntaxTree<T, P> child) {
			this.children.add(0, child);
			return this;
		}
	}
}
