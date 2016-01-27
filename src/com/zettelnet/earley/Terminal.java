package com.zettelnet.earley;

public interface Terminal<T> extends Symbol<T> {

	boolean isCompatibleWith(T token);
}
