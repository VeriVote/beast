package edu.pse.beast.api.codegen.loopbounds;

public class LoopBound {
	private String name;
	private String bound;
	private int index;

	public LoopBound(String name, String bound) {
		super();
		this.name = name;
		this.bound = bound;
	}

	public LoopBound(String functionName, int loopIndex, String bound) {
		this.index = loopIndex;
		this.name = functionName + ":" + loopIndex;
		this.bound = bound;
	}

	public String getName() {
		return name;
	}

	public String getBound() {
		return bound;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return name + ":" + bound;
	}

	public String getCBMCString() {
		return "--unwindset " + name + ":" + bound;
	}

}
