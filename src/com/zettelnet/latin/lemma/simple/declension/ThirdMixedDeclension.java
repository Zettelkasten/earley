package com.zettelnet.latin.lemma.simple.declension;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Numerus;

/**
 * Represents the <strong>third declension</strong>, also known as <strong>mixed
 * declination</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class ThirdMixedDeclension extends AbstractDeclension {

	private static class Endings extends MapFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(Casus.TYPE, Numerus.TYPE));

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

	public static final FormValueProvider<String> ENDINGS = new Endings();

	public ThirdMixedDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList("a");
	}
}
