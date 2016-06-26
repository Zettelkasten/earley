package com.zettelnet.latin.param;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.symbol.Symbol;

public class SimplePropertyTypeRegistry<T> implements PropertyTypeRegistry<T> {

	private final Map<Symbol<T>, Set<Object>> data;

	public SimplePropertyTypeRegistry() {
		this.data = new HashMap<>();
	}

	public SimplePropertyTypeRegistry<T> register(Symbol<T> symbol, Set<Object> types) {
		data.put(symbol, types);
		return this;
	}

	public SimplePropertyTypeRegistry<T> register(Symbol<T> symbol, Object... types) {
		return register(symbol, new HashSet<>(Arrays.asList(types)));
	}

	@Override
	public Set<Object> getPropertyTypes(Symbol<T> symbol) {
		return data.get(symbol);
	}
}
