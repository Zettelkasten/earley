package com.zettelnet.latin.form;

import java.io.PrintStream;
import java.util.Iterator;

import com.zettelnet.earley.param.property.PropertySetWriter;

public class FormWriter implements PropertySetWriter<Form> {
	
	public static final FormWriter INSTANCE = new FormWriter();

	@Override
	public void print(final PrintStream out, final Form form) {
		for (Iterator<FormProperty> i = form.values().iterator(); i.hasNext();) {
			FormProperty property = i.next();
			out.print(property.name());
			if (i.hasNext()) {
				out.print(' ');
			}
		}
	}
}
