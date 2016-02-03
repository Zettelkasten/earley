package com.zettelnet.earley;

import com.zettelnet.earley.param.Parameter;

public interface StateCause<T, P extends Parameter> {

	public class Predict<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> from;

		public Predict(final State<T, P> fromState) {
			this.from = fromState;
		}

		public State<T, P> getFromState() {
			return from;
		}

		public boolean isInitial() {
			return from instanceof SeedState;
		}
	}

	public class Scan<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> fromState;
		private final T withToken;

		public Scan(final State<T, P> fromState, final T withToken) {
			this.fromState = fromState;
			this.withToken = withToken;
		}

		public State<T, P> getFromState() {
			return fromState;
		}
		
		public T getWithToken() {
			return withToken;
		}
	}

	public class Complete<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> fromState;
		private final State<T, P> withState;

		public Complete(final State<T, P> fromState, final State<T, P> withState) {
			this.fromState = fromState;
			this.withState = withState;
		}

		public State<T, P> getFromState() {
			return fromState;
		}

		public State<T, P> getWithState() {
			return withState;
		}
	}
}
