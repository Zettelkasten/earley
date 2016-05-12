package com.zettelnet.german.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.german.form.GermanForm;

public interface GermanLemma {

	String getNominalForm();

	Collection<String> getForm(GermanForm form);

	boolean hasForm(GermanForm form);

	Map<GermanForm, Collection<String>> getForms();

	GermanLemmaType getType();

	/*
	 * TODO
	 * 
	 * Collection<GermanLemma> getDerivation(GermanDerivation derivation);
	 * 
	 * boolean hasDerivation(GermanDerivation derivation);
	 * 
	 * Map<GermanDerivation, Collection<GermanLemma>> getDerivations();
	 * 
	 * PropertySet<GermanLemmaProperty> getProperties();
	 * 
	 * 
	 * boolean isDerivation();
	 * 
	 * GermanLemma getDerivedFrom();
	 * 
	 * GermanDerivation getDerivationKind();
	 */
}
