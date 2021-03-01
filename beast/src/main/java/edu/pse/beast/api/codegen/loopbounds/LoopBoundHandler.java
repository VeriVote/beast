package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.List;

public class LoopBoundHandler {
	
	private List<String> mainFuncLoopBounds = new ArrayList<>();

	public void addMainLoopBound(String sizeOfBound) {
		mainFuncLoopBounds.add(sizeOfBound);
	}

	public void addMainLoopBounds(List<String> bounds) {
		mainFuncLoopBounds.addAll(bounds);
	}
	
}
