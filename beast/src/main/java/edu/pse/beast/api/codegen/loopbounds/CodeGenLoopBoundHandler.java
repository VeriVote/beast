package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;

public class CodeGenLoopBoundHandler {

	private Map<String, List<LoopBound>> functionNamesToLoopbounds = new HashMap<>();
	private List<LoopBound> mainLoopbounds = new ArrayList<>();
	private Map<String, List<LoopBound>> votingInitLoopbounds = new HashMap<>();
	private Map<String, List<LoopBound>> votingBackLoopbounds = new HashMap<>();

	public void addLoopBound(LoopBound b) {
		String funcName = b.getFunctionName();
		if (!functionNamesToLoopbounds.containsKey(funcName)) {
			functionNamesToLoopbounds.put(funcName, new ArrayList<>());
		}
		functionNamesToLoopbounds.get(funcName).add(b);
	}

	public List<LoopBound> getLoopBoundsAsList() {
		List<LoopBound> loopbounds = new ArrayList<>();
		for (List<LoopBound> lbl : functionNamesToLoopbounds.values()) {
			loopbounds.addAll(lbl);
		}
		return loopbounds;
	}

	public void pushMainLoopBounds(List<LoopBound> loopbounds) {
		mainLoopbounds.addAll(loopbounds);
	}

	public void addVotingInitLoopBounds(String votingFunctionName,
			List<LoopBound> loopbounds) {
		votingInitLoopbounds.put(votingFunctionName, loopbounds);
	}

	public void pushVotingLoopBounds(String votingFunctionName,
			List<LoopBound> loopbounds) {
		votingBackLoopbounds.put(votingFunctionName, loopbounds);
	}

	public void finishAddedLoopbounds() {
		for (String k : votingInitLoopbounds.keySet()) {
			List<LoopBound> initLbs = votingInitLoopbounds.get(k);
			for (int i = 0; i < initLbs.size(); ++i) {
				initLbs.get(i).setIndex(i);
				initLbs.get(i).setFunctionName(k);
			}

			List<LoopBound> extractedLbs = functionNamesToLoopbounds.get(k);
			int amtInitLoops = votingInitLoopbounds.get(k).size();
			for (LoopBound lb : extractedLbs) {
				lb.setFunctionName(k);
				lb.incrementIndexBy(amtInitLoops);
			}
			int amtLoopboundsInFunc = votingInitLoopbounds.get(k).size()
					+ extractedLbs.size();
			for (int i = 0; i < votingBackLoopbounds.get(k).size(); ++i) {
				LoopBound lb = votingBackLoopbounds.get(k).get(i);
				lb.setIndex(i + amtLoopboundsInFunc);
				lb.setFunctionName(k);
			}
		}
	}

	public String generateCBMCString(int v, int c, int s) {

		String created = "";

		for (int i = 0; i < mainLoopbounds.size(); ++i) {
			LoopBound lb = mainLoopbounds.get(i);
			lb.setFunctionName("main");
			lb.setIndex(i);
			created += lb.getUnwindString(v, c, s);
		}

		for (String k : functionNamesToLoopbounds.keySet()) {
			for (LoopBound lb : votingInitLoopbounds.get(k)) {
				created += lb.getUnwindString(v, c, s);
			}
			for (LoopBound lb : functionNamesToLoopbounds.get(k)) {
				created += lb.getUnwindString(v, c, s);
			}
			for (LoopBound lb : votingBackLoopbounds.get(k)) {
				created += lb.getUnwindString(v, c, s);
			}
		}

		created += " --unwind " + (Math.max(Math.max(v, c), s) + 1);

		return created;
	}

}
