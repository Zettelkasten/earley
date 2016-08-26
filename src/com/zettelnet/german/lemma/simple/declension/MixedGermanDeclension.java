package com.zettelnet.german.lemma.simple.declension;

import java.util.Arrays;

import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.MapGermanFormValueProvider;

public class MixedGermanDeclension extends AbstractGermanDeclension {

	private static class Endings extends MapGermanFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE));

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine);
			put(new String[] { "s", "es" }, GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine);
			put("", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Masculine);

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter);
			put(new String[] { "s", "es" }, GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter);
			put("", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter);
			put("", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter);

			put("en", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Neuter);
			put("en", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Neuter);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Neuter);
			put("en", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter);
		}
	}

	public static final GermanFormValueProvider<String> ENDINGS = new Endings();

	public MixedGermanDeclension() {
		super(ENDINGS);
	}
}
