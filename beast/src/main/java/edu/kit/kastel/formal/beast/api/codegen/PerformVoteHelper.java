package edu.kit.kastel.formal.beast.api.codegen;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PerformVoteHelper {
    private static final String FILE_KEY = "PERFORM_VOTE";
    private static final String EMPTY = "";

    private static final String RESULT = "result";
    private static final String ELECT_TYPE = "ELECT_TYPE";
    private static final String GENERATED_VAR = "GENERATED_VAR";
    private static final String VOTE_FUNC_NAME = "VOTE_FUNC_NAME";
    private static final String VOTE_TYPE = "VOTE_TYPE";
    private static final String VOTE_VAR = "VOTE_VAR";
    private static final String CURRENT_AMOUNT_VOTER = "CURRENT_AMT_VOTER";
    private static final String CURRENT_AMOUNT_CAND = "CURRENT_AMT_CAND";
    private static final String CURRENT_AMOUNT_SEAT = "CURRENT_AMT_SEAT";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    public static String getResultVarName(final int voteNumber) {
        return RESULT + voteNumber;
    }

    public static String generateCode(final int votingCallNumber,
                                      final ElectionTypeCStruct voteArrStruct,
                                      final ElectionTypeCStruct voteStruct,
                                      final CodeGenOptions options,
                                      final String votingFunctionName,
                                      final GeneratedCodeInfo cbmcGeneratedCode) {
        final String resultVarName = getResultVarName(votingCallNumber);
        cbmcGeneratedCode.addElectVariableName(votingCallNumber, resultVarName);
        final String voteVarName = SymbVarInitVoteHelper.getVoteVarName(options, votingCallNumber);

        final PerformVoteHelper performVote = new PerformVoteHelper();
        String code = performVote.getTemplate(FILE_KEY);
        code = code.replaceAll(ELECT_TYPE, voteStruct.getStruct().getName());
        code = code.replaceAll(GENERATED_VAR, resultVarName);
        code = code.replaceAll(VOTE_FUNC_NAME, votingFunctionName);
        code = code.replaceAll(VOTE_TYPE, voteStruct.getStruct().getName());
        code = code.replaceAll(VOTE_VAR, voteVarName);
        code = code.replaceAll(CURRENT_AMOUNT_VOTER,
                SymbVarInitVoteHelper.getCurrentAmtVoter(options, votingCallNumber));
        code = code.replaceAll(CURRENT_AMOUNT_CAND,
                SymbVarInitVoteHelper.getCurrentAmtCand(options, votingCallNumber));
        code = code.replaceAll(CURRENT_AMOUNT_SEAT,
                SymbVarInitVoteHelper.getCurrentAmtSeat(options, votingCallNumber));
        return code;
    }
}
