package edu.pse.beast.api.runner.propertycheck.run;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.PropertyCheckCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PreAndPostConditions;
import edu.pse.beast.api.runner.codefile.CodeFileData;
import edu.pse.beast.api.runner.propertycheck.PropertyCheckWorkUnit;
import edu.pse.beast.api.runner.propertycheck.output.JSONMessage;
import edu.pse.beast.api.runner.propertycheck.output.JSONRunningDataExtractor;
import edu.pse.beast.api.runner.threadpool.WorkUnitState;
import edu.pse.beast.api.trace.CounterExample;
import edu.pse.beast.api.trace.JSONResultExtractor;
import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;

/**
 * Contains all data to start a cbmc check for the codefile.
 *
 * @author Holger Klein
 *
 */
public class PropertyCheckRun implements PropertyCheckCallback {
    private static final String LINE_BREAK = "\n";

    private static final String SAT = "Verification failed";
    private static final String UNSAT = "Verification succeeded";

    private CElectionDescription description;
    private PreAndPostConditions propertyDescription;

    private PropertyCheckWorkUnit workUnit;
    private WorkUnitState previousState;

    private int v;
    private int s;
    private int c;

    private CodeGenOptions codeGenerationOptions;

    private CodeGenLoopBoundHandler loopBounds;

    private CodeFileData codeFile;

    private List<String> runLogs = new ArrayList<String>();

    private boolean descrChanged;
    private boolean propDescrChanged;

    private PropertyCheckCallback callBack;

    private JSONResultExtractor jsonExampleExtractor;

    private Configuration configuration;

    private JSONRunningDataExtractor jsonRunningDataExtractor;

    public PropertyCheckRun(final BoundValues bounds,
                        final CodeGenOptions codeGenOptions,
                        final CodeGenLoopBoundHandler loopbounds,
                        final CodeFileData codeFileData,
                        final CElectionDescription descr,
                        final PreAndPostConditions propDescr,
                        final Configuration tc) {
        v = bounds.voters;
        s = bounds.seats;
        c = bounds.candidates;
        this.codeGenerationOptions = codeGenOptions;
        this.codeFile = codeFileData;
        this.description = descr;
        this.propertyDescription = propDescr;
        this.loopBounds = loopbounds;
        this.configuration = tc;
        jsonRunningDataExtractor =
                new JSONRunningDataExtractor(descr, propDescr, bounds.seats, bounds.candidates,
                                             bounds.voters, codeFileData.getCodeInfo());
        jsonExampleExtractor =
                new JSONResultExtractor(descr, propDescr, codeFileData.getCodeInfo(),
                                                   bounds.seats, bounds.candidates, bounds.voters);
    }

    public final JSONResultExtractor getJsonOutputHandler() {
        return jsonExampleExtractor;
    }

    public final void setJsonOutputHandler(final JSONResultExtractor jsonOutputHandler) {
        this.jsonExampleExtractor = jsonOutputHandler;
    }

    public final void setPrevState(final WorkUnitState prevState) {
        if (prevState == WorkUnitState.FINISHED) {
            this.previousState = prevState;
        }
    }

    @Override
    public final void onPropertyCheckRawOutput(final String sessionUUID,
                                               final CElectionDescription descr,
                                               final PreAndPostConditions propertyDescr,
                                               final BoundValues bounds,
                                               final String uuid, final String output) {
        runLogs.add(output);
        final JSONMessage msg = jsonRunningDataExtractor.appendOutput(output);
        if (msg != null && callBack != null) {
            callBack.onNewCBMCMessage(msg);
        }
        if (callBack != null) {
            callBack.onPropertyCheckRawOutput(sessionUUID, descr, propertyDescr,
                                             bounds, uuid, output);
        }
    }

    @Override
    public final void onPropertyCheckFinished(final CElectionDescription descr,
                                              final PreAndPostConditions propertyDescr,
                                              final BoundValues bounds, final String uuid) {
        jsonExampleExtractor.processJSONOutput(runLogs);
        if (callBack != null) {
            callBack.onPropertyCheckFinished(descr, propertyDescr, bounds, uuid);
        }
    }

    @Override
    public final void onPropertyCheckStart(final CElectionDescription descr,
                                           final PreAndPostConditions propertyDescr,
                                           final BoundValues bounds, final String uuid) {
        this.runLogs.clear();
        if (callBack != null) {
            callBack.onPropertyCheckStart(descr, propertyDescr, bounds, uuid);
        }
    }

    @Override
    public final void onPropertyCheckStopped(final CElectionDescription descr,
                                            final PreAndPostConditions propertyDescr,
                                            final BoundValues bounds, final String uuid) {
        if (callBack != null) {
            callBack.onPropertyCheckStopped(descr, propertyDescr, bounds, uuid);
        }
    }

    public final List<JSONMessage> getMessagesAsList() {
        return jsonRunningDataExtractor.getMessages();
    }

    public final void setAndInitializeWorkUnit(final PropertyCheckWorkUnit checkWorkUnit,
                                               final PathHandler pathHandler) {
        if (checkWorkUnit.getProcessStarterSource() == null) {
            return;
        }
        final BoundValues bounds = new BoundValues(c, s, v);
        final PropertyCheckWorkUnit.ElectionAndProperty elecAndProp =
                new PropertyCheckWorkUnit.ElectionAndProperty(description, propertyDescription);
        checkWorkUnit.initialize(bounds, codeGenerationOptions, loopBounds,
                                codeFile, elecAndProp, this, pathHandler);
        if (previousState != null) {
            checkWorkUnit.setState(previousState);
            previousState = null;
        }
        this.workUnit = checkWorkUnit;
    }

    public final CodeFileData getCodeFile() {
        return codeFile;
    }

    public final WorkUnitState getState() {
        if (workUnit == null) {
            return WorkUnitState.NO_WORK_UNIT;
        }
        return workUnit.getState();
    }

    public final PropertyCheckWorkUnit getWorkUnit() {
        return workUnit;
    }

    public final String getOutput() {
        synchronized (runLogs) {
            return String.join(LINE_BREAK, runLogs);
        }
    }

    public final void setRunLogs(final String logs) {
        final List<String> list = new ArrayList<String>();
        final String[] arr = logs.split(LINE_BREAK);
        for (int i = 0; i < arr.length; ++i) {
            list.add(arr[i]);
        }
        this.runLogs = list;
        jsonRunningDataExtractor.initializeWithRawOutput(runLogs);
        jsonExampleExtractor.processJSONOutput(runLogs);
    }

    public final void handleDescrCodeChange() {
        descrChanged = true;
    }

    public final boolean isDescrChanged() {
        return descrChanged;
    }

    public final void handlePropDescrChanged() {
        propDescrChanged = true;
    }

    public final boolean isPropDescrChanged() {
        return propDescrChanged;
    }

    public final void updateDataForCheck(final CodeFileData cFile,
                                         final CodeGenOptions codeGenOptions) {
        this.codeFile = cFile;
        this.loopBounds = cFile.getCodeInfo().getLoopBoundHandler();
        workUnit.updateDataForCheck(cFile, loopBounds, codeGenOptions);
        descrChanged = false;
        propDescrChanged = false;
    }

    public final void setCb(final PropertyCheckCallback cb) {
        this.callBack = cb;
    }

    public final CodeGenLoopBoundHandler getLoopboundList() {
        return loopBounds;
    }

    public final void setLoopboundList(final CodeGenLoopBoundHandler loopBoundList) {
        this.loopBounds = loopBoundList;
    }

    public final CodeGenOptions getCodeGenerationOptions() {
        return codeGenerationOptions;
    }

    public final void setCodeGenOptions(final CodeGenOptions codeGenOptions) {
        this.codeGenerationOptions = codeGenOptions;
    }

    public final int getV() {
        return v;
    }

    public final int getS() {
        return s;
    }

    public final int getC() {
        return c;
    }

    public final void setV(final int voteAmount) {
        v = voteAmount;
    }

    public final void setS(final int seatAmount) {
        s = seatAmount;
    }

    public final void setC(final int candidateAmount) {
        c = candidateAmount;
    }

    public final CElectionDescription getDescr() {
        return description;
    }

    public final PreAndPostConditions getPropDescr() {
        return propertyDescription;
    }

    public final Configuration getTc() {
        return configuration;
    }

    public final String getStatusString() {
        String status = getState().toString();
        if (getState() == WorkUnitState.FINISHED) {
            if (getJsonOutputHandler().didFindCounterExample()) {
                status = SAT;
            } else {
                status = UNSAT;
            }
        }
        return status.substring(0, 1) + status.toLowerCase().substring(1);
    }

    final CounterExample getGeneratedExample() {
        return jsonExampleExtractor.getGeneratedExample();
    }

    public final boolean didFindCounterExample() {
        return jsonExampleExtractor.didFindCounterExample();
    }
}
