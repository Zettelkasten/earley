package com.zettelnet.earley.param.property;

import java.io.IOException;

public interface PropertySetParser<T extends PropertySet<?>> {

	T parse(String raw) throws IOException;
}
