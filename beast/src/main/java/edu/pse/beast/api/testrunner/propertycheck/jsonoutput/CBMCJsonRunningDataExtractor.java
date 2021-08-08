package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
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

    private List<CBMCJsonMessage> messages = new ArrayList<CBMCJsonMessage>();

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
        final CBMCJsonMessage msg;
        if (json.has(PROGRAM_KEY)) {
            msg = new CBMCJsonMessage(PROGRAM_KEY, json.getString(PROGRAM_KEY));
        } else if (json.has(MESSAGE_TYPE_KEY)) {
            msg = new CBMCJsonMessage(json.getString(MESSAGE_TYPE_KEY),
                                      json.getString(MESSAGE_TEXT_KEY));
        } else {
            msg = null;
        }
        if (msg != null) {
            messages.add(msg);
        }
        return msg;
    }

    public final void initializeWithRawOutput(final List<String> rawOutput) {
        final JSONArray arr = CBMCJsonHelper.rawOutputToJSON(rawOutput);
        final int arrLen = arr != null ? arr.length() : 0;
        for (int i = 0; i < arrLen; ++i) {
            final JSONObject json = arr.getJSONObject(i);
            parseJsonObject(json);
        }
    }

    public final CBMCJsonMessage appendOutput(final String rawOutput) {
        final CBMCJsonMessage msg;
        if (currentOutput == null) {
            if (rawOutput.trim().startsWith(BRACE_OP)) {
                // must be valid start of new JSON object
                currentOutput = rawOutput;
            }
            msg = null;
        } else {
            if (rawOutput.trim().startsWith(BRACE_CL)) {
                JSONObject json = null;
                try {
                    json = new JSONObject(currentOutput + BRACE_CL);
                    currentOutput = null;
                } catch (JSONException e) {
                    currentOutput += rawOutput;
                }
                msg = json != null ? parseJsonObject(json) : null;
            } else {
                msg = null;
                currentOutput += rawOutput;
            }
        }
        return msg;
    }
}
