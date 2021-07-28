package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

public class CBMCJsonRunningDataExtractor {
    private static final String BRACE_OP = "{";
    private static final String BRACE_CL = "}";

    private static final String MESSAGE_TEXT_KEY = "messageText";
    private static final String MESSAGE_TYPE_KEY = "messageType";
    private static final String PROGRAM_KEY = "program";

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;
    private int seatAmount;
    private int candidateAmount;
    private int voterAmount;
    private CBMCGeneratedCodeInfo codeInfo;

    private List<CBMCJsonMessage> messages = new ArrayList<>();

    private String currentOutput;

    public CBMCJsonRunningDataExtractor(final CElectionDescription descr,
                                        final PreAndPostConditionsDescription propDescr,
                                        final int seats,
                                        final int candidates,
                                        final int voters,
                                        final CBMCGeneratedCodeInfo generatedCodeInfo) {
        super();
        this.description = descr;
        this.propertyDescription = propDescr;
        this.seatAmount = seats;
        this.candidateAmount = candidates;
        this.voterAmount = voters;
        this.codeInfo = generatedCodeInfo;
    }

    public final List<CBMCJsonMessage> getMessages() {
        return messages;
    }

    private CBMCJsonMessage parseJsonObject(final JSONObject json) {
        if (json.has(PROGRAM_KEY)) {
            final CBMCJsonMessage msg =
                    new CBMCJsonMessage(PROGRAM_KEY, json.getString(PROGRAM_KEY));
            messages.add(msg);
            return msg;
        }
        if (json.has(MESSAGE_TYPE_KEY)) {
            final CBMCJsonMessage msg =
                    new CBMCJsonMessage(json.getString(MESSAGE_TYPE_KEY),
                                        json.getString(MESSAGE_TEXT_KEY));
            messages.add(msg);
            return msg;
        }
        return null;
    }

    public final void initializeWithRawOutput(final List<String> rawOutput) {
        final JSONArray arr = CBMCJsonHelper.rawOutputToJSON(rawOutput);
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length(); ++i) {
            final JSONObject json = arr.getJSONObject(i);
            parseJsonObject(json);
        }
    }

    public final CBMCJsonMessage appendOutput(final String rawOutput) {
        if (currentOutput == null) {
            if (rawOutput.trim().startsWith(BRACE_OP)) {
                // must be valid start of new JSON object
                currentOutput = rawOutput;
            }
        } else {
            if (rawOutput.trim().startsWith(BRACE_CL)) {
                try {
                    final JSONObject json = new JSONObject(currentOutput + BRACE_CL);
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
