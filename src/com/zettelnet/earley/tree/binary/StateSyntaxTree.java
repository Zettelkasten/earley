package com.zettelnet.earley.tree.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryInitialSyntaxTree;

public class StateSyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final State<T, P> state;

	public StateSyntaxTree(final State<T, P> state) {
		this.state = state;
	}

	@Override
	public Collection<BinarySyntaxTreeVariant<T, P>> getVariants() {
		Collection<StateCause<T, P>> causes = state.getCause();
		List<BinarySyntaxTreeVariant<T, P>> variants = new ArrayList<>(causes.size());

		boolean allowEpsilon = false;

		for (StateCause<T, P> cause : causes) {
			if (cause instanceof StateCause.Predict) {
				allowEpsilon = true;
			} else if (cause instanceof StateCause.Scan) {
				variants.add(new TerminalVariant<>(state, (StateCause.Scan<T, P>) cause));
			} else if (cause instanceof StateCause.Complete) {
				variants.add(new NonTerminalVariant<>(state, (StateCause.Complete<T, P>) cause));
			} else {
				throw new AssertionError("Unknown StateCause type!");
			}
		}

		if (allowEpsilon) {
			variants.add(new EpsilonVariant<>());
		}

		return variants;
	}

	@Override
	public SyntaxTree<T, P> toNaturalTree() {
		return new UnbinaryInitialSyntaxTree<>(null, this);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[n");
		Collection<BinarySyntaxTreeVariant<T, P>> variants = getVariants();
		for (BinarySyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}
}
