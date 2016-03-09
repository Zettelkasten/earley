package com.zettelnet.latin.derivation;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public class SimpleDerivation implements Derivation {

	public static Derivation withValues(DerivationType type, FormProperty... formProperties) {
		return new SimpleDerivation(type, Form.withValues(formProperties));
	}

	private final DerivationType type;
	private final Form form;

	public SimpleDerivation(final DerivationType type, final Form form) {
		this.type = type;
		this.form = form;
	}

	@Override
	public DerivationType getType() {
		return type;
	}

	@Override
	public Form getForm() {
		return form;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((form == null) ? 0 : form.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleDerivation other = (SimpleDerivation) obj;
		if (form == null) {
			if (other.form != null)
				return false;
		} else if (!form.equals(other.form))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
