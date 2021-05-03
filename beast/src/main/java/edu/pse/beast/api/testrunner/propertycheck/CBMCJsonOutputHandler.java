package edu.pse.beast.api.testrunner.propertycheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCJsonOutputHandler {
	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	private int s, c, v;
	List<String> rawOutput = new ArrayList<>();
	JSONArray resultArr;
	JSONArray traceArr;
	String cProverStatus;

	private final String OUTPUT_KEY = "output";
	private final String CBMC_JSON_RESULT_KEY = "result";
	private final String CBMC_JSON_TRACE_KEY = "trace";
	private final String CBMC_CPROVER_STATUS_KEY = "cProverStatus";
	private final String STEP_TYPE_KEY = "stepType";
	private final String STEP_TYPE_VALUE_ASSIGNMENT = "assignment";
	private final String ASSIGNMENT_VALUE_KEY = "value";
	private final String ASSIGNMENT_TYPE_KEY = "assignmentType";

	public CBMCJsonOutputHandler(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo, int s, int c, int v,
			List<String> rawOutput) {
		this.descr = descr;
		this.propDescr = propDescr;
		this.s = s;
		this.c = c;
		this.v = v;
		this.rawOutput.addAll(rawOutput);
		processCBMCJsonOutput(cbmcGeneratedCodeInfo);
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

	private Map<Integer, JSONObject> voteNumberToStructJSON = new HashMap<>();

	private void processCBMCJsonOutput(CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo) {
		Map<Integer, String> voteNumbersToVarName = cbmcGeneratedCodeInfo.getVoteNumberToVariableName();
		
		// find the beginning of the json array
		while (!rawOutput.get(0).startsWith("[")) {
			rawOutput.remove(0);
		}
		String jsonString = "{ " + OUTPUT_KEY + " : "
				+ String.join("\n", rawOutput) + "}";
		JSONObject resultJson = new JSONObject(jsonString);
		JSONArray outputArr = resultJson.getJSONArray(OUTPUT_KEY);
		parseOutputJSONArr(outputArr);
		for (int i = 0; i < traceArr.length(); ++i) {
			JSONObject traceJsonObj = traceArr.getJSONObject(i);
			if (traceJsonObj.getString(STEP_TYPE_KEY)
					.equals(STEP_TYPE_VALUE_ASSIGNMENT)) {
				JSONObject valueJsonObj = traceJsonObj
						.getJSONObject(ASSIGNMENT_VALUE_KEY);
				String assignmentType = traceJsonObj
						.getString(ASSIGNMENT_TYPE_KEY);
				String lhs = traceJsonObj.getString("lhs");
				for(int voteNumber : voteNumbersToVarName.keySet()) {
					String voteVarName = voteNumbersToVarName.get(voteNumber);
					if(lhs.contains(voteVarName)) {
						if(!voteNumberToStructJSON.containsKey(voteNumber)) {
							voteNumberToStructJSON.put(voteNumber, new JSONObject());
						}
						JSONObject voteStructJson = voteNumberToStructJSON.get(voteNumber);
						if(!valueJsonObj.getString("name").equals("struct")) {
							if(lhs.contains(cbmcGeneratedCodeInfo.getAmtMemberVarName())) {
								String assignedValueString = valueJsonObj.getString("data");
								int assignedValue = Integer.valueOf(removeAnythingButDigits(assignedValueString));
								voteStructJson.put(lhs, assignedValue);
							} else if(lhs.contains(cbmcGeneratedCodeInfo.getListMemberVarName())) {
								String assignedValueString = valueJsonObj.getString("data");
								int assignedValue = Integer.valueOf(removeAnythingButDigits(assignedValueString));
								voteStructJson.put(lhs, assignedValue);
							}
						}						
					}
				}
			}
		}
		System.out.println(voteNumberToStructJSON.get(1).toString(4));
	}
	
	String removeAnythingButDigits(String s) {
		String newString = "";
		for(int i = 0; i < s.length(); ++i) {
			if(Character.isDigit(s.charAt(i))) {
				newString += s.charAt(i);
			}
		}
		return newString;
	}
}
