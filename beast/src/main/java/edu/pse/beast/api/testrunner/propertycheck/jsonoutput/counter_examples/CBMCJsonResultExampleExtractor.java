package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonHelper;

public class CBMCJsonResultExampleExtractor {

    private static final String CBMC_JSON_RESULT_KEY = "result";
    private static final String CBMC_JSON_TRACE_KEY = "trace";
    private static final String CBMC_CPROVER_STATUS_KEY = "cProverStatus";
    private static final String STEP_TYPE_KEY = "stepType";
    private static final String STEP_TYPE_VALUE_ASSIGNMENT = "assignment";
    private static final String ASSIGNMENT_VALUE_KEY = "value";
    // private final String ASSIGNMENT_TYPE_KEY = "assignmentType";
    private static final String SOURCE_LOCATION = "sourceLocation";
    private static final String DATA = "data";
    private static final String FUNCTION = "function";
    private static final String FAILURE = "failure";
    private static final String DOT = ".";
    private static final String C = "C";
    private static final String S = "S";
    private static final String V = "V";

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;
    private int seatAmount;
    private int candidateAmount;
    private int voterAmount;
    private List<String> rawOutput = new ArrayList<>();
    private JSONArray resultArr = new JSONArray();
    private JSONArray traceArr;
    private String cProverStatus;

    private CBMCCounterExample generatedExample;

    private CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo;

    private boolean cbmcFoundExample;

    public CBMCJsonResultExampleExtractor(final CElectionDescription descr,
                                          final PreAndPostConditionsDescription propDescr,
                                          final CBMCGeneratedCodeInfo generatedCodeInfo,
                                          final int seats,
                                          final int candidates,
                                          final int votes) {
        this.description = descr;
        this.propertyDescription = propDescr;
        this.seatAmount = seats;
        this.candidateAmount = candidates;
        this.voterAmount = votes;
        this.cbmcGeneratedCodeInfo = generatedCodeInfo;
    }

    public final boolean didCBMCFindExample() {
        return cbmcFoundExample;
    }

    private void parseOutputJSONArr(final JSONArray outputArr) {
        for (int i = 0; i < outputArr.length(); ++i) {
            final JSONObject currentJson = outputArr.getJSONObject(i);
            if (currentJson.has(CBMC_JSON_RESULT_KEY)) {
                resultArr = currentJson.getJSONArray(CBMC_JSON_RESULT_KEY);
            } else if (currentJson.has(CBMC_CPROVER_STATUS_KEY)) {
                cProverStatus = currentJson.getString(CBMC_CPROVER_STATUS_KEY);
            }
        }
        for (int i = 0; i < resultArr.length(); ++i) {
            final JSONObject currentJson = resultArr.getJSONObject(i);
            if (currentJson.has(CBMC_JSON_TRACE_KEY)) {
                traceArr = currentJson.getJSONArray(CBMC_JSON_TRACE_KEY);
            }
        }
    }

    private static void processCBMCJsonCounterExampleTrace(final JSONArray trace,
                                                           final CBMCGeneratedCodeInfo codeInfo,
                                                           final CBMCCounterExample cExample) {
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
                final String lhs = traceJsonObj.getString("lhs");

                if (!lhs.contains(DOT)) {
                    if (lhs.startsWith(V) || lhs.startsWith(C) || lhs.startsWith(S)) {
                        try {
                            Integer.valueOf(lhs.substring(1));
                            System.out.println(lhs + " " + valueJsonObj.getString(DATA));
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
                System.out.println(valueJsonObj.getString(DATA));
                System.out.println(valueJsonObj);

                try {
                    Integer.valueOf(valueStr);
                } catch (NumberFormatException e) {
                    valueStr = "NaN";
                }

                CBMCAssignmentType assType = CBMCAssignmentType.UNKNOWN;
                if (codeInfo.getVoteVariableNameToVoteNumber().keySet().contains(structName)) {
                    assType = CBMCAssignmentType.VOTE;
                } else if (codeInfo.getGeneratedVotingVarNames().contains(structName)) {
                    assType = CBMCAssignmentType.GENERATED_VOTE;
                } else if (codeInfo.getElectVariableNameToElectNumber().keySet()
                            .contains(structName)) {
                    assType = CBMCAssignmentType.ELECT;
                } else if (codeInfo.getGeneratedElectVarNames().contains(structName)) {
                    assType = CBMCAssignmentType.GENERATED_ELECT;
                }
                final String info = codeInfo.getInfo(structName);
                cExample.add(structName, assType, memberName, valueStr, info);
            }
        }
    }

    public final void processCBMCJsonOutput(final List<String> testRunLogs) {
        rawOutput.clear();
        rawOutput.addAll(testRunLogs);
        final JSONArray outputArr = CBMCJsonHelper.rawOutputToJSON(rawOutput);
        if (outputArr != null) {
            parseOutputJSONArr(outputArr);

            cbmcFoundExample = FAILURE.equals(cProverStatus);
            if (cbmcFoundExample) {
                generatedExample = new CBMCCounterExample(cbmcGeneratedCodeInfo);
                processCBMCJsonCounterExampleTrace(traceArr, cbmcGeneratedCodeInfo,
                                                   generatedExample);
            }
        }
    }

    private static String removeAnythingButDigits(final String s) {
        String newString = "";
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isDigit(s.charAt(i))) {
                newString += s.charAt(i);
            }
        }
        return newString;
    }

    public final CBMCCounterExample getGeneratedExample() {
        return generatedExample;
    }
}
