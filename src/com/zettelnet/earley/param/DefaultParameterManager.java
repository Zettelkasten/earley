package com.zettelnet.earley.param;

public class DefaultParameterManager implements ParameterManager<DefaultParameter> {

	@Override
	public DefaultParameter makeParameter() {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public DefaultParameter copyParameter(DefaultParameter origin) {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public DefaultParameter copyParameter(DefaultParameter origin, DefaultParameter with) {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public boolean isCompatible(DefaultParameter parent, DefaultParameter child) {
		// no state -> always compatible
		return true;
	}
}
