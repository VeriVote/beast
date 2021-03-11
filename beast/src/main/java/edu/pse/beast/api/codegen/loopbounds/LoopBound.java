package edu.pse.beast.api.codegen.loopbounds;

public class LoopBound {
	private String name;
	private String bound;

	public LoopBound(String name, String bound) {
		super();
		this.name = name;
		this.bound = bound;
	}

	public String getName() {
		return name;
	}

	public String getBound() {
		return bound;
	}

	public String getCBMCString() {
		return "--unwindset " + name + ":" + bound;
	}

}
