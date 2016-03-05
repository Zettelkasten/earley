package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.form.Form;

public interface FormProvider<T> {

	Collection<String> getForm(T lemma, Form form);

	boolean hasForm(T lemma, Form form);

	Map<Form, Collection<String>> getForms(T lemma);
}
