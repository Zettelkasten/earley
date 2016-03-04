package com.zettelnet.earley;

import com.zettelnet.earley.param.Parameter;

public interface StateCause<T, P extends Parameter> {

	State<T, P> getPreState();

	public class Predict<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> parent;

		public Predict(final State<T, P> parentState) {
			this.parent = parentState;
		}

		public State<T, P> getParentState() {
			return parent;
		}

		public boolean isInitial() {
			return parent instanceof SeedState;
		}

		@Override
		public State<T, P> getPreState() {
			return null;
		}
	}

	public class Scan<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> pre;

		private final T token;
		private final P parameter;

		public Scan(final State<T, P> preState, final T scannedToken, final P scannedTokenParameter) {
			this.pre = preState;
			this.token = scannedToken;
			this.parameter = scannedTokenParameter;
		}

		public State<T, P> getPreState() {
			return pre;
		}

		public T getToken() {
			return token;
		}

		public P getTokenParameter() {
			return parameter;
		}
	}

	public class Complete<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> child;
		private final State<T, P> pre;

		public Complete(final State<T, P> childState, final State<T, P> preState) {
			this.child = childState;
			this.pre = preState;
		}

		public State<T, P> getChildState() {
			return child;
		}

		public State<T, P> getPreState() {
			return pre;
		}
	}

	public class Epsilon<T, P extends Parameter> implements StateCause<T, P> {

		private final State<T, P> pre;

		public Epsilon(final State<T, P> preState) {
			this.pre = preState;
		}

		public State<T, P> getPreState() {
			return pre;
		}
	}
}
