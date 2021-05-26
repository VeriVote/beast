package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class VoteTypeAssignment {
	private String name;
	private String member;
	private int value;

	public VoteTypeAssignment(String name, String member, int value) {
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

	public int getValue() {
		return value;
	}

}
