package com.zettelnet.latin.lemma.simple.declension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
public class FifthDeclension extends AbstractDeclension {

	private static class Endings extends MapFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(Casus.TYPE, Numerus.TYPE));

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

	public static final FormValueProvider<String> ENDINGS = new Endings();

	public FifthDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Collections.emptyList();
	}
}
