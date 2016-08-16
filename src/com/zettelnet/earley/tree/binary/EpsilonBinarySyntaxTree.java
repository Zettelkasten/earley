package com.zettelnet.earley.tree.binary;

import java.util.Arrays;
import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryNonTerminalSyntaxTree;

public class EpsilonBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final Production<T, P> epsilonProduction;
	private final P epsilonParameter;
	
	public EpsilonBinarySyntaxTree(final Production<T, P> epsilonProduction, final P epsilonParameter) {
		assert epsilonProduction.isEpsilon();
		
		this.epsilonProduction = epsilonProduction;
		this.epsilonParameter = epsilonParameter;
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
				return epsilonProduction;
			}

			@Override
			public P getParameter() {
				return epsilonParameter;
			}

			@Override
			public BinarySyntaxTree<T, P> getChildNode() {
				return null;
			}
			
			@Override
			public double getLocalProbability() {
				return epsilonProduction.getProbability();
			}
		});
	}

	@Override
	public Symbol<T> getRootSymbol() {
		return epsilonProduction.key();
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
