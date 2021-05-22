package edu.pse.beast.api.codegen.loopbounds;

import java.util.List;

public class LoopBound {
	private LoopBound parent;
	private List<LoopBound> children;
	private String functionName;
	private LoopBoundType loopBoundType;
	private int index;

	public LoopBound(LoopBound parent, List<LoopBound> children, String functionName,
			LoopBoundType loopBoundType, int index) {
		this.parent = parent;
		this.children = children;
		this.functionName = functionName;
		this.loopBoundType = loopBoundType;
		this.index = index;
	}

}
