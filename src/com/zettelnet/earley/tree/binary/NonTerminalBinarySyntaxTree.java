package com.zettelnet.earley.tree.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryNonTerminalSyntaxTree;

public class NonTerminalBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final State<T, P> state;

	public NonTerminalBinarySyntaxTree(final State<T, P> state) {
		this.state = state;
	}

	@Override
	public List<BinarySyntaxTreeVariant<T, P>> getVariants() {
		Collection<StateCause<T, P>> causes = state.getCause();
		List<BinarySyntaxTreeVariant<T, P>> variants = new ArrayList<>(causes.size());

		for (StateCause<T, P> cause : causes) {
			if (cause instanceof StateCause.Predict) {
				// ignore
			} else if (cause instanceof StateCause.Scan) {
				variants.add(new TerminalVariant<>(state, (StateCause.Scan<T, P>) cause));
			} else if (cause instanceof StateCause.Complete) {
				variants.add(new NonTerminalVariant<>(state, (StateCause.Complete<T, P>) cause));
			} else if (cause instanceof StateCause.Epsilon) {
				variants.add(new EpsilonChildVariant<>(state, (StateCause.Epsilon<T, P>) cause));
			} else {
				throw new AssertionError("Unknown StateCause type!");
			}
		}

		return variants;
	}

	@Override
	public boolean isFirst() {
		return state.getCurrentPosition() == state.getProduction().size();
	}

	@Override
	public NonTerminal<T> getRootSymbol() {
		return state.getProduction().key();
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
	public SyntaxTree<T, P> toNaturalTree() {
		if (!isFirst()) {
			throw new AssertionError("Only first binary syntax tree can be converted to natural tree");
		} else {
			return new UnbinaryNonTerminalSyntaxTree<>(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		Collection<BinarySyntaxTreeVariant<T, P>> variants = getVariants();
//		if (!variants.isEmpty()) {
			if (isFirst()) {
				str.append("[" + getRootSymbol() + " ");
			} else {
				str.append("[n ");
			}
			for (BinarySyntaxTreeVariant<T, P> variant : variants) {
				str.append(" ");
				str.append(variant);
			}
			str.append("]");
//		}
		return str.toString();
	}
}
