package edu.pse.beast.api.savingloading.testruns;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.CodeGenOptionsSaverLoaderHelper;
import edu.pse.beast.api.savingloading.RelativePathConverter;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCCodeFileDataSaverLoaderHelper;
import edu.pse.beast.api.savingloading.loopbound.LoopBoundHandlerSaverLoaderHelper;
import edu.pse.beast.api.savingloading.loopbound.LoopBoundSaverLoaderHelper;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCTestRunSaverLoaderHelper {
    private static final String CBMC_CODE_FILE_DATA_KEY = "cbmc_code_file";

    private static final String TEST_RUN_LOGS_KEY = "test_run_logs";

    private static final String AMT_VOTER_KEY = "amt_voter";
    private static final String AMT_CANDS_KEY = "amt_cands";
    private static final String AMT_SEATS_KEY = "amt_seats";

    private static final String LOOP_BOUND_LIST_KEY = "loop_bounds";

    private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

    private static final String STATE_KEY = "state";

    private static JSONArray loopBoundListToJSONArr(final List<LoopBound> list) {
        final JSONArray json = new JSONArray();
        for (final LoopBound lb : list) {
            json.put(LoopBoundSaverLoaderHelper.loopBoundToJSON(lb));
        }
        return json;
    }

    private static List<LoopBound> loopBoundListFromJSONArr(final JSONArray jsonArray) {
        final List<LoopBound> list = new ArrayList<LoopBound>();
        for (int i = 0; i < list.size(); ++i) {
            list.add(LoopBoundSaverLoaderHelper.loopBoundFromJSON(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    private static JSONObject cbmcTestRunToJSON(final CBMCTestRunWithSymbolicVars run,
                                                final RelativePathConverter pathHandler) {
        final JSONObject json = new JSONObject();
        json.put(AMT_VOTER_KEY, run.getV());
        json.put(AMT_CANDS_KEY, run.getC());
        json.put(AMT_SEATS_KEY, run.getS());
        json.put(TEST_RUN_LOGS_KEY, run.getTestOutput());
        json.put(CBMC_CODE_FILE_DATA_KEY,
                 CBMCCodeFileDataSaverLoaderHelper
                 .cbmcCodeFileToJSON(run.getCbmcCodeFile(), pathHandler));
        json.put(CODE_GEN_OPTIONS_KEY,
                 CodeGenOptionsSaverLoaderHelper
                 .codeGenOptionsToJSON(run.getCodeGenerationOptions()));
        json.put(LOOP_BOUND_LIST_KEY, run.getLoopboundList());
        json.put(STATE_KEY, run.getState().toString());
        return json;
    }

    private static CBMCTestRunWithSymbolicVars
                cbmcTestRunFromJSON(final JSONObject json, final CElectionDescription descr,
                                    final PreAndPostConditionsDescription propDescr,
                                    final CBMCTestConfiguration tc,
                                    final RelativePathConverter pathHandler) {
        final int v = json.getInt(AMT_VOTER_KEY);
        final int c = json.getInt(AMT_CANDS_KEY);
        final int s = json.getInt(AMT_SEATS_KEY);

        final CBMCCodeFileData codeFileData =
                CBMCCodeFileDataSaverLoaderHelper
                .cbmcCodeFileFromJSON(json.getJSONObject(CBMC_CODE_FILE_DATA_KEY), pathHandler);
        final CodeGenOptions codeGenOptions =
                CodeGenOptionsSaverLoaderHelper
                .codeGenOptionsFromJSON(json.getJSONObject(CODE_GEN_OPTIONS_KEY));
        final CodeGenLoopBoundHandler loopbounds =
                LoopBoundHandlerSaverLoaderHelper.loopBoundHandlerFromJSON(json);
        final CBMCTestRunWithSymbolicVars.BoundValues bounds =
                new CBMCTestRunWithSymbolicVars.BoundValues(c, s, v);
        final CBMCTestRunWithSymbolicVars cbmcTestRun =
                new CBMCTestRunWithSymbolicVars(bounds, codeGenOptions, loopbounds,
                                                codeFileData, descr, propDescr, tc);

        final String testRunLogs = json.getString(TEST_RUN_LOGS_KEY);
        cbmcTestRun.setTestRunLogs(testRunLogs);
        final WorkUnitState state = WorkUnitState.valueOf(json.getString(STATE_KEY));
        cbmcTestRun.setPrevState(state);
        return cbmcTestRun;
    }

    public static JSONArray cbmcTestRunListToJSON(final List<CBMCTestRunWithSymbolicVars> runs,
                                                  final RelativePathConverter pathHandler) {
        final JSONArray arr = new JSONArray();
        for (final CBMCTestRunWithSymbolicVars r : runs) {
            arr.put(cbmcTestRunToJSON(r, pathHandler));
        }
        return arr;
    }

    public static List<CBMCTestRunWithSymbolicVars>
            cbmcTestRunListFromJsonArr(final JSONArray arr, final CElectionDescription descr,
                                       final PreAndPostConditionsDescription propDescr,
                                       final CBMCTestConfiguration tc,
                                       final RelativePathConverter pathHandler) {
        final List<CBMCTestRunWithSymbolicVars> runs = new ArrayList<CBMCTestRunWithSymbolicVars>();
        for (int i = 0; i < arr.length(); ++i) {
            final JSONObject jsonObject = arr.getJSONObject(i);
            runs.add(cbmcTestRunFromJSON(jsonObject, descr, propDescr, tc, pathHandler));
        }
        return runs;
    }

}
