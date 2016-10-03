package com.zettelnet.latin.param.coordinative;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.lemma.property.SubjunctionType;
import com.zettelnet.latin.param.FormParameter;

public class SubjunctionMoodPropertyExpression implements SubParameterExpression {

	private Map<Object, Set<? extends Property>> handle(FormParameter parameter, FormParameter childParameter) {
		Set<Property> moods = new HashSet<>();

		if (parameter.getProperty(SubjunctionType.TYPE) == null) {
			return Collections.emptyMap();
		}

		for (Property property : parameter.getProperty(SubjunctionType.TYPE)) {
			SubjunctionType moodProperty = (SubjunctionType) property;
			moods.add(moodProperty.getMood());
		}

		Map<Object, Set<? extends Property>> map = new HashMap<>();
		map.put(Mood.TYPE, moods);
		return map;
	}

	@Override
	public Map<Object, Set<? extends Property>> predict(FormParameter parameter, FormParameter childParameter) {
		return handle(parameter, childParameter);
	}

	@Override
	public Map<Object, Set<? extends Property>> complete(FormParameter parameter, FormParameter childParameter) {
		return handle(parameter, childParameter);
	}

	@Override
	public String toString() {
		return "smood[&pi;]";
	}
}
