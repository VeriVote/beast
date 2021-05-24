package edu.pse.beast.api.codegen.loopbounds;

import java.util.List;
import java.util.Optional;

public class LoopBound {
	private final static String LOOP_BOUND_TEMPLATE = " --unwindset FUNC_NAME.IDX:BOUND ";

	private LoopBound parent;
	private List<LoopBound> children;
	private String functionName;
	private LoopBoundType loopBoundType;
	private int index;
	private int manualBoundIfNeeded;

	public LoopBound(LoopBound parent, List<LoopBound> children,
			String functionName, LoopBoundType loopBoundType, int index) {
		this.parent = parent;
		this.children = children;
		this.functionName = functionName;
		this.loopBoundType = loopBoundType;
		this.index = index;
	}

	public LoopBound(LoopBound parent, List<LoopBound> children,
			String functionName, LoopBoundType loopBoundType, int index,
			int manualBound) {
		this.parent = parent;
		this.children = children;
		this.functionName = functionName;
		this.loopBoundType = loopBoundType;
		this.index = index;
		this.manualBoundIfNeeded = manualBoundIfNeeded;
	}

	public String getUnwindString(int v, int c, int s) {
		int bound;
		switch (loopBoundType) {
			case NECESSARY_LOOP_BOUND_AMT_VOTERS :
				bound = v + 1;
				break;
			case NECESSARY_LOOP_BOUND_AMT_CANDS :
				bound = c + 1;
				break;
			case NECESSARY_LOOP_BOUND_AMT_SEATS :
				bound = s + 1;
				break;
			case MANUALLY_ENTERED_INTEGER :
				bound = manualBoundIfNeeded;
				break;
			default :
				return "";
		}
		String currentUnwindArgument = LOOP_BOUND_TEMPLATE
				.replaceAll("FUNC_NAME", functionName)
				.replaceAll("IDX", String.valueOf(index))
				.replaceAll("BOUND", String.valueOf(bound));

		return currentUnwindArgument;
	}

	public LoopBound getParent() {
		return parent;
	}

	public List<LoopBound> getChildren() {
		return children;
	}

	public String getFunctionName() {
		return functionName;
	}

	public LoopBoundType getLoopBoundType() {
		return loopBoundType;
	}

	public int getIndex() {
		return index;
	}

	public int getManualBoundIfNeeded() {
		return manualBoundIfNeeded;
	}

	public void setParent(LoopBound parent) {
		this.parent = parent;
	}

	public void setChildren(List<LoopBound> children) {
		this.children = children;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void setLoopBoundType(LoopBoundType loopBoundType) {
		this.loopBoundType = loopBoundType;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setManualBoundIfNeeded(int manualBoundIfNeeded) {
		this.manualBoundIfNeeded = manualBoundIfNeeded;
	}

}
