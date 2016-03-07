package com.zettelnet.earley.token;

public interface TokenFactory<T> {

	T makeToken(String content);
}
