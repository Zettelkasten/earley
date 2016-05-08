package com.zettelnet.latin.lemma.declension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.noun.Noun;

/**
 * Generates a value of a {@link Form} of a {@link Noun}.
 * <p>
 * <code>form = base + ending</code>
 * <p>
 * The following extra rules apply:
 * <ul>
 * <li>the Nominative Singular is the special {@link Noun#getNominalForm()} form
 * </li>
 * <li>for Accusative Neuter forms, the Nominative form is used</li>
 * <li>Nominative Plural Neuter ends on <code>-a</code></li>
 * <li>the Vocative forms equal the Nominative forms</li>
 * </ul>
 */
public abstract class AbstractDeclension implements FormProvider<DeclinableLemma> {

	private final FormValueProvider<String> endings;

	public AbstractDeclension(final FormValueProvider<String> endings) {
		this.endings = endings;
	}

	protected static Collection<String> concat(final String stem, Collection<String> endings) {
		Collection<String> variants = new ArrayList<>(endings.size());
		for (String ending : endings) {
			StringBuilder str = new StringBuilder();
			str.append(stem);
			str.append(ending);
			variants.add(str.toString());
		}

		return variants;
	}

	@Override
	public Collection<String> getForm(final DeclinableLemma lemma, final Form form) {
		if (form.hasProperties(Casus.Nominative, Numerus.Singular)) {
			return Arrays.asList(lemma.getFirstForm(form.getGenus()));
		} else if (form.hasProperties(Casus.Accusative, Genus.Neuter)) {
			return getForm(lemma, form.derive(Casus.Nominative));
		} else if (form.hasProperties(Casus.Nominative, Numerus.Plural, Genus.Neuter)) {
			return concat(lemma.getStem(), getNominativePluralNeuterEnding());
		} else if (form.hasProperties(Casus.Vocative)) {
			return getForm(lemma, form.derive(Casus.Nominative));
		} else {
			return concat(lemma.getStem(), endings.getValue(form));
		}
	}

	public abstract Collection<String> getNominativePluralNeuterEnding();

	public Set<Casus> getCasusSet(final DeclinableLemma lemma) {
		return EnumSet.of(Casus.Nominative, Casus.Genitive, Casus.Dative, Casus.Accusative, Casus.Ablative, Casus.Vocative);
	}

	public Set<Numerus> getNumerusSet(final DeclinableLemma lemma) {
		return EnumSet.of(Numerus.Singular, Numerus.Plural);
	}

	public Set<Genus> getGenusSet(final DeclinableLemma lemma) {
		return lemma.getGenus();
	}

	public boolean hasForm(final DeclinableLemma lemma, final Form form) {
		return getCasusSet(lemma).contains(form.getCasus())
				&& getNumerusSet(lemma).contains(form.getNumerus())
				&& getGenusSet(lemma).contains(form.getGenus());
	}

	@Override
	public Map<Form, Collection<String>> getForms(DeclinableLemma lemma) {
		Map<Form, Collection<String>> forms = new HashMap<>();

		for (Casus casus : getCasusSet(lemma)) {
			for (Numerus numerus : getNumerusSet(lemma)) {
				for (Genus genus : getGenusSet(lemma)) {
					Form form = Form.withValues(casus, numerus, genus);
					Collection<String> variants = getForm(lemma, form);

					if (!variants.isEmpty()) {
						forms.put(form, variants);
					}
				}
			}
		}

		return forms;
	}
}
