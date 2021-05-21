package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.c_parser.ExtractedCLoop;


public abstract class CElectionDescriptionFunction {
	protected String name;
	protected List<String> code = new ArrayList<>();
	protected List<ExtractedCLoop> extractedLoops = new ArrayList<>();
	
	public CElectionDescriptionFunction(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return String.join("\n", code);
	}

	public void setCode(String code) {
		this.code = Arrays.asList(code.split("\n"));
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getCodeAsList() {
		return code;
	}

	public abstract String getDeclCString();
	
	@Override
	public String toString() {
		return name;
	}
	
	public List<ExtractedCLoop> getExtractedLoops() {
		return extractedLoops;
	}
	
	public void setExtractedLoops(List<ExtractedCLoop> extractedLoops) {
		this.extractedLoops = extractedLoops;
	}

}
