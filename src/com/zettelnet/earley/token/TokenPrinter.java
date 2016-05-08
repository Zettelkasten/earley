package com.zettelnet.earley.token;

import java.io.PrintStream;

public interface TokenPrinter<T> {

	void print(PrintStream out, T token);
}
