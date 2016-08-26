package com.zettelnet.german.lemma.simple.declension;

import java.util.Arrays;

import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.MapGermanFormValueProvider;

public class WeakGermanDeclension extends AbstractGermanDeclension {

	private static class Endings extends MapGermanFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE));

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("en", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine);
			put("en", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("en", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Masculine);

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine);
			put("", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Feminine);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Feminine);
		}
	}

	public static final GermanFormValueProvider<String> ENDINGS = new Endings();

	public WeakGermanDeclension() {
		super(ENDINGS);
	}
}
