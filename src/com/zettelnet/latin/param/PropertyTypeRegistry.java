package com.zettelnet.latin.param;

import java.util.Set;

import com.zettelnet.earley.symbol.Symbol;

public interface PropertyTypeRegistry<T> {

	Set<Object> getPropertyTypes(Symbol<T> symbol);
}
