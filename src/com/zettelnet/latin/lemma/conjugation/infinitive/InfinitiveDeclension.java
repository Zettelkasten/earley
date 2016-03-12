package com.zettelnet.latin.lemma.conjugation.infinitive;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.declension.AbstractDeclension;

/**
 * Represents the <strong>second declension</strong>, also known as
 * <strong>o-declension</strong>, <strong>modified to suit the declension of
 * infinitives / gerunds</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class InfinitiveDeclension extends AbstractDeclension {

	private static class Endings extends MapFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("_i", Casus.Genitive, Numerus.Singular);
			put("_o", Casus.Dative, Numerus.Singular);
			put("um", Casus.Accusative, Numerus.Singular);
		}
	}

	public static final FormValueProvider<String> ENDINGS = new Endings();

	public InfinitiveDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList();
	}

	@Override
	public Collection<String> getForm(DeclinableLemma lemma, Form form) {
		// gerunds are declined as if they were masculine
		return super.getForm(lemma, form.derive(Genus.Masculine));
	}
	
	@Override
	public Set<Casus> getCasusSet(final DeclinableLemma lemma) {
		return EnumSet.of(Casus.Nominative, Casus.Genitive, Casus.Dative, Casus.Accusative);
	}

	@Override
	public Set<Numerus> getNumerusSet(final DeclinableLemma lemma) {
		return EnumSet.of(Numerus.Singular);
	}

}
