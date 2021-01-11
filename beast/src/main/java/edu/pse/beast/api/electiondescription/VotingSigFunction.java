package edu.pse.beast.api.electiondescription;

import java.util.List;

public class VotingSigFunction {	
	private String name;
	private List<String> code;
	
	private String createdName;
	private int returnLine;
	
	public VotingSigFunction(String name, List<String> code, String createdName, int returnLine) {
		this.name = name;
		this.code = code;
		this.createdName = createdName;
		this.returnLine = returnLine;
	}
	
	
}
