package edu.pse.beast.gui.testconfigeditor.testconfig.cbmc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;

public class CBMCTestConfiguration {
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
	
	private String uuid;
	
	public CBMCTestConfiguration() {
		uuid = UUID.randomUUID().toString();
	}
	
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
		if(maxCands < minCands) {
			maxCands = minCands;
		}
	}
	public void setMaxCands(int maxCands) {
		this.maxCands = maxCands;
		if(maxCands < minCands) {
			minCands = maxCands;
		}
	}
	
	public void setMinVoters(int minVoters) {
		this.minVoters = minVoters;
		if(maxVoters < minVoters) {
			maxVoters = minVoters;
		}
	}
	public void setMaxVoters(int maxVoters) {
		this.maxVoters = maxVoters;
		if(maxVoters < minVoters) {
			minVoters = maxVoters;
		}
	}
	
	public void setMinSeats(int minSeats) {
		this.minSeats = minSeats;
		if(maxSeats < minSeats) {
			maxSeats = minSeats;
		}
	}
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
		if(maxSeats < minSeats) {
			minSeats = maxSeats;
		}
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

	public void handleDescrCodeChange() {
		for(CBMCTestRun r : runs) {
			r.handleDescrCodeChange();
		}
	}

	public void handlePropDescrChanged() {
		for(CBMCTestRun r : runs) {
			r.handlePropDescrChanged();
		}
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void deleteRun(CBMCTestRun run) {
		runs.remove(run);
	}
	
}
