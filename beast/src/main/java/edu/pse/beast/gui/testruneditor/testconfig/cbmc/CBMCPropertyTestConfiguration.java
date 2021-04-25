package edu.pse.beast.gui.testruneditor.testconfig.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;

public class CBMCPropertyTestConfiguration {
	private int minCands;
	private int minVoters;
	private int minSeats;

	private int maxCands;
	private int maxVoters;
	private int maxSeats;

	private String name;

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	
	private boolean startRunsOnCreation;
	
	private List<CBMCTestRun> runs = new ArrayList<>();
	
	public void addRuns(List<CBMCTestRun> runs) {
		this.runs.addAll(runs);
	}
	
	public int getMinCands() {
		return minCands;
	}
	public int getMinVoters() {
		return minVoters;
	}
	public int getMinSeats() {
		return minSeats;
	}
	public int getMaxCands() {
		return maxCands;
	}
	public int getMaxVoters() {
		return maxVoters;
	}
	public int getMaxSeats() {
		return maxSeats;
	}
	public String getName() {
		return name;
	}
	public CElectionDescription getDescr() {
		return descr;
	}
	public PreAndPostConditionsDescription getPropDescr() {
		return propDescr;
	}
	public void setMinCands(int minCands) {
		this.minCands = minCands;
	}
	public void setMinVoters(int minVoters) {
		this.minVoters = minVoters;
	}
	public void setMinSeats(int minSeats) {
		this.minSeats = minSeats;
	}
	public void setMaxCands(int maxCands) {
		this.maxCands = maxCands;
	}
	public void setMaxVoters(int maxVoters) {
		this.maxVoters = maxVoters;
	}
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescr(CElectionDescription descr) {
		this.descr = descr;
	}
	public void setPropDescr(PreAndPostConditionsDescription propDescr) {
		this.propDescr = propDescr;
	}
	public boolean getStartRunsOnCreation() {
		return startRunsOnCreation;
	}
	public void setStartRunsOnCreation(boolean startRunsOnCreation) {
		this.startRunsOnCreation = startRunsOnCreation;
	}
	public void addRun(CBMCTestRun run) {
		runs.add(run);
	}
	public List<CBMCTestRun> getRuns() {
		return runs;
	}

	public void handleDescrChange() {
		for(CBMCTestRun r : runs) {
			r.handleDescrChange();
		}
	}

}
