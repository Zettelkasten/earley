package com.zettelnet.earley.param.property;

import java.util.Collection;
import java.util.Map;

public interface PropertySetValueResource<T> {

	Collection<String> getValue(String section, T key);

	Map<T, Collection<String>> getSection(String section);

	Map<String, Map<T, Collection<String>>> getSections();

	boolean containsSection(String section);
}
