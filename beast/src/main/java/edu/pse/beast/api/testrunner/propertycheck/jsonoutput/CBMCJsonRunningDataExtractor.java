package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCJsonRunningDataExtractor {

	private final static String PROGRAM_KEY = "program";
	public final static String MESSAGE_TEXT_KEY = "messageText";
	public final static String MESSAGE_TYPE_KEY = "messageType";

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	private int s, c, v;
	private CBMCGeneratedCodeInfo codeInfo;

	private List<CBMCJsonMessage> messages = new ArrayList<>();

	private String currentOutput;

	public CBMCJsonRunningDataExtractor(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr, int s, int c, int v,
			CBMCGeneratedCodeInfo codeInfo) {
		super();
		this.descr = descr;
		this.propDescr = propDescr;
		this.s = s;
		this.c = c;
		this.v = v;
		this.codeInfo = codeInfo;
	}
	
	public List<CBMCJsonMessage> getMessages() {
		return messages;
	}

	private CBMCJsonMessage parseJsonObject(JSONObject json) {
		if (json.has(PROGRAM_KEY)) {
			CBMCJsonMessage msg = new CBMCJsonMessage(PROGRAM_KEY,
					json.getString(PROGRAM_KEY));
			messages.add(msg);
			return msg;
		}
		if (json.has(MESSAGE_TYPE_KEY)) {
			CBMCJsonMessage msg = new CBMCJsonMessage(
					json.getString(MESSAGE_TYPE_KEY),
					json.getString(MESSAGE_TEXT_KEY));
			messages.add(msg);
			return msg;
		}
		return null;
	}

	public void initializeWithRawOutput(List<String> rawOutput) {
		JSONArray arr = CBMCJsonHelper.rawOutputToJSON(rawOutput);
		for (int i = 0; i < arr.length(); ++i) {
			JSONObject json = arr.getJSONObject(i);
			parseJsonObject(json);
		}
	}

	public CBMCJsonMessage appendOutput(String rawOutput) {
		if (currentOutput == null) {
			if (rawOutput.trim().startsWith("{")) { // must be valid start of
													// new json object
				currentOutput = rawOutput;
			}
		} else {
			if (rawOutput.trim().startsWith("}")) {
				try {
					JSONObject json = new JSONObject(currentOutput + "}");
					currentOutput = null;
					return parseJsonObject(json);
				} catch (JSONException e) {
					currentOutput += rawOutput;
				}
			} else {
				currentOutput += rawOutput;
			}
		}
		return null;
	}
}
