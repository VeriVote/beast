package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCJsonOutputHandler {

	private final String OUTPUT_KEY = "output";
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

	private List<VoteTypeAssignment> voteTypeAssignments = new ArrayList<>();
	private List<VoteAssignment> voteAssignments = new ArrayList<>();
	private List<ElectAssignment> electAssignments = new ArrayList<>();
	private List<String> allAssignments = new ArrayList<>();

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

	public List<String> getAllAssignments() {
		return allAssignments;
	}

	public String getExampleText() {
		Map<Integer, Map<String, Integer>> voteNumberToAssignmentString = new HashMap<>();
		for (VoteAssignment va : voteAssignments) {
			if (!voteNumberToAssignmentString.containsKey(va.getVoteNumber())) {
				voteNumberToAssignmentString.put(va.getVoteNumber(),
						new HashMap<>());
			}
			Map<String, Integer> memberAssignments = voteNumberToAssignmentString
					.get(va.getVoteNumber());
			memberAssignments.put(va.getMemberName(), va.getValue());
		}

		String completeString = "";
		for (Integer voteNumber : voteNumberToAssignmentString.keySet()) {
			List<String> exampleString = new ArrayList<>();
			Map<String, Integer> memberAssignments = voteNumberToAssignmentString
					.get(voteNumber);
			for (String member : memberAssignments.keySet()) {
				exampleString.add("    " + member + " = "
						+ memberAssignments.get(member));
			}
			exampleString.sort((s1, s2) -> {
				return s1.compareTo(s2);
			});
			completeString += "Votes" + voteNumber + " {\n";
			completeString += String.join("\n", exampleString);
			completeString += "\n}\n";
		}

		Map<String, Map<String, Integer>> varNameToOtherVoteAssignments = new HashMap();
		for (VoteTypeAssignment vta : voteTypeAssignments) {
			if (!varNameToOtherVoteAssignments.containsKey(vta.getName())) {
				varNameToOtherVoteAssignments.put(vta.getName(),
						new HashMap<>());
			}
			Map<String, Integer> memberValueMap = varNameToOtherVoteAssignments
					.get(vta.getName());
			memberValueMap.put(vta.getMember(), vta.getValue());
		}

		for (String varName : varNameToOtherVoteAssignments.keySet()) {
			List<String> list = new ArrayList<>();

			Map<String, Integer> memberValueMap = varNameToOtherVoteAssignments
					.get(varName);

			for (String member : memberValueMap.keySet()) {
				list.add("    " + member + " = " + memberValueMap.get(member));
			}

			list.sort((s1, s2) -> {
				return s1.compareTo(s2);
			});

			completeString += varName + " {\n";
			completeString += String.join("\n", list);
			completeString += "\n}\n";
		}

		Map<Integer, Map<String, Integer>> electNumberToAssignmentString = new HashMap<>();
		for (ElectAssignment ea : electAssignments) {
			if (!electNumberToAssignmentString
					.containsKey(ea.getElectNumber())) {
				electNumberToAssignmentString.put(ea.getElectNumber(),
						new HashMap<>());
			}
			Map<String, Integer> memberAssignments = electNumberToAssignmentString
					.get(ea.getElectNumber());
			memberAssignments.put(ea.getMemberName(), ea.getValue());
		}
		for (Integer electNumber : electNumberToAssignmentString.keySet()) {
			Map<String, Integer> memberAssignments = electNumberToAssignmentString
					.get(electNumber);

			List<String> list = new ArrayList();
			for (String memberName : memberAssignments.keySet()) {
				list.add("    " + memberName + " = "
						+ memberAssignments.get(memberName));
			}
			list.sort((s1, s2) -> {
				return s1.compareTo(s2);
			});

			completeString += "Elect" + electNumber + " {\n";
			completeString += String.join("\n", list);
			completeString += "\n}\n";
		}

		return completeString;
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

	public String getAllAssignmentsText() {
		return String.join("\n", allAssignments);
	}

	private void processCBMCJsonOutput(
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo) {

		// find the beginning of the json array
		while (!rawOutput.get(0).startsWith("[")) {
			rawOutput.remove(0);
		}
		String jsonString = "{ " + OUTPUT_KEY + " : "
				+ String.join("\n", rawOutput) + "}";
		JSONObject resultJson = new JSONObject(jsonString);
		JSONArray outputArr = resultJson.getJSONArray(OUTPUT_KEY);
		parseOutputJSONArr(outputArr);

		if (!cProverStatus.equals("failure")) {
			return;
		}

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

				if (!lhs.contains("."))
					continue;

				if (!valueJsonObj.has("data"))
					continue;

				int dotIdx = lhs.indexOf('.');

				String structName = lhs.substring(0, dotIdx);

				String memberName = lhs.substring(dotIdx + 1);

				String valueStr = removeAnythingButDigits(
						valueJsonObj.getString("data"));
				int value = Integer.valueOf(valueStr);

				allAssignments
						.add(structName + "." + memberName + " = " + value);

				if (cbmcGeneratedCodeInfo.getVoteVariableNameToVoteNumber()
						.keySet().contains(structName)) {
					VoteAssignment ass = new VoteAssignment(assignmentLine,
							assignmentFunc,
							cbmcGeneratedCodeInfo
									.getVoteVariableNameToVoteNumber()
									.get(structName),
							structName, memberName, value);
					voteAssignments.add(ass);
				} else if (cbmcGeneratedCodeInfo.getGeneratedVotingVarNames()
						.contains(structName)) {
					voteTypeAssignments.add(new VoteTypeAssignment(structName,
							memberName, value));
				} else if (cbmcGeneratedCodeInfo
						.getElectVariableNameToElectNumber().keySet()
						.contains(structName)) {
					ElectAssignment ass = new ElectAssignment(assignmentLine,
							assignmentFunc,
							cbmcGeneratedCodeInfo
									.getElectVariableNameToElectNumber()
									.get(structName),
							structName, memberName, value);
					electAssignments.add(ass);
				}
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

}
