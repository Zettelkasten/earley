package com.zettelnet.earley.symbol;

public interface Terminal<T> extends Symbol<T> {

	boolean isCompatibleWith(T token);
}
