package edu.pse.beast.gui;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class PropertyTestRunParameters {
	private int minCands;
	private int minVoters;
	private int minSeats;

	private int maxCands;
	private int maxVoters;
	private int maxSeats;

	private String name;

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	
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

}
