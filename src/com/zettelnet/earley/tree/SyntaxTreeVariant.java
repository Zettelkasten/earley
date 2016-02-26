package com.zettelnet.earley.tree;

import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;

public interface SyntaxTreeVariant<T, P extends Parameter> {

	Production<T, P> getProduction();
	
	P getParameter();

	List<SyntaxTree<T, P>> getChildren();
}
