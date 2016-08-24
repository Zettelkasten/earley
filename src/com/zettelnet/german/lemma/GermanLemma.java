package com.zettelnet.german.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;

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
	 * 
	 * 
	 * 
	 * boolean isDerivation();
	 * 
	 * GermanLemma getDerivedFrom();
	 * 
	 * GermanDerivation getDerivationKind();
	 */

	PropertySet<GermanLemmaProperty> getProperties();
}
