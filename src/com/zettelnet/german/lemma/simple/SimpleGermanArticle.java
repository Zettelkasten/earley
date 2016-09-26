package com.zettelnet.german.lemma.simple;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.zettelnet.german.derivation.GermanDerivation;
import com.zettelnet.german.form.GermanCasus;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.MapGermanFormValueProvider;
import com.zettelnet.german.lemma.GermanFormProvider;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.lemma.property.GermanDefiniteness;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;

public class SimpleGermanArticle extends AbstractProvidedGermanLemma {

	private static class DefiniteValues extends MapGermanFormValueProvider<String> {
		public DefiniteValues() {
			super(Arrays.asList(GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE));

			put("der", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("des", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine);
			put("dem", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("den", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine);

			put("die", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("der", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine);
			put("der", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("die", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine);

			put("das", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter);
			put("des", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter);
			put("dem", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter);
			put("das", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter);

			put("die", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("der", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Masculine);
			put("den", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("die", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Masculine);
			put("die", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("der", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Feminine);
			put("den", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("die", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Feminine);
			put("die", GermanCasus.Nominative, GermanNumerus.Plural, GermanGenus.Neuter);
			put("der", GermanCasus.Genitive, GermanNumerus.Plural, GermanGenus.Neuter);
			put("den", GermanCasus.Dative, GermanNumerus.Plural, GermanGenus.Neuter);
			put("die", GermanCasus.Accusative, GermanNumerus.Plural, GermanGenus.Neuter);
		}
	}

	private static class IndefiniteValues extends MapGermanFormValueProvider<String> {
		public IndefiniteValues() {
			super(Arrays.asList(GermanCasus.TYPE, GermanNumerus.TYPE, GermanGenus.TYPE));

			put("ein", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("eines", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Masculine);
			put("einem", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Masculine);
			put("einen", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Masculine);

			put("eine", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("einer", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Feminine);
			put("einer", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Feminine);
			put("eine", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Feminine);

			put("ein", GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Neuter);
			put("eines", GermanCasus.Genitive, GermanNumerus.Singular, GermanGenus.Neuter);
			put("einem", GermanCasus.Dative, GermanNumerus.Singular, GermanGenus.Neuter);
			put("ein", GermanCasus.Accusative, GermanNumerus.Singular, GermanGenus.Neuter);
		}
	}
	public static final GermanLemma DEFINITE_ARTICLE = new SimpleGermanArticle(new ConcreteGermanFormProvider(new DefiniteValues()), GermanDefiniteness.Definite);
	public static final GermanLemma INDEFINITE_ARTICLE = new SimpleGermanArticle(new ConcreteGermanFormProvider(new IndefiniteValues()), GermanDefiniteness.Indefinite);

	public SimpleGermanArticle(GermanFormProvider<GermanLemma> formProvider, GermanLemmaProperty... properties) {
		super(formProvider, properties);
	}

	@Override
	public String getNominalForm() {
		return getForm(GermanForm.withValues(GermanCasus.Nominative, GermanNumerus.Singular, GermanGenus.Masculine)).iterator().next();
	}

	@Override
	public GermanLemmaType getType() {
		return GermanLemmaType.Article;
	}

	@Override
	public Collection<GermanLemma> getDerivation(GermanDerivation derivation) {
		return Collections.emptyList();
	}

	@Override
	public boolean hasDerivation(GermanDerivation derivation) {
		return false;
	}

	@Override
	public Map<GermanDerivation, Collection<GermanLemma>> getDerivations() {
		return Collections.emptyMap();
	}

	@Override
	public boolean isDerivation() {
		return false;
	}

	@Override
	public GermanLemma getDerivedFrom() {
		return null;
	}

	@Override
	public GermanDerivation getDerivationKind() {
		return null;
	}
}
