package com.zettelnet.earley.test.latin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.earley.param.property.PropertySets;
import com.zettelnet.earley.token.TokenFactory;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.Lemma;

public class DeterminationRegistry implements TokenFactory<Token> {

	private final Map<String, Collection<Determination>> formsStripped;

	public DeterminationRegistry() {
		this.formsStripped = new HashMap<>();
	}

	public static String strip(String content) {
		return content.replaceAll("_", "").toLowerCase();
	}

	public void register(Lemma lemma) {
		for (Entry<Form, Collection<String>> entry : lemma.getForms().entrySet()) {
			PropertySet<?> properties = PropertySets.derive(entry.getKey(), lemma.getProperties());
			for (String value : entry.getValue()) {
				register(value, new Determination(lemma, properties));
			}
		}

		for (Collection<Lemma> collection : lemma.getDerivations().values()) {
			for (Lemma derivation : collection) {
				register(derivation);
			}
		}
	}

	public void register(String value, Determination determination) {
		String key = strip(value);
		if (!formsStripped.containsKey(key)) {
			formsStripped.put(key, new ArrayList<>());
		}
		formsStripped.get(key).add(determination);
	}

	public Collection<Determination> getDeterminations(String content) {
		String key = strip(content);
		if (!formsStripped.containsKey(key)) {
			return Collections.emptyList();
		} else {
			return formsStripped.get(key);
		}
	}

	public int getSize() {
		return formsStripped.size();
	}

	@Override
	public Token makeToken(String content) {
		return new Token(content, getDeterminations(content));
	}
}
