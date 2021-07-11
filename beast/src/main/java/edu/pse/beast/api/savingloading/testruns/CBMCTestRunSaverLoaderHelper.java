package edu.pse.beast.api.savingloading.testruns;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.CodeGenOptionsSaverLoaderHelper;
import edu.pse.beast.api.savingloading.RelativePathConverter;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCCodeFileDataSaverLoaderHelper;
import edu.pse.beast.api.savingloading.loopbound.LoopBoundSaverLoaderHelper;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class CBMCTestRunSaverLoaderHelper {

    private static final String CBMC_CODE_FILE_DATA_KEY = "cbmc_code_file";

    private static final String TEST_RUN_LOGS_KEY = "test_run_logs";

    private static final String AMT_VOTER_KEY = "amt_voter";
    private static final String AMT_CANDS_KEY = "amt_cands";
    private static final String AMT_SEATS_KEY = "amt_seats";

    private static final String LOOP_BOUND_LIST_KEY = "loop_bounds";

    private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

    private static final String STATE_KEY = "state";

    private static JSONArray loopBoundListToJSONArr(List<LoopBound> list) {
        JSONArray json = new JSONArray();
        for (LoopBound lb : list) {
            json.put(LoopBoundSaverLoaderHelper.loopBoundToJSON(lb));
        }
        return json;
    }

    private static List<LoopBound> loopBoundListFromJSONArr(
            JSONArray jsonArray) {
        List<LoopBound> list = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i) {
            list.add(LoopBoundSaverLoaderHelper
                    .loopBoundFromJSON(jsonArray.getJSONObject(i)));
        }

        return list;
    }

    private static JSONObject cbmcTestRunToJSON(CBMCTestRunWithSymbolicVars run,
            RelativePathConverter pathHandler) {
        JSONObject json = new JSONObject();

        json.put(AMT_VOTER_KEY, run.getV());
        json.put(AMT_CANDS_KEY, run.getC());
        json.put(AMT_SEATS_KEY, run.getS());

        json.put(TEST_RUN_LOGS_KEY, run.getTestOutput());
        json.put(CBMC_CODE_FILE_DATA_KEY, CBMCCodeFileDataSaverLoaderHelper
                .cbmcCodeFileToJSON(run.getCbmcCodeFile(), pathHandler));

        json.put(CODE_GEN_OPTIONS_KEY, CodeGenOptionsSaverLoaderHelper
                .codeGenOptionsToJSON(run.getCodeGenOptions()));

        json.put(LOOP_BOUND_LIST_KEY, run.getLoopboundList());
        json.put(STATE_KEY, run.getState().toString());

        return json;
    }

    private static CBMCTestRunWithSymbolicVars cbmcTestRunFromJSON(
            JSONObject json, CElectionDescription descr,
            PreAndPostConditionsDescription propDescr, CBMCTestConfiguration tc,
            RelativePathConverter pathHandler) {

        int v = json.getInt(AMT_VOTER_KEY);
        int c = json.getInt(AMT_CANDS_KEY);
        int s = json.getInt(AMT_SEATS_KEY);

        CBMCCodeFileData codeFileData = CBMCCodeFileDataSaverLoaderHelper
                .cbmcCodeFileFromJSON(
                        json.getJSONObject(CBMC_CODE_FILE_DATA_KEY),
                        pathHandler);

        CodeGenOptions codeGenOptions = CodeGenOptionsSaverLoaderHelper
                .codeGenOptionsFromJSON(
                        json.getJSONObject(CODE_GEN_OPTIONS_KEY));
        String loopbounds = json.getString(LOOP_BOUND_LIST_KEY);

        CBMCTestRunWithSymbolicVars cbmcTestRun = new CBMCTestRunWithSymbolicVars(
                v, s, c, codeGenOptions, loopbounds, codeFileData, descr,
                propDescr, tc);

        String testRunLogs = json.getString(TEST_RUN_LOGS_KEY);
        cbmcTestRun.setTestRunLogs(testRunLogs);

        WorkUnitState state = WorkUnitState.valueOf(json.getString(STATE_KEY));
        cbmcTestRun.setPrevState(state);

        return cbmcTestRun;
    }

    public static JSONArray cbmcTestRunListToJSON(
            List<CBMCTestRunWithSymbolicVars> runs,
            RelativePathConverter pathHandler) {
        JSONArray arr = new JSONArray();

        for (CBMCTestRunWithSymbolicVars r : runs) {
            arr.put(cbmcTestRunToJSON(r, pathHandler));
        }

        return arr;
    }

    public static List<CBMCTestRunWithSymbolicVars> cbmcTestRunListFromJsonArr(
            JSONArray arr, CElectionDescription descr,
            PreAndPostConditionsDescription propDescr, CBMCTestConfiguration tc,
            RelativePathConverter pathHandler) {
        List<CBMCTestRunWithSymbolicVars> runs = new ArrayList<>();
        for (int i = 0; i < arr.length(); ++i) {
            runs.add(cbmcTestRunFromJSON(arr.getJSONObject(i), descr, propDescr,
                    tc, pathHandler));
        }
        return runs;
    }

}
