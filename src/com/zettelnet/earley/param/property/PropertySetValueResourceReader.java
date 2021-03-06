package com.zettelnet.earley.param.property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PropertySetValueResourceReader<T extends PropertySet<?>> implements PropertySetValueResource<T> {

	public static final char COMMENT = '#';
	public static final char SECTION_HEADER = '$';

	public static final String ASSIGN = "=";
	public static final String ENUMERATION = "\\s+,\\s+";
	public static final char QUOTATION = '\"';

	private final PropertySetParser<T> keyParser;

	private final Map<String, Map<T, Collection<String>>> data;

	public PropertySetValueResourceReader(final PropertySetParser<T> keyParser, final String fileName) throws IOException {
		this(keyParser, new Scanner(PropertySetValueResourceReader.class.getResourceAsStream(fileName)));
	}

	public PropertySetValueResourceReader(final PropertySetParser<T> keyParser, final Scanner scanner) throws IOException {
		this.keyParser = keyParser;
		this.data = new HashMap<>();

		Map<T, Collection<String>> currentSection = createSection(null);

		int lineNumber = 0;
		while (scanner.hasNextLine()) {
			lineNumber++;
			String line = scanner.nextLine();

			if (line.isEmpty()) {
				// ignore empty lines
			} else {
				char firstChar = line.charAt(0);
				switch (firstChar) {
				case COMMENT:
					// ignored
					break;
				case SECTION_HEADER:
					currentSection = createSection(line.substring(1).trim());
					break;
				default:
					// entry
					String[] splitAssign = line.split(ASSIGN, 2);
					if (splitAssign.length != 2) {
						throw new IOException(String.format("Missing assignment operator (%s) in entry on line %s", ASSIGN, lineNumber));
					}
					String keyRaw = splitAssign[0].trim();
					String valueRaw = splitAssign[1].trim();

					T key;
					try {
						key = parseKey(keyRaw);
					} catch (Exception e) {
						throw new IOException(String.format("Malformed key (%s) in entry on line %s", keyRaw, lineNumber), e);
					}

					Collection<String> value;
					try {
						value = parseValue(valueRaw);
					} catch (Exception e) {
						throw new IOException(String.format("Malformed value (%s) in entry on line %s", valueRaw, lineNumber), e);
					}

					currentSection.put(key, value);
					break;
				}
			}
		}
	}

	private Map<T, Collection<String>> createSection(String sectionKey) {
		if (data.containsKey(sectionKey)) {
			return data.get(sectionKey);
		} else {
			Map<T, Collection<String>> section = new HashMap<>();
			data.put(sectionKey, section);
			return section;
		}
	}

	private T parseKey(String raw) throws IOException {
		return keyParser.parse(raw);
	}

	private Collection<String> parseValue(String raw) throws IOException {
		String[] rawTokens = raw.split(ENUMERATION);

		List<String> list = new ArrayList<>();

		for (String rawToken : rawTokens) {
			if (rawToken.length() < 2 || rawToken.charAt(0) != QUOTATION || rawToken.charAt(rawToken.length() - 1) != QUOTATION) {
				throw new IOException(String.format("Missing quotation marks surrounding token %s", rawToken));
			} else {
				list.add(rawToken.substring(1, rawToken.length() - 1));
			}
		}

		return list;
	}

	@Override
	public Collection<String> getValue(String section, T key) {
		return data.get(section).get(key);
	}

	@Override
	public Map<T, Collection<String>> getSection(String section) {
		return data.get(section);
	}

	@Override
	public Map<String, Map<T, Collection<String>>> getSections() {
		return data;
	}

	@Override
	public boolean containsSection(String section) {
		return data.containsKey(section);
	}
}
