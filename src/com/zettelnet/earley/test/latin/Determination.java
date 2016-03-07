package com.zettelnet.earley.test.latin;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;

public class Determination {

	private final Lemma lemma;
	private final Form form;

	public Determination(final Lemma lemma, final FormProperty... formProperties) {
		this(lemma, Form.withValues(formProperties));
	}
	
	public Determination(final Lemma lemma, final Form form) {
		this.lemma = lemma;
		this.form = form;
	}

	public Lemma getLemma() {
		return lemma;
	}

	public LemmaType getLemmaType() {
		return lemma.getType();
	}

	public Form getForm() {
		return form;
	}

	@Override
	public String toString() {
		return "Determination [" + lemma + ", " + form + "]";
	}
}
