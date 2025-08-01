package edu.kit.kastel.formal.beast.api.io;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.io.code.GeneratedCodeInfoInputOutputHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeInfoInputOutputTest {
    private static final String TEST_CODE_STRING = "asdasdasdasdasd";
    private static final String VOTE1 = "vote1";
    private static final String VOTE2 = "vote2";
    private static final String ELECT1 = "elect1";
    private static final String INTERSECTION1 = "intersection1";
    private static final String INTERSECTION_INFO = "An Intersection";

    private static final String AMOUNT_VAR = "amt";
    private static final String LIST = "list";

    @Test
    public void testInputOutputCodeInfo() {
        final GeneratedCodeInfo codeInfo = new GeneratedCodeInfo();
        codeInfo.setCode(TEST_CODE_STRING);
        codeInfo.addVotingVariableName(1, VOTE1);
        codeInfo.addVotingVariableName(2, VOTE2);
        codeInfo.addElectVariableName(1, ELECT1);
        codeInfo.addedGeneratedVotingVar(INTERSECTION1);
        codeInfo.addInfo(INTERSECTION1, INTERSECTION_INFO);
        codeInfo.setVotesAmtMemberVarName(AMOUNT_VAR);
        codeInfo.setVotesListMemberVarName(LIST);
        codeInfo.setResultAmtMemberVarName(AMOUNT_VAR);
        codeInfo.setResultListMemberVarName(LIST);
        final JSONObject json =
                GeneratedCodeInfoInputOutputHelper.generatedCodeInfoToJSON(codeInfo);
        GeneratedCodeInfoInputOutputHelper.generatedCodeInfoFromJSON(json);
    }
}
