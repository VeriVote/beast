package edu.kit.kastel.formal.beast.api.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PreferenceParameters implements VotingParameters {
    private static final String DECL_KEY = "DECLARATION";
    private static final String INIT_KEY = "INITIALIZATION";

    private static final String EMPTY = "";
    private static final String LINKE_BREAK = "\n";

    private static final String VOTE_STRUCT_TYPE = "VOTE_STRUCT_TYPE";
    private static final String VOTE_VAR_NAME = "VOTE_VAR_NAME";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String AMOUNT_VOTERS = "AMT_VOTERS";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String LIST_INDEX = "LIST_INDEX";
    private static final String CAND_INDEX = "CAND_INDEX";
    private static final String VOTE_GIVEN = "VOTE_GIVEN";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private int v;
    private int c;
    private int s;

    private List<List<Integer>> votesPerVoter = new ArrayList<List<Integer>>();

    public PreferenceParameters(final int candidateAmount) {
        this.c = candidateAmount;
    }

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    public final void addVoter(final List<Integer> preferenceVotes) {
        votesPerVoter.add(preferenceVotes);
        this.v++;
    }

    @Override
    public final String genVoteStructInitCode(final ElectionTypeCStruct voteInputStruct,
                                              final CodeGenOptions options,
                                              final GeneratedCodeInfo cbmcGeneratedCodeInfo,
                                              final String generatedVarName) {
        final List<String> code = new ArrayList<String>();

        final String declString =
                getTemplate(DECL_KEY)
                .replaceAll(VOTE_STRUCT_TYPE, voteInputStruct.getStruct().getName())
                .replaceAll(VOTE_VAR_NAME, generatedVarName)
                .replaceAll(VOTE_VAR_NAME, generatedVarName)
                .replaceAll(AMOUNT_MEMBER, voteInputStruct.getAmountName())
                .replaceAll(AMOUNT_VOTERS, String.valueOf(v));
        code.add(declString);

        for (int i = 0; i < votesPerVoter.size(); ++i) {
            for (int j = 0; j < c; ++j) {
                final String voteString =
                        getTemplate(INIT_KEY)
                        .replaceAll(VOTE_VAR_NAME, generatedVarName)
                        .replaceAll(LIST_MEMBER,
                                    voteInputStruct.getListName())
                        .replaceAll(LIST_INDEX, String.valueOf(i))
                        .replaceAll(CAND_INDEX, String.valueOf(j))
                        .replaceAll(VOTE_GIVEN,
                                    String.valueOf(votesPerVoter.get(i).get(j)));
                code.add(voteString);
            }
        }
        return String.join(LINKE_BREAK, code);
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
    public final int getLastElectionNumber() {
        return 1;
    }
}
