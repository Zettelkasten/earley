package com.zettelnet.latin.lemma.declension;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.morph.MapMorphProvider;
import com.zettelnet.latin.morph.MorphProvider;

/**
 * Represents the <strong>third declension</strong>, also known as
 * <strong>consonantal declension</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class ThirdIStemDeclension extends AbstractDeclension {

	private static class Endings extends MapMorphProvider {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("is", Casus.Genitive, Numerus.Singular);
			put("_i", Casus.Dative, Numerus.Singular);
			put(new String[] { "em", "im" }, Casus.Accusative, Numerus.Singular);
			put(new String[] { "e", "_i" }, Casus.Ablative, Numerus.Singular);

			put("_es", Casus.Nominative, Numerus.Plural);
			put("ium", Casus.Genitive, Numerus.Plural);
			put("ibus", Casus.Dative, Numerus.Plural);
			put(new String[] { "_es", "_is" }, Casus.Accusative, Numerus.Plural);
			put("ibus", Casus.Ablative, Numerus.Plural);
		}
	}

	public static final MorphProvider ENDINGS = new Endings();

	public ThirdIStemDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList("ia");
	}
}
