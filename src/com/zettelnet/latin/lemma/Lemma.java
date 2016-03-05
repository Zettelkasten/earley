package com.zettelnet.latin.lemma;

import java.util.Map;

import com.zettelnet.latin.form.Form;

public interface Lemma {

	String getFirstForm();

	String getForm(Form form);

	boolean hasForm(Form form);

	Map<Form, String> getForms();

	LemmaType getType();
}
