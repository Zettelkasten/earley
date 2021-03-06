package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public interface Lemma {

	String getNominalForm();

	Collection<String> getForm(Form form);

	boolean hasForm(Form form);

	Map<Form, Collection<String>> getForms();
	
	Collection<Lemma> getDerivation(Derivation derivation);
	
	boolean hasDerivation(Derivation derivation);
	
	Map<Derivation, Collection<Lemma>> getDerivations();

	PropertySet<LemmaProperty> getProperties();

	LemmaType getType();
	
	boolean isDerivation();
	
	Lemma getDerivedFrom();
	
	Derivation getDerivationKind();
}
