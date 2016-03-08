package com.zettelnet.earley.process;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;

public class ProcessableProduction<T, P extends Parameter, R> {

	private final Production<T, P> production;
	private final Processor<T, P, R> processor;

	public ProcessableProduction(final Production<T, P> production, final Processor<T, P, R> processor) {
		this.production = production;
		this.processor = processor;
	}

	public Production<T, P> getProduction() {
		return production;
	}

	public Processor<T, P, R> getProcessor() {
		return processor;
	}
}
