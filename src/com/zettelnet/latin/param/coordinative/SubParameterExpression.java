package com.zettelnet.latin.param.coordinative;

import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.latin.param.FormParameter;

public interface SubParameterExpression {

	Map<Object, Set<? extends Property>> predict(FormParameter parameter, FormParameter childParameter);

	Map<Object, Set<? extends Property>> scan(FormParameter parameter, FormParameter childParameter);

	Map<Object, Set<? extends Property>> complete(FormParameter parameter, FormParameter childParameter);
}
