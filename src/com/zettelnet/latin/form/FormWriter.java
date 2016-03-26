package com.zettelnet.latin.form;

import java.io.PrintStream;

import com.zettelnet.earley.param.property.PropertySetWriter;

public class FormWriter implements PropertySetWriter<Form> {
	
	public static final FormWriter INSTANCE = new FormWriter();

	@Override
	public void print(final PrintStream out, final Form form) {
		out.print(form.toString());
	}
}
