package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class VoteOrElectTypeAssignment {
	private String name;
	private String member;
	private String value;

	public VoteOrElectTypeAssignment(String name, String member, String value) {
		this.name = name;
		this.member = member;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getMember() {
		return member;
	}

	public String getValue() {
		return value;
	}

}
