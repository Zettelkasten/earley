package com.zettelnet.earley.print;

import java.io.PrintStream;

public interface TokenPrinter<T> {

	void print(PrintStream out, T token);
}
