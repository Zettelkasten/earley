package com.zettelnet.latin.lemma.declension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Noun;
import com.zettelnet.latin.morph.MorphProvider;

/**
 * Generates a value of a {@link Form} of a {@link Noun}.
 * <p>
 * <code>form = base + ending</code>
 * <p>
 * The following extra rules apply:
 * <ul>
 * <li>the Nominative Singular is the special {@link Noun#getFirstForm()} form
 * </li>
 * <li>for Accusative Neuter forms, the Nominative form is used</li>
 * <li>Nominative Plural Neuter ends on <code>-a</code></li>
 * <li>the Vocative forms equal the Nominative forms</li>
 * </ul>
 * 
 * @param noun
 * @param form
 * @return
 */
public abstract class AbstractDeclension implements FormProvider<Noun> {

	private final MorphProvider endings;

	public AbstractDeclension(final MorphProvider endings) {
		this.endings = endings;
	}

	protected static Collection<String> concat(final String stem, Collection<String> endings) {
		Collection<String> variants = new ArrayList<>(endings.size());
		for (String ending : endings) {
			StringBuilder str = new StringBuilder();
			str.append(stem);
			if (ending != null) {
				str.append(ending);
			}
			variants.add(str.toString());
		}
		return variants;
	}

	@Override
	public Collection<String> getForm(final Noun noun, final Form form) {
		if (form.hasProperties(Casus.Nominative, Numerus.Singular)) {
			return Arrays.asList(noun.getFirstForm());
		} else if (form.hasProperties(Casus.Accusative, Genus.Neuter)) {
			return getForm(noun, form.derive(Casus.Nominative));
		} else if (form.hasProperties(Casus.Nominative, Numerus.Plural, Genus.Neuter)) {
			return concat(noun.getStem(), getNominativePluralNeuterEnding());
		} else if (form.hasProperties(Casus.Vocative)) {
			return getForm(noun, form.derive(Casus.Nominative));
		} else {
			return concat(noun.getStem(), endings.getMorph(form));
		}
	}

	public abstract Collection<String> getNominativePluralNeuterEnding();

	public Set<Casus> getCasusSet(final Noun noun) {
		return new HashSet<>(Arrays.asList(Casus.Nominative, Casus.Genitive, Casus.Dative, Casus.Accusative, Casus.Ablative, Casus.Vocative));
	}

	public Set<Numerus> getNumerusSet(final Noun noun) {
		return new HashSet<>(Arrays.asList(Numerus.values()));
	}

	public Set<Genus> getGenusSet(final Noun noun) {
		return new HashSet<>(Arrays.asList(noun.getGenus()));
	}

	public boolean hasForm(final Noun noun, final Form form) {
		return getCasusSet(noun).contains(form.getCasus())
				&& getNumerusSet(noun).contains(form.getNumerus())
				&& getGenusSet(noun).contains(form.getGenus());
	}

	@Override
	public Map<Form, Collection<String>> getForms(Noun noun) {
		Map<Form, Collection<String>> forms = new HashMap<>();

		for (Casus casus : getCasusSet(noun)) {
			for (Numerus numerus : getNumerusSet(noun)) {
				for (Genus genus : getGenusSet(noun)) {
					Form form = Form.withValues(casus, numerus, genus);
					forms.put(form, getForm(noun, form));
				}
			}
		}

		return forms;
	}
}
