package com.zettelnet.earley.param.property;

import java.io.PrintStream;

public interface PropertySetWriter<T> {

	void print(PrintStream out, T propertySet);
}
