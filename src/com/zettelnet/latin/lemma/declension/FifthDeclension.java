package com.zettelnet.latin.lemma.declension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
public class FifthDeclension extends AbstractDeclension {

	private static class Endings extends MapMorphProvider {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put(".e_i", Casus.Genitive, Numerus.Singular);
			put(".e_i", Casus.Dative, Numerus.Singular);
			put("em", Casus.Accusative, Numerus.Singular);
			put("_e", Casus.Ablative, Numerus.Singular);

			put("_es", Casus.Nominative, Numerus.Plural);
			put("_erum", Casus.Genitive, Numerus.Plural);
			put("_ebus", Casus.Dative, Numerus.Plural);
			put("_es", Casus.Accusative, Numerus.Plural);
			put("_ebus", Casus.Ablative, Numerus.Plural);
		}
	}

	public static final MorphProvider ENDINGS = new Endings();

	public FifthDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Collections.emptyList();
	}
}