package edu.kit.kastel.formal.beast.api.trace;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.output.JSONHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class JSONResultExtractor {
    private static final String NOT_A_NUMBER = "NaN";

    private static final String RESULT_KEY = "result";
    private static final String TRACE_KEY = "trace";
    private static final String CPROVER_STATUS_KEY = "cProverStatus";
    private static final String STEP_TYPE_KEY = "stepType";
    private static final String STEP_TYPE_VALUE_ASSIGNMENT = "assignment";
    private static final String ASSIGNMENT_VALUE_KEY = "value";
    private static final String LHS = "lhs";
    // private final String ASSIGNMENT_TYPE_KEY = "assignmentType";
    private static final String SOURCE_LOCATION = "sourceLocation";
    private static final String DATA = "data";
    private static final String FUNCTION = "function";
    private static final String FAILURE = "failure";
    private static final String DOT = ".";
    private static final String C = "C"; // TODO: If ever used, change to non-hard-coded version
    private static final String S = "S"; // TODO: If ever used, change to non-hard-coded version
    private static final String V = "V"; // TODO: If ever used, change to non-hard-coded version

    // private CElectionDescription description;
    // private PropertyDescription propertyDescription;
    // private int seatAmount;
    // private int candidateAmount;
    // private int voterAmount;
    private List<String> rawOutput = new ArrayList<String>();
    private JSONArray resultArr = new JSONArray();
    private JSONArray traceArr;
    private String cProverStatus;

    private CounterExample generatedExample;

    private GeneratedCodeInfo generatedCodeInfo;

    private boolean foundCounterExample;

    public JSONResultExtractor(final CElectionDescription descr,
                               final PropertyDescription propDescr,
                               final GeneratedCodeInfo codeInfo,
                               final int seats,
                               final int candidates,
                               final int votes) {
        // this.description = descr;
        // this.propertyDescription = propDescr;
        // this.seatAmount = seats;
        // this.candidateAmount = candidates;
        // this.voterAmount = votes;
        this.generatedCodeInfo = codeInfo;
    }

    public final boolean didFindCounterExample() {
        return foundCounterExample;
    }

    private void parseOutputJSONArr(final JSONArray outputArr) {
        for (int i = 0; i < outputArr.length(); ++i) {
            final JSONObject currentJson = outputArr.getJSONObject(i);
            if (currentJson.has(RESULT_KEY)) {
                resultArr = currentJson.getJSONArray(RESULT_KEY);
            } else if (currentJson.has(CPROVER_STATUS_KEY)) {
                cProverStatus = currentJson.getString(CPROVER_STATUS_KEY);
            }
        }
        for (int i = 0; i < resultArr.length(); ++i) {
            final JSONObject currentJson = resultArr.getJSONObject(i);
            if (currentJson.has(TRACE_KEY)) {
                traceArr = currentJson.getJSONArray(TRACE_KEY);
            }
        }
    }

    private static void processJSONCounterExampleTrace(final JSONArray trace,
                                                       final GeneratedCodeInfo codeInfo,
                                                       final CounterExample cExample) {
        for (int i = 0; i < trace.length(); ++i) {
            final JSONObject traceJsonObj = trace.getJSONObject(i);
            if (traceJsonObj.getString(STEP_TYPE_KEY).equals(STEP_TYPE_VALUE_ASSIGNMENT)) {
                final JSONObject valueJsonObj =
                        traceJsonObj.getJSONObject(ASSIGNMENT_VALUE_KEY);
                if (!traceJsonObj.has(SOURCE_LOCATION)) {
                    continue;
                }
                final JSONObject locationJsonObj =
                        traceJsonObj.getJSONObject(SOURCE_LOCATION);
                if (!locationJsonObj.has(FUNCTION)) {
                    continue;
                }

                // String assignmentLine = locationJsonObj.getString("line");
                // String assignmentFunc = locationJsonObj.getString("function");
                // String assignmentType = traceJsonObj.getString(ASSIGNMENT_TYPE_KEY);
                final String lhs = traceJsonObj.getString(LHS);

                if (!lhs.contains(DOT)) {
                    if (lhs.startsWith(V) || lhs.startsWith(C) || lhs.startsWith(S)) {
                        try {
                            Integer.valueOf(lhs.substring(1));
                            // System.out.println(lhs + BLANK + valueJsonObj.getString(DATA));
                            // FIXME: Do something!?
                        } catch (NumberFormatException e) {
                            // TODO: handle exception
                        }
                    }
                    continue;
                }

                if (!valueJsonObj.has(DATA)) {
                    continue;
                }

                final int dotIdx = lhs.indexOf(DOT);
                final String structName = lhs.substring(0, dotIdx);
                final String memberName = lhs.substring(dotIdx + 1);

                String valueStr = removeAnythingButDigits(valueJsonObj.getString(DATA));

                try {
                    Integer.valueOf(valueStr);
                } catch (NumberFormatException e) {
                    valueStr = NOT_A_NUMBER;
                }

                AssignmentType assType = AssignmentType.UNKNOWN;
                if (codeInfo.getVoteVariableNameToVoteNumber().keySet().contains(structName)) {
                    assType = AssignmentType.VOTE;
                } else if (codeInfo.getGeneratedVotingVarNames().contains(structName)) {
                    assType = AssignmentType.GENERATED_VOTE;
                } else if (codeInfo.getElectVariableNameToElectNumber().keySet()
                            .contains(structName)) {
                    assType = AssignmentType.ELECT;
                } else if (codeInfo.getGeneratedElectVarNames().contains(structName)) {
                    assType = AssignmentType.GENERATED_ELECT;
                }
                final String info = codeInfo.getInfo(structName);
                cExample.add(structName, assType, memberName, valueStr, info);
            }
        }
    }

    public final void processJSONOutput(final List<String> runLogs) {
        rawOutput.clear();
        rawOutput.addAll(runLogs);
        final JSONArray outputArr = JSONHelper.rawOutputToJSON(rawOutput);
        if (outputArr != null) {
            parseOutputJSONArr(outputArr);
            foundCounterExample = FAILURE.equals(cProverStatus);
            if (foundCounterExample) {
                generatedExample = new CounterExample(generatedCodeInfo);
                processJSONCounterExampleTrace(traceArr, generatedCodeInfo, generatedExample);
            }
        }
    }

    private static String removeAnythingButDigits(final String s) {
        final StringBuilder newString = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isDigit(s.charAt(i))) {
                newString.append(s.charAt(i));
            }
        }
        return newString.toString();
    }

    public final CounterExample getGeneratedExample() {
        return generatedExample;
    }
}
