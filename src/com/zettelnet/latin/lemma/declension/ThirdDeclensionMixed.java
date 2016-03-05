package com.zettelnet.latin.lemma.declension;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.morph.MapMorphProvider;
import com.zettelnet.latin.morph.MorphProvider;

/**
 * Represents the <strong>third declension</strong>, also known as <strong>mixed
 * declination</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class ThirdDeclensionMixed extends AbstractDeclension {

	private static class Endings extends MapMorphProvider {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("is", Casus.Genitive, Numerus.Singular);
			put("_i", Casus.Dative, Numerus.Singular);
			put("em", Casus.Accusative, Numerus.Singular);
			put("e", Casus.Ablative, Numerus.Singular);

			put("_es", Casus.Nominative, Numerus.Plural);
			put("ium", Casus.Genitive, Numerus.Plural);
			put("ibus", Casus.Dative, Numerus.Plural);
			put("_es", Casus.Accusative, Numerus.Plural);
			put("ibus", Casus.Ablative, Numerus.Plural);
		}
	}

	public static final MorphProvider ENDINGS = new Endings();

	public ThirdDeclensionMixed() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList("a");
	}
}
