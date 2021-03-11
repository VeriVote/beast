package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.List;

public class LoopBoundHandler {

	private List<String> mainFuncLoopBounds = new ArrayList<>();
	private List<String> votingFuncLoopBounds = new ArrayList<>();
	
	public void addMainLoopBound(String sizeOfBound) {
		mainFuncLoopBounds.add(sizeOfBound);
	}

	public void addMainLoopBounds(List<String> bounds) {
		mainFuncLoopBounds.addAll(bounds);
	}

	public List<LoopBound> getMainLoopBounds() {
		List<LoopBound> created = new ArrayList<>();
		for (int i = 0; i < mainFuncLoopBounds.size(); ++i) {
			final String nameTemplate = "main.NUMBER";
			LoopBound bound = new LoopBound(
					nameTemplate.replace("NUMBER", String.valueOf(i)),
					mainFuncLoopBounds.get(i));
			created.add(bound);
		}
		return created;
	}

	public void addVotingLoopBounds(List<String> bounds) {
		votingFuncLoopBounds.addAll(bounds);
	}

}
