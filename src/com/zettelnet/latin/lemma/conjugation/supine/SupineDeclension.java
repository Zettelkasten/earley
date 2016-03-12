package com.zettelnet.latin.lemma.conjugation.supine;

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

public class SupineDeclension extends AbstractDeclension {

	private static class Endings extends MapFormValueProvider<String> {
		public Endings() {
			super(Arrays.asList(Casus.class, Numerus.class));

			put("um", Casus.Accusative, Numerus.Singular);
			put("_u", Casus.Ablative, Numerus.Singular);
		}
	}

	public static final FormValueProvider<String> ENDINGS = new Endings();

	public SupineDeclension() {
		super(ENDINGS);
	}

	@Override
	public Collection<String> getNominativePluralNeuterEnding() {
		return Arrays.asList();
	}

	@Override
	public Collection<String> getForm(DeclinableLemma lemma, Form form) {
		// supines are declined as if they were masculine
		return super.getForm(lemma, form.derive(Genus.Masculine));
	}
	
	@Override
	public Set<Casus> getCasusSet(final DeclinableLemma lemma) {
		return EnumSet.of(Casus.Accusative, Casus.Ablative);
	}

	@Override
	public Set<Numerus> getNumerusSet(final DeclinableLemma lemma) {
		return EnumSet.of(Numerus.Singular);
	}

}
