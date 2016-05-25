package com.zettelnet.latin.lemma.simple.declension;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Numerus;

/**
 * Represents the <strong>second declension</strong>, also known as
 * <strong>o-declension</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class SecondDeclension extends AbstractDeclension {

	private static class Endings extends MapFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(Casus.TYPE, Numerus.TYPE));

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

	public static final FormValueProvider<String> ENDINGS = new Endings();

	public SecondDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList("a");
	}

	@Override
	public Collection<String> getForm(DeclinableLemma lemma, Form form) {
		// nouns ending on "-us" end on "-e" in Voc Sg
		if (form.hasProperties(Casus.Vocative, Numerus.Singular, Genus.Masculine) && lemma.getNominalForm().endsWith("us")) {
			return concat(lemma.getStem(), Arrays.asList("e"));
		} else {
			return super.getForm(lemma, form);
		}
	}
}
