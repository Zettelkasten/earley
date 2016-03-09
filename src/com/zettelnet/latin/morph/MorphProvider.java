package com.zettelnet.latin.morph;

import java.util.Collection;

public interface MorphProvider<T> {

	Collection<String> getMorph(final T key);

}
