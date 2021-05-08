package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;

public class LoopBound {
	private LoopBoundType loopBoundType;
	private int index;
	private String functionName;

	public LoopBound(LoopBoundType loopBoundType, int index,
			String functionName) {
		this.loopBoundType = loopBoundType;
		this.index = index;
		this.functionName = functionName;
	}

	public LoopBound(LoopBoundType loopBoundType, String functionName) {
		super();
		this.loopBoundType = loopBoundType;
		this.functionName = functionName;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static List<LoopBound> functionLoopBounds(String funcName,
			List<LoopBoundType> types) {
		List<LoopBound> bounds = new ArrayList<>();

		for (LoopBoundType type : types) {
			bounds.add(new LoopBound(type, funcName));
		}

		return bounds;
	}

}
