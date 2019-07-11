package edu.pse.beast.electionsimulator;

import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;

public class ElectionSimulationData {
	
	public final int voters;
	public final int candidates;
	public final int seats;
	
	public final ResultValueWrapper values;
	
	public ElectionSimulationData(int voters, int candidates, int seats, ResultValueWrapper values) {
		this.voters = voters;
		this.candidates = candidates;
		this.seats = seats;
		
		this.values = values;
	}
}
