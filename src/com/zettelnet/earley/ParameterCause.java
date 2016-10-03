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

	public abstract Symbol<T> getSymbol();

	public abstract ParameterExpression<T, P> getParameterExpression();

	public static <T, P extends Parameter> ParameterCause<T, P> fromStateCause(State<T, P> state, StateCause<T, P> stateCause) {
		if (stateCause instanceof StateCause.Predict) {
			return new Predict<>(((StateCause.Predict<T, P>) stateCause).getParentState(), state);
		} else if (stateCause instanceof StateCause.Scan) {
			StateCause.Scan<T, P> scan = (StateCause.Scan<T, P>) stateCause;
			return new Scan<>(((StateCause.Scan<T, P>) stateCause).getPreState(), state, scan.getToken(), scan.getTokenParameter());
		} else if (stateCause instanceof StateCause.Complete) {
			return new Complete<>(((StateCause.Complete<T, P>) stateCause).getChildState(), state, stateCause.getPreState().getParameter());
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
			return (NonTerminal<T>) getFromState().next();
		}

		@Override
		public ParameterExpression<T, P> getParameterExpression() {
			return getFromState().nextParameterExpression();
		}
		
		public Production<T, P> getProduction() {
			return getToState().getProduction();
		}
	}

	public static class Scan<T, P extends Parameter> extends ParameterCause<T, P> {

		private final T token;
		private final P tokenParameter;
		
		public Scan(final State<T, P> from, final State<T, P> to, final T token, final P tokenParameter) {
			super(from, to);
			this.token = token;
			this.tokenParameter = tokenParameter;
		}

		@Override
		public Terminal<T> getSymbol() {
			return (Terminal<T>) getFromState().next();
		}

		@Override
		public ParameterExpression<T, P> getParameterExpression() {
			return getFromState().nextParameterExpression();
		}
		
		public T getToken() {
			return token;
		}
		
		public P getTokenParameter() {
			return tokenParameter;
		}
	}

	public static class Complete<T, P extends Parameter> extends ParameterCause<T, P> {

		private final P preParameter;
		
		public Complete(final State<T, P> from, final State<T, P> to, final P preParameter) {
			super(from, to);
			this.preParameter = preParameter;
		}

		@Override
		public NonTerminal<T> getSymbol() {
			return (NonTerminal<T>) getToState().last();
		}

		@Override
		public ParameterExpression<T, P> getParameterExpression() {
			return getToState().lastParameterExpression();
		}
		
		public P getPreParameter() {
			return preParameter;
		}
	}

	public static class Epsilon<T, P extends Parameter> extends ParameterCause<T, P> {

		public Epsilon(final State<T, P> from, final State<T, P> to) {
			super(from, to);
		}

		@Override
		public NonTerminal<T> getSymbol() {
			return (NonTerminal<T>) getFromState().next();
		}

		@Override
		public ParameterExpression<T, P> getParameterExpression() {
			return getFromState().nextParameterExpression();
		}

		public Production<T, P> getProduction() {
			return getToState().getProduction();
		}
	}
}
