package com.zettelnet.german.derivation;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanFormProperty;

public class SimpleGermanDerivation implements GermanDerivation {

	public static GermanDerivation withValues(GermanDerivationType type, GermanForm form) {
		return new SimpleGermanDerivation(type, form);
	}

	public static GermanDerivation withValues(GermanDerivationType type, GermanFormProperty... formProperties) {
		return new SimpleGermanDerivation(type, GermanForm.withValues(formProperties));
	}

	private final GermanDerivationType type;
	private final GermanForm form;

	public SimpleGermanDerivation(final GermanDerivationType type, final GermanForm form) {
		this.type = type;
		this.form = form;
	}

	@Override
	public GermanDerivationType getType() {
		return type;
	}

	@Override
	public GermanForm getForm() {
		return form;
	}

	@Override
	public String toString() {
		return type + " " + form;
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
		SimpleGermanDerivation other = (SimpleGermanDerivation) obj;
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
