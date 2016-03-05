package com.zettelnet.earley.param.property;

public interface PropertySet<T> {

	<U extends T> boolean hasProperty(Class<U> property);

	<U extends T> T getProperty(Class<U> property);

}
