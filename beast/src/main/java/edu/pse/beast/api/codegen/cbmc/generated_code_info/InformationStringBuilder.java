package edu.pse.beast.api.codegen.cbmc.generated_code_info;

public class InformationStringBuilder {

    public static String genForVote(final int voteNumber, final String varName) {
        final String template = "These are the assignments for vote number VOTE_NUMBER";
        return template.replaceAll("VOTE_NUMBER", String.valueOf(voteNumber));
    }

    public static String genForResult(final int electNumber, final String varName) {
        final String template = "These are the results of vote number VOTE_NUMBER";
        return template.replaceAll("VOTE_NUMBER", String.valueOf(electNumber));
    }

}
