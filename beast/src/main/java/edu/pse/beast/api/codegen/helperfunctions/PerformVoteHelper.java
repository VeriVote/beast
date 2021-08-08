package edu.pse.beast.api.codegen.helperfunctions;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PerformVoteHelper {
    private static final String RESOURCES =
            "/edu/pse/beast/api/codegen/helperfunctions/";
    private static final String FILE_KEY = "PERFORM_VOTE";
    private static final String FILE_ENDING = ".template";

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

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public static String getResultVarName(final int voteNumber) {
        return RESULT + voteNumber;
    }

    public static String generateCode(final int votingCallNumber,
                                      final ElectionTypeCStruct voteArrStruct,
                                      final ElectionTypeCStruct voteStruct,
                                      final CodeGenOptions options,
                                      final String votingFunctionName,
                                      final CBMCGeneratedCodeInfo cbmcGeneratedCode,
                                      final Class<?> c) {
        final String resultVarName = getResultVarName(votingCallNumber);
        cbmcGeneratedCode.addElectVariableName(votingCallNumber, resultVarName);
        final String voteVarName = SymbVarInitVoteHelper.getVoteVarName(options, votingCallNumber);

        String code = getTemplate(FILE_KEY, c);
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
