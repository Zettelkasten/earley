package com.zettelnet.german.lemma.simple.declension;

import java.util.Arrays;

import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.MapGermanFormValueProvider;

public class StrongGermanDeclension extends AbstractGermanDeclension {

	private static class Endings extends MapGermanFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE));

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine);
			put(new String[] { "s", "es" }, GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine);
			put(new String[] { "", "e" }, GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine);

			put("e", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("e", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("e", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Masculine);

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine);
			put("", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine);

			put("", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Feminine);
			put("n", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Feminine);

			put("", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter);
			put(new String[] { "s", "es" }, GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter);
			put(new String[] { "", "e" }, GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter);
			put("", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter);

			put("e", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Neuter);
			put("e", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Neuter);
			put("en", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Neuter);
			put("e", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter);
		}
	}

	public static final GermanFormValueProvider<String> ENDINGS = new Endings();

	public StrongGermanDeclension() {
		super(ENDINGS);
	}
}
