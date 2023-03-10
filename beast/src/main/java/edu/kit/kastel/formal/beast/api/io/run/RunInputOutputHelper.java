package edu.kit.kastel.formal.beast.api.io.run;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.io.CodeGenOptionsInputOutputHelper;
import edu.kit.kastel.formal.beast.api.io.RelativePathConverter;
import edu.kit.kastel.formal.beast.api.io.code.CodeFileDataInputOutputHelper;
import edu.kit.kastel.formal.beast.api.io.loopbound.LoopBoundHandlerInputOutputHelper;
import edu.kit.kastel.formal.beast.api.io.loopbound.LoopBoundInputOutputHelper;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.codefile.CodeFileData;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.kit.kastel.formal.beast.api.runner.threadpool.WorkUnitState;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.Configuration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class RunInputOutputHelper {
    private static final String CODE_FILE_DATA_KEY = "code_file";
    private static final String RUN_LOGS_KEY = "run_logs";
    private static final String AMT_VOTER_KEY = "amt_voter";
    private static final String AMT_CANDS_KEY = "amt_cands";
    private static final String AMT_SEATS_KEY = "amt_seats";
    private static final String LOOP_BOUND_LIST_KEY = "loop_bounds";
    private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";
    private static final String STATE_KEY = "state";

    private static JSONArray loopBoundListToJSONArr(final List<LoopBound> list) {
        final JSONArray json = new JSONArray();
        for (final LoopBound lb : list) {
            json.put(LoopBoundInputOutputHelper.loopBoundToJSON(lb));
        }
        return json;
    }

    private static List<LoopBound> loopBoundListFromJSONArr(final JSONArray jsonArray) {
        final List<LoopBound> list = new ArrayList<LoopBound>();
        for (int i = 0; i < list.size(); ++i) {
            list.add(LoopBoundInputOutputHelper.loopBoundFromJSON(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    private static JSONObject runToJSON(final PropertyCheckRun run,
                                        final RelativePathConverter pathHandler) {
        final JSONObject json = new JSONObject();
        json.put(AMT_VOTER_KEY, run.getV());
        json.put(AMT_CANDS_KEY, run.getC());
        json.put(AMT_SEATS_KEY, run.getS());
        json.put(RUN_LOGS_KEY, run.getOutput());
        json.put(CODE_FILE_DATA_KEY,
                 CodeFileDataInputOutputHelper
                 .codeFileToJSON(run.getCodeFile(), pathHandler));
        json.put(CODE_GEN_OPTIONS_KEY,
                 CodeGenOptionsInputOutputHelper
                 .codeGenOptionsToJSON(run.getCodeGenerationOptions()));
        json.put(LOOP_BOUND_LIST_KEY, run.getLoopboundList());
        json.put(STATE_KEY, run.getState().toString());
        return json;
    }

    private static PropertyCheckRun cbmcRunFromJSON(final JSONObject json,
                                                    final CElectionDescription descr,
                                                    final PropertyDescription propDescr,
                                                    final Configuration tc,
                                                    final RelativePathConverter pathHandler) {
        final int v = json.getInt(AMT_VOTER_KEY);
        final int c = json.getInt(AMT_CANDS_KEY);
        final int s = json.getInt(AMT_SEATS_KEY);

        final CodeFileData codeFileData =
                CodeFileDataInputOutputHelper
                .codeFileFromJSON(json.getJSONObject(CODE_FILE_DATA_KEY), pathHandler);
        final CodeGenOptions codeGenOptions =
                CodeGenOptionsInputOutputHelper
                .codeGenOptionsFromJSON(json.getJSONObject(CODE_GEN_OPTIONS_KEY));
        final CodeGenLoopBoundHandler loopbounds =
                LoopBoundHandlerInputOutputHelper.loopBoundHandlerFromJSON(json);
        final PropertyCheckRun.BoundValues bounds =
                new PropertyCheckRun.BoundValues(c, s, v);
        final PropertyCheckRun cbmcRun =
                new PropertyCheckRun(bounds, codeGenOptions, loopbounds,
                                 codeFileData, descr, propDescr, tc);

        final String runLogs = json.getString(RUN_LOGS_KEY);
        cbmcRun.setRunLogs(runLogs);
        final WorkUnitState state = WorkUnitState.valueOf(json.getString(STATE_KEY));
        cbmcRun.setPrevState(state);
        return cbmcRun;
    }

    public static JSONArray runListToJSON(final List<PropertyCheckRun> runs,
                                          final RelativePathConverter pathHandler) {
        final JSONArray arr = new JSONArray();
        for (final PropertyCheckRun r : runs) {
            arr.put(runToJSON(r, pathHandler));
        }
        return arr;
    }

    public static List<PropertyCheckRun> runListFromJSONArr(final JSONArray arr,
                                                            final CElectionDescription descr,
                                                            final PropertyDescription propDescr,
                                                            final Configuration tc,
                                                            final RelativePathConverter
                                                                    pathHandler) {
        final List<PropertyCheckRun> runs = new ArrayList<PropertyCheckRun>();
        for (int i = 0; i < arr.length(); ++i) {
            final JSONObject jsonObject = arr.getJSONObject(i);
            runs.add(cbmcRunFromJSON(jsonObject, descr, propDescr, tc, pathHandler));
        }
        return runs;
    }
}
