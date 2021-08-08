package edu.pse.beast.api.savingloading;

import org.json.JSONObject;
import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCGeneratedCodeInfoSaverLoaderHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeInfoSavingLoadingTest {
    private static final String TEST_CODE_STRING = "asdasdasdasdasd";
    private static final String VOTE1 = "vote1";
    private static final String VOTE2 = "vote2";
    private static final String ELECT1 = "elect1";
    private static final String INTERSECTION1 = "intersection1";
    private static final String INTERSECTION_INFO = "An Intersection";

    private static final String AMOUNT_VAR = "amt";
    private static final String LIST = "list";

    @Test
    public void testSavingLoadingGenCodeInfo() {
        final CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
        cbmcGeneratedCodeInfo.setCode(TEST_CODE_STRING);
        cbmcGeneratedCodeInfo.addVotingVariableName(1, VOTE1);
        cbmcGeneratedCodeInfo.addVotingVariableName(2, VOTE2);
        cbmcGeneratedCodeInfo.addElectVariableName(1, ELECT1);
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(INTERSECTION1);
        cbmcGeneratedCodeInfo.addInfo(INTERSECTION1, INTERSECTION_INFO);
        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName(AMOUNT_VAR);
        cbmcGeneratedCodeInfo.setVotesListMemberVarName(LIST);
        cbmcGeneratedCodeInfo.setResultAmtMemberVarName(AMOUNT_VAR);
        cbmcGeneratedCodeInfo.setResultListMemberVarName(LIST);
        final JSONObject json =
                CBMCGeneratedCodeInfoSaverLoaderHelper
                .generatedCodeInfoToJSON(cbmcGeneratedCodeInfo);
        CBMCGeneratedCodeInfoSaverLoaderHelper.generatedCodeInfoFromJSON(json);
    }
}
