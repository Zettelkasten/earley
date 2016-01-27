package com.zettelnet.earley;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;

public class Chart<T, P extends Parameter> implements Iterable<State<T, P>> {

	private final InputPosition<T> inputPosition;
	private final Queue<State<T, P>> toCompute;
	// value and key are always equal
	// Map is used instead of Set provide method for getting an element that
	// equals another
	private final Map<State<T, P>, State<T, P>> states;

	public Chart(final InputPosition<T> inputPosition) {
		this.inputPosition = inputPosition;
		this.toCompute = new LinkedList<>();
		this.states = new LinkedHashMap<>();
	}

	public InputPosition<T> getInputPosition() {
		return inputPosition;
	}

	public State<T, P> add(State<T, P> state) {
		if (!states.containsKey(state)) {
			toCompute.add(state);
			states.put(state, state);
			return state;
		} else {
			return states.get(state);
		}
	}

	public State<T, P> add(State<T, P> state, StateCause<T, P> cause) {
		state = add(state);
		state.addCause(cause);
		return state;
	}

	public State<T, P> poll() {
		return toCompute.poll();
	}

	@Override
	public Iterator<State<T, P>> iterator() {
		return states.keySet().iterator();
	}
	
	public Iterable<State<T, P>> concurrentIterable() {
		return new ArrayList<>(states.keySet());
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("S(");
		str.append(inputPosition);
		str.append(")");

		return str.toString();
	}
}
