package com.zettelnet.earley.process;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

@FunctionalInterface
public interface Processor<T, P extends Parameter, R> {

	R process(ProcessingManager<T, P, R> manager, SyntaxTreeVariant<T, P> tree);
}
