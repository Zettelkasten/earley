package com.zettelnet.latin.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public final class BaseForm implements Form {

	private static final int casusLen = Casus.values().length + 1;
	private static final int numerusLen = Numerus.values().length + 1;
	private static final int genusLen = Genus.values().length + 1;
	private static final int personLen = Person.values().length + 1;
	private static final int moodLen = Mood.values().length + 1;
	private static final int tenseLen = Tense.values().length + 1;
	private static final int voiceLen = Voice.values().length + 1;
	private static final int comparisonLen = Comparison.values().length + 1;
	private static final int verbTypeLen = Finiteness.values().length + 1;

	private static final int cacheLen = casusLen * numerusLen * genusLen * personLen * moodLen * tenseLen * voiceLen * comparisonLen * verbTypeLen;
	private static final Form[] cache = new Form[cacheLen];

	public static Form valueOf(Casus casus, Numerus numerus, Genus genus, Person person, Mood mood, Tense tense, Voice voice, Comparison comparison, Finiteness verbType) {
		int hashCode = hashCode(casus, numerus, genus, person, mood, tense, voice, comparison, verbType);

		if (cache[hashCode] == null) {
			cache[hashCode] = new BaseForm(casus, numerus, genus, person, mood, tense, voice, comparison, verbType, hashCode);
		}
		return cache[hashCode];
	}

	public static int hashCode(Casus casus, Numerus numerus, Genus genus, Person person, Mood mood, Tense tense, Voice voice, Comparison comparison, Finiteness verbType) {
		int i = 0;

		i *= casusLen;
		i += hashCode(casus);

		i *= numerusLen;
		i += hashCode(numerus);

		i *= genusLen;
		i += hashCode(genus);

		i *= personLen;
		i += hashCode(person);

		i *= moodLen;
		i += hashCode(mood);

		i *= tenseLen;
		i += hashCode(tense);

		i *= voiceLen;
		i += hashCode(voice);

		i *= comparisonLen;
		i += hashCode(comparison);

		i *= verbTypeLen;
		i += hashCode(verbType);

		return i;
	}

	public static int hashCode(FormProperty property) {
		if (property == null) {
			return 0;
		} else {
			return property.ordinal() + 1;
		}
	}

	private final Casus casus;
	private final Numerus numerus;
	private final Genus genus;
	private final Person person;
	private final Mood mood;
	private final Tense tense;
	private final Voice voice;
	private final Comparison comparison;
	private final Finiteness verbType;

	private final int hashCode;

	private BaseForm(Casus casus, Numerus numerus, Genus genus, Person person, Mood mood, Tense tense, Voice voice, Comparison comparison, Finiteness verbType, int hashCode) {
		this.casus = casus;
		this.numerus = numerus;
		this.genus = genus;
		this.person = person;
		this.mood = mood;
		this.tense = tense;
		this.voice = voice;
		this.comparison = comparison;
		this.verbType = verbType;

		this.hashCode = hashCode;
	}

	@Override
	public Casus getCasus() {
		return casus;
	}

	@Override
	public Numerus getNumerus() {
		return numerus;
	}

	@Override
	public Genus getGenus() {
		return genus;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public Mood getMood() {
		return mood;
	}

	@Override
	public Tense getTense() {
		return tense;
	}

	@Override
	public Voice getVoice() {
		return voice;
	}

	@Override
	public Comparison getComparison() {
		return comparison;
	}

	@Override
	public Finiteness getVerbType() {
		return verbType;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public <T extends FormProperty> boolean hasProperty(Class<T> property) {
		return getProperty(property) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends FormProperty> T getProperty(Class<T> property) {
		if (property == Casus.class) {
			return (T) casus;
		} else if (property == Numerus.class) {
			return (T) numerus;
		} else if (property == Genus.class) {
			return (T) genus;
		} else if (property == Person.class) {
			return (T) person;
		} else if (property == Mood.class) {
			return (T) mood;
		} else if (property == Tense.class) {
			return (T) tense;
		} else if (property == Voice.class) {
			return (T) voice;
		} else if (property == Comparison.class) {
			return (T) comparison;
		} else if (property == Finiteness.class) {
			return (T) verbType;
		} else {
			return null;
		}
	}
	
	@Override
	public Form retainAll(@SuppressWarnings("unchecked") Class<? extends FormProperty>... properties) {
		Collection<Class<? extends FormProperty>> props = new HashSet<>(Arrays.asList(properties));
		
		Casus casus = props.contains(Casus.class) ? this.casus : null;
		Numerus numerus = props.contains(Numerus.class) ? this.numerus : null;
		Genus genus = props.contains(Genus.class) ? this.genus: null;
		Person person = props.contains(Person.class) ? this.person : null;
		Mood mood = props.contains(Mood.class) ? this.mood : null;
		Tense tense = props.contains(Tense.class) ? this.tense : null;
		Voice voice = props.contains(Voice.class) ? this.voice : null;
		Comparison comparison = props.contains(Comparison.class) ? this.comparison : null;
		Finiteness verbType = props.contains(Finiteness.class) ? this.verbType : null;
		
		return valueOf(casus, numerus, genus, person, mood, tense, voice, comparison, verbType);
	}

	@Override
	public String toStringShort() {
		StringBuilder str = new StringBuilder();

		if (casus != null) {
			str.append(casus.shortName());
			str.append(" ");
		}
		if (numerus != null) {
			str.append(numerus.shortName());
			str.append(" ");
		}
		if (genus != null) {
			str.append(genus.shortName());
			str.append(" ");
		}
		if (person != null) {
			str.append(person.shortName());
			str.append(" ");
		}
		if (mood != null) {
			str.append(mood.shortName());
			str.append(" ");
		}
		if (tense != null) {
			str.append(tense.shortName());
			str.append(" ");
		}
		if (voice != null) {
			str.append(voice.shortName());
			str.append(" ");
		}
		if (comparison != null) {
			str.append(comparison.shortName());
			str.append(" ");
		}
		if (verbType != null) {
			str.append(verbType.shortName());
			str.append(" ");
		}

		if (str.length() != 0) {
			str.deleteCharAt(str.length() - 1);
		}

		return str.toString();
	}
}
