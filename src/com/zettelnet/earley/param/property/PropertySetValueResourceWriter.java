package com.zettelnet.earley.param.property;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PropertySetValueResourceWriter<T extends PropertySet<?>> {

	private final PropertySetWriter<T> keyWriter;

	private final PrintStream out;

	private boolean sortSections;

	public PropertySetValueResourceWriter(final PropertySetWriter<T> keyWriter, final PrintStream out) {
		this(keyWriter, out, true);
	}

	public PropertySetValueResourceWriter(final PropertySetWriter<T> keyWriter, final PrintStream out, boolean sortSections) {
		this.keyWriter = keyWriter;
		this.out = out;
		this.sortSections = sortSections;
	}

	public void printResource(final PropertySetValueResource<T> resource) {
		out.println(String.format("%c Auto generated %s", PropertySetValueResourceReader.COMMENT, new Date()));
		out.println();
		
		// null section key is treated extra
		if (resource.containsSection(null)) {
			printSectionValues(resource.getSection(null));
		}

		for (Map.Entry<String, Map<T, Collection<String>>> entry : resource.getSections().entrySet()) {
			String sectionKey = entry.getKey();

			if (sectionKey != null) {
				out.print(PropertySetValueResourceReader.SECTION_HEADER);
				out.print(sectionKey);
				out.println();

				printSectionValues(entry.getValue());
			}
		}
	}

	private void printSectionValues(Map<T, Collection<String>> sectionValues) {
		if (sortSections) {
			sectionValues = new TreeMap<>(sectionValues);
		}

		for (Map.Entry<T, Collection<String>> entry : sectionValues.entrySet()) {
			T key = entry.getKey();
			keyWriter.print(out, key);

			out.print(PropertySetValueResourceReader.ASSIGN);

			Collection<String> value = entry.getValue();

			for (Iterator<String> i = value.iterator(); i.hasNext();) {
				String str = i.next();
				out.print(PropertySetValueResourceReader.QUOTATION);
				out.print(str);
				out.print(PropertySetValueResourceReader.QUOTATION);

				if (i.hasNext()) {
					out.print(',');
				}
			}

			out.println();
		}
	}
}
