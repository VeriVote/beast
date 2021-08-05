package edu.pse.beast.api.codegen.cbmc.generated_code_info;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class InformationStringBuilder {
    private static final String INFO_STRING = "These are the ";
    private static final String FOR_STRING = " for vote number ";
    private static final String ASSIGNMENTS = "assignments";
    private static final String RESULTS = "results";

    private static final String VOTE_NUMBER = "VOTE_NUMBER";

    private static String infoString(final String type) {
        return INFO_STRING + type + FOR_STRING + VOTE_NUMBER;
    }

    public static String genForVote(final int voteNumber, final String varName) {
        return infoString(ASSIGNMENTS).replaceAll(VOTE_NUMBER, String.valueOf(voteNumber));
    }

    public static String genForResult(final int electNumber, final String varName) {
        return infoString(RESULTS).replaceAll(VOTE_NUMBER, String.valueOf(electNumber));
    }

}
