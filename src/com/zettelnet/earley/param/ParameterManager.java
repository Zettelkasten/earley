package com.zettelnet.earley.param;

public interface ParameterManager<P extends Parameter> extends ParameterFactory<P> {

	P copyParameter(P source);

	P copyParameter(P source, P with);
	
	P scanParameter(P parameter, P tokenParameter);

	boolean isCompatible(P parent, P child);
}
