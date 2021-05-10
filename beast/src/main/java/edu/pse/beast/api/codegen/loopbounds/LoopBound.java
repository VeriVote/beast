package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.pse.beast.api.codegen.CodeGenOptions;

public class LoopBound {
	private LoopBoundType loopBoundType;
	private int index;
	private String functionName;
	private Optional<Integer> manualLoopBoundIfPresent = Optional.empty();

	public LoopBound(LoopBoundType loopBoundType, int index,
			String functionName, Optional<Integer> manualLoopBoundIfPresent) {
		this.loopBoundType = loopBoundType;
		this.index = index;
		this.functionName = functionName;
		this.manualLoopBoundIfPresent = manualLoopBoundIfPresent;
	}

	public LoopBound(LoopBoundType loopBoundType, int index,
			String functionName) {
		this.loopBoundType = loopBoundType;
		this.index = index;
		this.functionName = functionName;
	}

	public LoopBound(LoopBoundType loopBoundType, String functionName) {
		this.loopBoundType = loopBoundType;
		this.functionName = functionName;
	}

	public static List<LoopBound> functionLoopBounds(String funcName,
			List<LoopBoundType> types) {
		List<LoopBound> bounds = new ArrayList<>();

		for (LoopBoundType type : types) {
			bounds.add(new LoopBound(type, funcName));
		}

		return bounds;
	}

	public LoopBoundType getLoopBoundType() {
		return loopBoundType;
	}

	public int getIndex() {
		return index;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setLoopBoundType(LoopBoundType loopBoundType) {
		this.loopBoundType = loopBoundType;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

}
