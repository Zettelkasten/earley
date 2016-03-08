package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public interface Lemma {

	String getFirstForm();

	Collection<String> getForm(Form form);

	boolean hasForm(Form form);

	Map<Form, Collection<String>> getForms();

	PropertySet<LemmaProperty> getProperties();

	LemmaType getType();
}
