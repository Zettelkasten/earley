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
public class FourthDeclension extends AbstractDeclension {

	private static class Endings extends MapMorphProvider {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("_us", Casus.Genitive, Numerus.Singular);
			put("u_i", Casus.Dative, Numerus.Singular);
			put("um", Casus.Accusative, Numerus.Singular);
			put("_u", Casus.Ablative, Numerus.Singular);
			put("_i", Casus.Locative, Numerus.Singular);

			put("_us", Casus.Nominative, Numerus.Plural);
			put("uum", Casus.Genitive, Numerus.Plural);
			put("ibus", Casus.Dative, Numerus.Plural);
			put("_us", Casus.Accusative, Numerus.Plural);
			put("ibus", Casus.Ablative, Numerus.Plural);
		}
	}

	public static final MorphProvider ENDINGS = new Endings();

	public FourthDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList("ua");
	}
}
