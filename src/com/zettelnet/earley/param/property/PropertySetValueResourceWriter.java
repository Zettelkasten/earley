package com.zettelnet.earley.param.property;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

public class PropertySetValueResourceWriter<T extends PropertySet<?>> {

	private final PropertySetWriter<T> keyWriter;

	private final PrintStream out;

	public PropertySetValueResourceWriter(final PropertySetWriter<T> keyWriter, final PrintStream out) {
		this.keyWriter = keyWriter;
		this.out = out;
	}

	public void printResource(final PropertySetValueResource<T> resource) {
		// null section key is treated extra
		if (resource.containsSection(null)) {
			printSectionValues(resource.getSection(null));
		}

		for (Map.Entry<String, Map<T, Collection<String>>> entry : resource.getSections().entrySet()) {
			String sectionKey = entry.getKey();

			if (sectionKey != null) {
				out.print(PropertySetValueResource.SECTION_HEADER);
				out.print(sectionKey);
				out.println();

				printSectionValues(entry.getValue());
			}
		}
	}

	private void printSectionValues(final Map<T, Collection<String>> sectionValues) {
		for (Map.Entry<T, Collection<String>> entry : sectionValues.entrySet()) {
			T key = entry.getKey();
			keyWriter.print(out, key);

			out.print(PropertySetValueResource.ASSIGN);

			Collection<String> value = entry.getValue();
			if (value.size() == 1) {
				String singleValue = value.iterator().next();
				out.print(singleValue);
			}

			out.println();
		}
	}
}
