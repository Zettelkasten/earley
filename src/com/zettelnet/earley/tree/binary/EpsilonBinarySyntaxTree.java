package com.zettelnet.earley.tree.binary;

import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryNonTerminalSyntaxTree;

public class EpsilonBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final NonTerminal<T> symbol;
	
	public EpsilonBinarySyntaxTree(final NonTerminal<T> symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public List<BinarySyntaxTreeVariant<T, P>> getVariants() {
		return Arrays.asList(new BinarySyntaxTreeVariant<T, P>() {

			@Override
			public BinarySyntaxTree<T, P> getMainNode() {
				return EpsilonBinarySyntaxTree.this;
			}

			@Override
			public BinarySyntaxTree<T, P> getPreNode() {
				return null;
			}

			@Override
			public boolean isFirst() {
				return true;
			}

			@Override
			public Production<T, P> getProduction() {
				return null;
			}

			@Override
			public P getParameter() {
				return null;
			}

			@Override
			public BinarySyntaxTree<T, P> getChildNode() {
				return null;
			}
		});
	}

	@Override
	public Symbol<T> getRootSymbol() {
		return symbol;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public T getToken() {
		return null;
	}

	@Override
	public P getTokenParameter() {
		return null;
	}

	@Override
	public boolean isFirst() {
		return true;
	}

	@Override
	public SyntaxTree<T, P> toNaturalTree() {
		return new UnbinaryNonTerminalSyntaxTree<>(this);
	}
}
