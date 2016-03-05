package com.zettelnet.latin.lemma.declension;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.Noun;
import com.zettelnet.latin.morph.MapMorphProvider;
import com.zettelnet.latin.morph.MorphProvider;

/**
 * Represents the <strong>second declension</strong>, also known as
 * <strong>o-declension</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class SecondDeclension extends AbstractDeclension {

	private static class Endings extends MapMorphProvider {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("_i", Casus.Genitive, Numerus.Singular);
			put("_o", Casus.Dative, Numerus.Singular);
			put("um", Casus.Accusative, Numerus.Singular);
			put("_o", Casus.Ablative, Numerus.Singular);
			put("_i", Casus.Locative, Numerus.Singular);

			put("_i", Casus.Nominative, Numerus.Plural);
			put("_orum", Casus.Genitive, Numerus.Plural);
			put("_is", Casus.Dative, Numerus.Plural);
			put("_os", Casus.Accusative, Numerus.Plural);
			put("_is", Casus.Ablative, Numerus.Plural);
		}
	}

	public static final MorphProvider ENDINGS = new Endings();

	public SecondDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList("a");
	}

	@Override
	public Collection<String> getForm(Noun noun, Form form) {
		// nouns ending on "-us" end on "-e" in Voc Sg
		if (form.hasProperties(Casus.Vocative, Numerus.Singular) && noun.getFirstForm().endsWith("us")) {
			return concat(noun.getStem(), Arrays.asList("e"));
		} else {
			return super.getForm(noun, form);
		}
	}
}
