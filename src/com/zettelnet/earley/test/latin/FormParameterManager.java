package com.zettelnet.earley.test.latin;

import com.zettelnet.earley.param.ParameterManager;

public class FormParameterManager implements ParameterManager<FormParameter> {

	@Override
	public FormParameter makeParameter() {
		return new FormParameter();
	}

	@Override
	public FormParameter copyParameter(FormParameter source) {
		return source;
	}

	@Override
	public FormParameter copyParameter(FormParameter source, FormParameter with) {
		return source.deriveWith(with);
	}

	@Override
	public boolean isCompatible(FormParameter parent, FormParameter child) {
		if (parent.isCompatibleWith(child)) {
			System.out.println("parent " + parent + " is compatible with child " + child);
		}
		return parent.isCompatibleWith(child);
	}
}
