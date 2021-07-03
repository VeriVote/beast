package edu.pse.beast.api.savingloading;

import java.io.File;

import org.json.JSONObject;
import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCGeneratedCodeInfoSaverLoaderHelper;

public class CodeInfoSavingLoadingTest {

	@Test
	public void testSavingLoadingGenCodeInfo() {
		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
		cbmcGeneratedCodeInfo.setCode("asdasdasdasdasd");
		cbmcGeneratedCodeInfo.addVotingVariableName(1, "vote1");
		cbmcGeneratedCodeInfo.addVotingVariableName(2, "vote2");
		cbmcGeneratedCodeInfo.addElectVariableName(1, "elect1");
		cbmcGeneratedCodeInfo.addedGeneratedVotingVar("intersection1");
		cbmcGeneratedCodeInfo.addInfo("intersection1", "an intersection");
		cbmcGeneratedCodeInfo.setVotesAmtMemberVarName("amt");
		cbmcGeneratedCodeInfo.setVotesListMemberVarName("list");
		JSONObject json = CBMCGeneratedCodeInfoSaverLoaderHelper
				.generatedCodeInfoToJSON(cbmcGeneratedCodeInfo);
		CBMCGeneratedCodeInfo loaded = CBMCGeneratedCodeInfoSaverLoaderHelper
				.generatedCodeInfoFromJSON(json);
	}
}
