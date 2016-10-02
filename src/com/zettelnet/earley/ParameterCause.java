package com.zettelnet.earley;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public abstract class ParameterCause<T, P extends Parameter> {

	private final State<T, P> from, to;

	public ParameterCause(final State<T, P> from, final State<T, P> to) {
		this.from = from;
		this.to = to;
	}

	public State<T, P> getFromState() {
		return from;
	}

	public State<T, P> getToState() {
		return to;
	}

	public P getFrom() {
		return from.getParameter();
	}

	public P getTo() {
		return to.getParameter();
	}

	public Symbol<T> getSymbol() {
		return from.next();
	}

	public ParameterExpression<T, P> getParameterExpression() {
		return from.nextParameterExpression();
	}

	public static <T, P extends Parameter> ParameterCause<T, P> fromStateCause(State<T, P> state, StateCause<T, P> stateCause) {
		if (stateCause instanceof StateCause.Predict) {
			return new Predict<>(((StateCause.Predict<T, P>) stateCause).getParentState(), state);
		} else if (stateCause instanceof StateCause.Scan) {
			return new Scan<>(((StateCause.Scan<T, P>) stateCause).getPreState(), state);
		} else if (stateCause instanceof StateCause.Complete) {
			return new Complete<>(((StateCause.Complete<T, P>) stateCause).getChildState(), state);
		} else if (stateCause instanceof StateCause.Epsilon) {
			return new Epsilon<>(((StateCause.Epsilon<T, P>) stateCause).getPreState(), state);
		} else {
			throw new AssertionError("Unknown StateCause type");
		}
	}

	public static class Predict<T, P extends Parameter> extends ParameterCause<T, P> {

		public Predict(final State<T, P> from, final State<T, P> to) {
			super(from, to);
		}

		@Override
		public NonTerminal<T> getSymbol() {
			return (NonTerminal<T>) super.getSymbol();
		}
	}

	public static class Scan<T, P extends Parameter> extends ParameterCause<T, P> {

		public Scan(final State<T, P> from, final State<T, P> to) {
			super(from, to);
		}

		@Override
		public Terminal<T> getSymbol() {
			return (Terminal<T>) super.getSymbol();
		}
	}

	public static class Complete<T, P extends Parameter> extends ParameterCause<T, P> {

		public Complete(final State<T, P> from, final State<T, P> to) {
			super(from, to);
		}

		@Override
		public NonTerminal<T> getSymbol() {
			return (NonTerminal<T>) super.getSymbol();
		}
	}

	public static class Epsilon<T, P extends Parameter> extends ParameterCause<T, P> {

		public Epsilon(final State<T, P> from, final State<T, P> to) {
			super(from, to);
		}
	}
}
