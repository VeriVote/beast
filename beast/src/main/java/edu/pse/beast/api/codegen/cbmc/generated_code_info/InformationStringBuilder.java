package edu.pse.beast.api.codegen.cbmc.generated_code_info;

public class InformationStringBuilder {

	public static String genForVote(int voteNumber, String varName) {
		String template = "These are the assignments for vote number VOTE_NUMBER";
		return template.replaceAll("VOTE_NUMBER", String.valueOf(voteNumber));
	}

	public static String genForResult(int electNumber, String varName) {
		String template = "These are the results of vote number VOTE_NUMBER";
		return template.replaceAll("VOTE_NUMBER", String.valueOf(electNumber));
	}

}
