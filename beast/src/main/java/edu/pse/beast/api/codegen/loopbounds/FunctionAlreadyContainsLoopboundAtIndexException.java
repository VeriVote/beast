package edu.pse.beast.api.codegen.loopbounds;

public class FunctionAlreadyContainsLoopboundAtIndexException
		extends
			Exception {
	private LoopBound lb;

	public FunctionAlreadyContainsLoopboundAtIndexException(LoopBound lb) {
		this.lb = lb;
	}

	public LoopBound getLb() {
		return lb;
	}
}
