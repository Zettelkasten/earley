package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.form.Form;

public interface Lemma {

	String getFirstForm();

	Collection<String> getForm(Form form);

	boolean hasForm(Form form);

	Map<Form, Collection<String>> getForms();

	LemmaType getType();
}
