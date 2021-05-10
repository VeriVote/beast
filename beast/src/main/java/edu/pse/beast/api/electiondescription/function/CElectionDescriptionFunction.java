package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CElectionDescriptionFunction {
	private String name;
	private List<String> code = new ArrayList<>();

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

	public String getDeclCString() {
		return "";
	}

}
