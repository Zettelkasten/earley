package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;

public interface ParameterTranslator<P extends Parameter, Q extends Parameter> {

	Q translateParameter(P parameter);
}
