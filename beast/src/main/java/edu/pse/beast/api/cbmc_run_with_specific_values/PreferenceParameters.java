package edu.pse.beast.api.cbmc_run_with_specific_values;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

public class PreferenceParameters implements VotingParameters {
    private static final String VOTE_VAR_NAME = "VOTE_VAR_NAME";

    private final String votingDeclTemplate = "VOTE_STRUCT_TYPE " + VOTE_VAR_NAME + ";";
    private final String amtVotesTemplate = VOTE_VAR_NAME + ".AMT_MEMBER = AMT_VOTERS;";
    private final String votesPerVoterTemplate =
            VOTE_VAR_NAME + ".LIST_MEMBER[LIST_INDEX][CAND_INDEX] = VOTE_GIVEN;";

    private int v;
    private int c;
    private int s;

    private List<List<Integer>> votesPerVoter = new ArrayList<>();

    public PreferenceParameters(final int candidateAmount) {
        this.c = candidateAmount;
    }

    public final void addVoter(final List<Integer> preferenceVotes) {
        votesPerVoter.add(preferenceVotes);
        this.v++;
    }

    @Override
    public final String genVoteStructInitCode(final ElectionTypeCStruct voteInputStruct,
                                              final CodeGenOptions options,
                                              final CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo,
                                              final String generatedVarName) {
        final List<String> code = new ArrayList<>();

        final String votingStructName = voteInputStruct.getStruct().getName();
        final String votingStructDeclString =
                votingDeclTemplate
                .replaceAll("VOTE_STRUCT_TYPE", votingStructName)
                .replaceAll(VOTE_VAR_NAME, generatedVarName);
        code.add(votingStructDeclString);

        final String amtDeclString =
                amtVotesTemplate
                .replaceAll(VOTE_VAR_NAME, generatedVarName)
                .replaceAll("AMT_MEMBER", voteInputStruct.getAmtName())
                .replaceAll("AMT_VOTERS", String.valueOf(v));
        code.add(amtDeclString);

        for (int i = 0; i < votesPerVoter.size(); ++i) {
            for (int j = 0; j < c; ++j) {
                final String voteString =
                        votesPerVoterTemplate
                        .replaceAll(VOTE_VAR_NAME, generatedVarName)
                        .replaceAll("LIST_MEMBER",
                                    voteInputStruct.getListName())
                        .replaceAll("LIST_INDEX", String.valueOf(i))
                        .replaceAll("CAND_INDEX", String.valueOf(j))
                        .replaceAll("VOTE_GIVEN",
                                    String.valueOf(votesPerVoter.get(i).get(j)));
                code.add(voteString);
            }
        }

        return String.join("\n", code);
    }

    @Override
    public final int getV() {
        return v;
    }

    @Override
    public final int getC() {
        return c;
    }

    @Override
    public final int getS() {
        return s;
    }

    @Override
    public final int getHighestVote() {
        return 1;
    }
}
