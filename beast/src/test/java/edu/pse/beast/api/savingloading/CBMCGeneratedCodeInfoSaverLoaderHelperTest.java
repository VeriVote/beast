package edu.pse.beast.api.savingloading;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import edu.pse.beast.api.codegen.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCGeneratedCodeInfoSaverLoaderHelper;

public class CBMCGeneratedCodeInfoSaverLoaderHelperTest {

	@Test
	public void testCBMCGeneratedCodeInfoSaverLoaderHelper() {
		CBMCGeneratedCodeInfo generatedCodeInfo = new CBMCGeneratedCodeInfo();
		String code = "56as4d6s8dgf456df8g46dfg4";

		Map<Integer, String> voteNumberToVariableNameMap = new HashMap<>();

		voteNumberToVariableNameMap.put(0, "asdasd");
		voteNumberToVariableNameMap.put(1, "fdgrth");
		voteNumberToVariableNameMap.put(2, "gvfger");

		generatedCodeInfo.setCode(code);
		generatedCodeInfo
				.setVoteNumberToVariableName(voteNumberToVariableNameMap);

		JSONObject json = CBMCGeneratedCodeInfoSaverLoaderHelper
				.generatedCodeInfoToJSON(generatedCodeInfo);
		CBMCGeneratedCodeInfo loaded = CBMCGeneratedCodeInfoSaverLoaderHelper
				.generatedCodeInfoFromJSON(json);

		assertEquals(loaded.getCode(), generatedCodeInfo.getCode());
		for (Integer voteNumber : loaded.getVoteNumberToVariableName()
				.keySet()) {
			assertTrue(generatedCodeInfo.getVoteNumberToVariableName()
					.containsKey(voteNumber));
			assertEquals(
					generatedCodeInfo.getVoteNumberToVariableName()
							.get(voteNumber),
					loaded.getVoteNumberToVariableName().get(voteNumber));
		}
	}

}
