package com.zettelnet.earley.token;

public interface TokenScanner<T> {

	T makeToken(String content);
}
