package com.zettelnet.earley.token;

import java.util.List;

public interface Tokenizer<T> {

	List<T> tokenize(String raw);
}
