package com.zettelnet.german.lemma.simple.declension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.lemma.GermanFormProvider;

public abstract class AbstractGermanDeclension implements GermanFormProvider<DeclinableGermanLemma> {

	private final GermanFormValueProvider<String> endings;

	public AbstractGermanDeclension(final GermanFormValueProvider<String> endings) {
		this.endings = endings;
	}

	protected static Collection<String> concat(final String stem, Collection<String> endings) {
		Collection<String> variants = new ArrayList<>(endings.size());
		for (String ending : endings) {
			StringBuilder str = new StringBuilder();
			str.append(stem);

			int equalCharacterCount = countDuplicateCharacters(stem, ending);

			str.append(ending.substring(equalCharacterCount));
			variants.add(str.toString());
		}

		return variants;
	}

	protected static int countDuplicateCharacters(final String stem, final String ending) {
		for (int length = Math.min(stem.length(), ending.length()); length > 0; length--) {
			boolean equalCharacters = true;
			for (int i = 0; i < length && equalCharacters; i++) {
				if (stem.charAt(stem.length() - length + i) != ending.charAt(i)) {
					equalCharacters = false;
				}
			}
			if (equalCharacters) {
				return length;
			}
		}
		return 0;
	}

	@Override
	public Collection<String> getForm(final DeclinableGermanLemma lemma, final GermanForm form) {
		if (form.hasProperties(GermanCasus.Genitive, GermanNumerus.Singular)) {
			return Arrays.asList(lemma.getGenitiveSingular());
		} else {
			String stem;
			if (form.hasProperties(GermanNumerus.Singular)) {
				stem = lemma.getNominativeSingular();
			} else if (form.hasProperties(GermanNumerus.Plural)) {
				stem = lemma.getNominativePlural();
			} else {
				throw new AssertionError("Unknown numerus");
			}

			return concat(stem, endings.getValue(form));
		}
	}

	public Set<GermanCasus> getCasusSet(final DeclinableGermanLemma lemma) {
		return EnumSet.of(GermanCasus.Nominative, GermanCasus.Genitive, GermanCasus.Dative, GermanCasus.Accusative);
	}

	public Set<GermanNumerus> getNumerusSet(final DeclinableGermanLemma lemma) {
		return EnumSet.of(GermanNumerus.Singular, GermanNumerus.Plural);
	}

	public boolean hasForm(final DeclinableGermanLemma lemma, final GermanForm form) {
		return getCasusSet(lemma).contains(form.getCasus())
				&& getNumerusSet(lemma).contains(form.getNumerus())
				&& form.getGenus() == lemma.getGenus();
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms(DeclinableGermanLemma lemma) {
		Map<GermanForm, Collection<String>> forms = new HashMap<>();

		for (GermanCasus casus : getCasusSet(lemma)) {
			for (GermanNumerus numerus : getNumerusSet(lemma)) {
				GermanGenus genus = lemma.getGenus();
				GermanForm form = GermanForm.withValues(casus, numerus, genus);
				Collection<String> variants = getForm(lemma, form);

				if (!variants.isEmpty()) {
					forms.put(form, variants);
				}
			}
		}

		return forms;
	}
}
