package com.zettelnet.latin.param;

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
	public FormParameter scanParameter(FormParameter parameter, FormParameter tokenParameter) {
		return parameter.scanWith(tokenParameter);
	}

	@Override
	public boolean isCompatible(FormParameter parent, FormParameter child) {
		return parent.isCompatibleWith(child);
	}
}
