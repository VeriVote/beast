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

	public List<LoopBound> getMainLoopBounds(
			String amtVoters, 
			String amtCandidates, 
			String amtSeats) {
		List<LoopBound> created = new ArrayList<>();
		for (int i = 0; i < mainFuncLoopBounds.size(); ++i) {
			final String nameTemplate = "main.NUMBER";
			String bound = null;
			if(mainFuncLoopBounds.get(i).equals("AMT_VOTERS")) {
				bound = amtVoters;
			} else if(mainFuncLoopBounds.get(i).equals("AMT_CANDIDATES")) {
				bound = amtCandidates;
			} else if(mainFuncLoopBounds.get(i).equals("AMT_SEATS")) {
				bound = amtSeats;
			}
			LoopBound loopBound = 
					new LoopBound(
						nameTemplate.replace(
								"NUMBER", String.valueOf(i)),
						bound);
			created.add(loopBound);
		}
		return created;
	}

	public void addVotingLoopBounds(List<String> bounds) {
		votingFuncLoopBounds.addAll(bounds);
	}
	
	public List<LoopBound> getVotingLoopBounds(
			String amtVoters, 
			String amtCandidates, 
			String amtSeats) {
		List<LoopBound> created = new ArrayList<>();
		for (int i = 0; i < votingFuncLoopBounds.size(); ++i) {
			final String nameTemplate = "main.NUMBER";
			String bound = null;
			if(votingFuncLoopBounds.get(i).equals("AMT_VOTERS")) {
				bound = amtVoters;
			} else if(votingFuncLoopBounds.get(i).equals("AMT_CANDIDATES")) {
				bound = amtCandidates;
			} else if(votingFuncLoopBounds.get(i).equals("AMT_SEATS")) {
				bound = amtSeats;
			}
			LoopBound loopBound = 
					new LoopBound(
						nameTemplate.replace(
								"NUMBER", String.valueOf(i)),
						bound);
			created.add(loopBound);
		}
		return created;
	}

}
