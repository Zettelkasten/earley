package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.form.Form;

/**
 * Generates a value of a {@link Form} of a {@link Noun}.
 * <p>
 * <code>form = base + ending</code>
 * <p>
 * The following extra rules apply:
 * <ul>
 * <li>the Nominative Singular is the special {@link Noun#getFirstForm()} form
 * </li>
 * <li>for Accusative Neuter forms, the Nominative form is used</li>
 * <li>Nominative Plural Neuter ends on <code>-a</code></li>
 * <li>the Vocative forms equal the Nominative forms</li>
 * </ul>
 * 
 * @param noun
 * @param form
 * @return
 */
public interface FormProvider<T> {

	Collection<String> getForm(T lemma, Form form);

	boolean hasForm(T lemma, Form form);

	Map<Form, Collection<String>> getForms(T lemma);
}
