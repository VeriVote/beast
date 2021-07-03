package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonHelper;

public class CBMCJsonResultExampleExtractor {

	private final String CBMC_JSON_RESULT_KEY = "result";
	private final String CBMC_JSON_TRACE_KEY = "trace";
	private final String CBMC_CPROVER_STATUS_KEY = "cProverStatus";
	private final String STEP_TYPE_KEY = "stepType";
	private final String STEP_TYPE_VALUE_ASSIGNMENT = "assignment";
	private final String ASSIGNMENT_VALUE_KEY = "value";
	private final String ASSIGNMENT_TYPE_KEY = "assignmentType";

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	private int s, c, v;
	private List<String> rawOutput = new ArrayList<>();
	private JSONArray resultArr;
	private JSONArray traceArr;
	private String cProverStatus;

	private CBMCCounterExample generatedExample;

	private CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo;

	private boolean cbmcFoundExample;

	public CBMCJsonResultExampleExtractor(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo, int s, int c, int v) {
		this.descr = descr;
		this.propDescr = propDescr;
		this.s = s;
		this.c = c;
		this.v = v;
		this.cbmcGeneratedCodeInfo = cbmcGeneratedCodeInfo;
	}

	public boolean didCBMCFindExample() {
		return cbmcFoundExample;
	}

	private void parseOutputJSONArr(JSONArray outputArr) {
		for (int i = 0; i < outputArr.length(); ++i) {
			JSONObject currentJson = outputArr.getJSONObject(i);
			if (currentJson.has(CBMC_JSON_RESULT_KEY)) {
				resultArr = currentJson.getJSONArray(CBMC_JSON_RESULT_KEY);
			} else if (currentJson.has(CBMC_CPROVER_STATUS_KEY)) {
				cProverStatus = currentJson.getString(CBMC_CPROVER_STATUS_KEY);
			}
		}
		for (int i = 0; i < resultArr.length(); ++i) {
			JSONObject currentJson = resultArr.getJSONObject(i);
			if (currentJson.has(CBMC_JSON_TRACE_KEY)) {
				traceArr = currentJson.getJSONArray(CBMC_JSON_TRACE_KEY);
			}
		}
	}

	public void processCBMCJsonOutput(List<String> testRunLogs) {
		rawOutput.clear();
		rawOutput.addAll(testRunLogs);
		JSONArray outputArr = CBMCJsonHelper.rawOutputToJSON(rawOutput);
		if (outputArr == null)
			return;
		parseOutputJSONArr(outputArr);

		if (!cProverStatus.equals("failure")) {
			cbmcFoundExample = false;
			return;
		}

		cbmcFoundExample = true;
		generatedExample = new CBMCCounterExample(cbmcGeneratedCodeInfo);

		for (int i = 0; i < traceArr.length(); ++i) {
			JSONObject traceJsonObj = traceArr.getJSONObject(i);
			if (traceJsonObj.getString(STEP_TYPE_KEY)
					.equals(STEP_TYPE_VALUE_ASSIGNMENT)) {
				JSONObject valueJsonObj = traceJsonObj
						.getJSONObject(ASSIGNMENT_VALUE_KEY);
				if (!traceJsonObj.has("sourceLocation")) {
					continue;
				}
				JSONObject locationJsonObj = traceJsonObj
						.getJSONObject("sourceLocation");
				if (!locationJsonObj.has("function"))
					continue;

				String assignmentLine = locationJsonObj.getString("line");
				String assignmentFunc = locationJsonObj.getString("function");
				String assignmentType = traceJsonObj
						.getString(ASSIGNMENT_TYPE_KEY);
				String lhs = traceJsonObj.getString("lhs");

				if (!lhs.contains(".")) {
					if (lhs.startsWith("V") || lhs.startsWith("C")
							|| lhs.startsWith("S")) {
						try {
							Integer.valueOf(lhs.substring(1));
							System.out.println(
									lhs + " " + valueJsonObj.getString("data"));
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					continue;
				}

				if (!valueJsonObj.has("data"))
					continue;

				int dotIdx = lhs.indexOf('.');

				String structName = lhs.substring(0, dotIdx);

				String memberName = lhs.substring(dotIdx + 1);

				String valueStr = removeAnythingButDigits(
						valueJsonObj.getString("data"));
				System.out.println(valueJsonObj.getString("data"));
				System.out.println(valueJsonObj);

				try {
					Integer.valueOf(valueStr);
				} catch (Exception e) {
					valueStr = "NaN";
				}

				CBMCAssignmentType assType = CBMCAssignmentType.UNKNOWN;
				if (cbmcGeneratedCodeInfo.getVoteVariableNameToVoteNumber()
						.keySet().contains(structName)) {
					assType = CBMCAssignmentType.VOTE;
				} else if (cbmcGeneratedCodeInfo.getGeneratedVotingVarNames()
						.contains(structName)) {
					assType = CBMCAssignmentType.GENERATED_VOTE;
				} else if (cbmcGeneratedCodeInfo
						.getElectVariableNameToElectNumber().keySet()
						.contains(structName)) {
					assType = CBMCAssignmentType.ELECT;
				} else if (cbmcGeneratedCodeInfo.getGeneratedElectVarNames()
						.contains(structName)) {
					assType = CBMCAssignmentType.GENERATED_ELECT;
				}

				String info = cbmcGeneratedCodeInfo.getInfo(structName);
				generatedExample.add(structName, assType, memberName, valueStr,
						info);
			}
		}
	}

	String removeAnythingButDigits(String s) {
		String newString = "";
		for (int i = 0; i < s.length(); ++i) {
			if (Character.isDigit(s.charAt(i))) {
				newString += s.charAt(i);
			}
		}
		return newString;
	}

	public CBMCCounterExample getGeneratedExample() {
		return generatedExample;
	}

}
