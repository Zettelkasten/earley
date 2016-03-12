package com.zettelnet.latin.lemma.declension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.MapFormProvider;
import com.zettelnet.latin.form.Numerus;

/**
 * Represents the <strong>first declension</strong>, also known as
 * <strong>a-declension</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class FirstDeclension extends AbstractDeclension {

	private static class Endings extends MapFormProvider<String> {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("ae", Casus.Genitive, Numerus.Singular);
			put("ae", Casus.Dative, Numerus.Singular);
			put("am", Casus.Accusative, Numerus.Singular);
			put("_a", Casus.Ablative, Numerus.Singular);

			put("ae", Casus.Nominative, Numerus.Plural);
			put("_arum", Casus.Genitive, Numerus.Plural);
			put("_is", Casus.Dative, Numerus.Plural);
			put("_as", Casus.Accusative, Numerus.Plural);
			put("_is", Casus.Ablative, Numerus.Plural);
		}
	}

	public static final FormValueProvider<String> ENDINGS = new Endings();

	public FirstDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Collections.emptyList();
	}
}
