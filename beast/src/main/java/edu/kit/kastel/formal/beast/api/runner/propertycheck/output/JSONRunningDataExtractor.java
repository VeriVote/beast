package edu.kit.kastel.formal.beast.api.runner.propertycheck.output;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class JSONRunningDataExtractor {
    private static final String BRACE_OP = "{";
    private static final String BRACE_CL = "}";

    private static final String MESSAGE_TEXT_KEY = "messageText";
    private static final String MESSAGE_TYPE_KEY = "messageType";
    private static final String PROGRAM_KEY = "program";

    private CElectionDescription description;
    private PropertyDescription propertyDescription;
    private int seatAmount;
    private int candidateAmount;
    private int voterAmount;
    private GeneratedCodeInfo codeInfo;

    private List<JSONMessage> messages = new ArrayList<JSONMessage>();

    private String currentOutput;

    public JSONRunningDataExtractor(final CElectionDescription descr,
                                    final PropertyDescription propDescr,
                                    final int seats,
                                    final int candidates,
                                    final int voters,
                                    final GeneratedCodeInfo generatedCodeInfo) {
        super();
        this.description = descr;
        this.propertyDescription = propDescr;
        this.seatAmount = seats;
        this.candidateAmount = candidates;
        this.voterAmount = voters;
        this.codeInfo = generatedCodeInfo;
    }

    public final List<JSONMessage> getMessages() {
        return messages;
    }

    private JSONMessage parseJsonObject(final JSONObject json) {
        final JSONMessage msg;
        if (json.has(PROGRAM_KEY)) {
            msg = new JSONMessage(PROGRAM_KEY, json.getString(PROGRAM_KEY));
        } else if (json.has(MESSAGE_TYPE_KEY)) {
            msg = new JSONMessage(json.getString(MESSAGE_TYPE_KEY),
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
        final JSONArray arr = JSONHelper.rawOutputToJSON(rawOutput);
        final int arrLen = arr != null ? arr.length() : 0;
        for (int i = 0; i < arrLen; ++i) {
            final JSONObject json = arr.getJSONObject(i);
            parseJsonObject(json);
        }
    }

    public final JSONMessage appendOutput(final String rawOutput) {
        final JSONMessage msg;
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
