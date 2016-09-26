package com.zettelnet.german.lemma.simple.declension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanDeterminerType;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.MapGermanFormValueProvider;
import com.zettelnet.german.lemma.GermanFormProvider;

public class AdjectiveGermanDeclension implements GermanFormProvider<GermanAdjective> {

	private static class Endings extends MapGermanFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE, GermanDeterminerType.TYPE));

			// strong declension
			
			put("er", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);
			put("em", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);
			put("er", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.WithoutArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);
			put("er", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);
			put("er", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);
			put("er", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.WithoutArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);

			put("es", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);
			put("em", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);
			put("es", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);
			put("er", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.WithoutArticle);

			// weak declension
			
			put("e", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.DefiniteArticle);

			// mixed declension
			
			put("er", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);

			put("e", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);
			put("e", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Feminine, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);

			put("es", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
			put("es", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter, GermanDeterminerType.IndefiniteArticle);
		}
	}

	public static final GermanFormValueProvider<String> ENDINGS = new Endings();

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
	public Collection<String> getForm(GermanAdjective lemma, GermanForm form) {
		String stem = lemma.getStem();
		return concat(stem, ENDINGS.getValue(form));
	}

	@Override
	public boolean hasForm(GermanAdjective lemma, GermanForm form) {
		return !getForm(lemma, form).isEmpty();
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms(GermanAdjective lemma) {
		final Map<GermanForm, Collection<String>> forms = new HashMap<>();
		
		for (GermanDeterminerType determinerType : GermanDeterminerType.values()) {
			for (GermanCasus casus : GermanCasus.values()) {
				for (GermanNumerus numerus : GermanNumerus.values()) {
					for (GermanGenus genus : GermanGenus.values()) {
						GermanForm form = GermanForm.withValues(determinerType, casus, numerus, genus);
						Collection<String> variants = getForm(lemma, form);
						
						if (!variants.isEmpty()) {
							forms.put(form, variants);
						}
					}
				}
			}
		}
		
		return forms;
	}
}
