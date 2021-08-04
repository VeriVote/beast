package edu.pse.beast.api.testrunner.propertycheck.symbolic_vars;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonRunningDataExtractor;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCCounterExample;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCJsonResultExampleExtractor;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

/**
 * Contains all data to start a cbmc check for the codefile.
 *
 * @author Holger Klein
 *
 */
public class CBMCTestRunWithSymbolicVars implements CBMCTestCallback {
    private static final String LINE_BREAK = "\n";

    private static final String SAT = "Verification failed";
    private static final String UNSAT = "Verification succeeded";

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;

    private CBMCPropertyCheckWorkUnit workUnit;
    private WorkUnitState previousState;

    private int v;
    private int s;
    private int c;

    private CodeGenOptions codeGenerationOptions;

    private CodeGenLoopBoundHandler loopBounds;

    private CBMCCodeFileData cbmcCodeFile;

    private List<String> testRunLogs = new ArrayList<>();

    private boolean descrChanged;
    private boolean propDescrChanged;

    private CBMCTestCallback callBack;

    private CBMCJsonResultExampleExtractor cmbcJsonExampleExtractor;

    private CBMCTestConfiguration testConfiguration;

    private CBMCJsonRunningDataExtractor cbmcJsonRunningDataExtractor;

    public CBMCTestRunWithSymbolicVars(final BoundValues bounds,
                                       final CodeGenOptions codeGenOptions,
                                       final CodeGenLoopBoundHandler loopbounds,
                                       final CBMCCodeFileData codeFileData,
                                       final CElectionDescription descr,
                                       final PreAndPostConditionsDescription propDescr,
                                       final CBMCTestConfiguration tc) {
        v = bounds.voters;
        s = bounds.seats;
        c = bounds.candidates;
        this.codeGenerationOptions = codeGenOptions;
        this.cbmcCodeFile = codeFileData;
        this.description = descr;
        this.propertyDescription = propDescr;
        this.loopBounds = loopbounds;
        this.testConfiguration = tc;
        cbmcJsonRunningDataExtractor =
                new CBMCJsonRunningDataExtractor(descr, propDescr, bounds.seats, bounds.candidates,
                                                 bounds.voters, codeFileData.getCodeInfo());
        cmbcJsonExampleExtractor =
                new CBMCJsonResultExampleExtractor(descr, propDescr, codeFileData.getCodeInfo(),
                                                   bounds.seats, bounds.candidates, bounds.voters);
    }

    public final CBMCJsonResultExampleExtractor getJsonOutputHandler() {
        return cmbcJsonExampleExtractor;
    }

    public final void setJsonOutputHandler(final CBMCJsonResultExampleExtractor jsonOutputHandler) {
        this.cmbcJsonExampleExtractor = jsonOutputHandler;
    }

    public final void setPrevState(final WorkUnitState prevState) {
        if (prevState == WorkUnitState.FINISHED) {
            this.previousState = prevState;
        }
    }

    @Override
    public final void onPropertyTestRawOutput(final String sessionUUID,
                                              final CElectionDescription descr,
                                              final PreAndPostConditionsDescription propertyDescr,
                                              final BoundValues bounds,
                                              final String uuid, final String output) {
        testRunLogs.add(output);
        final CBMCJsonMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        if (msg != null && callBack != null) {
            callBack.onNewCBMCMessage(msg);
        }
        if (callBack != null) {
            callBack.onPropertyTestRawOutput(sessionUUID, descr, propertyDescr,
                                             bounds, uuid, output);
        }
    }

    @Override
    public final void onPropertyTestFinished(final CElectionDescription descr,
                                             final PreAndPostConditionsDescription propertyDescr,
                                             final BoundValues bounds, final String uuid) {
        cmbcJsonExampleExtractor.processCBMCJsonOutput(testRunLogs);
        if (callBack != null) {
            callBack.onPropertyTestFinished(descr, propertyDescr, bounds, uuid);
        }
    }

    @Override
    public final void onPropertyTestStart(final CElectionDescription descr,
                                          final PreAndPostConditionsDescription propertyDescr,
                                          final BoundValues bounds, final String uuid) {
        this.testRunLogs.clear();

        if (callBack != null) {
            callBack.onPropertyTestStart(descr, propertyDescr, bounds, uuid);
        }
    }

    @Override
    public final void onPropertyTestStopped(final CElectionDescription descr,
                                            final PreAndPostConditionsDescription propertyDescr,
                                            final BoundValues bounds, final String uuid) {
        if (callBack != null) {
            callBack.onPropertyTestStopped(descr, propertyDescr, bounds, uuid);
        }
    }

    public final List<CBMCJsonMessage> getMessagesAsList() {
        return cbmcJsonRunningDataExtractor.getMessages();
    }

    public final void setAndInitializeWorkUnit(final CBMCPropertyCheckWorkUnit cbmcWorkUnit,
                                               final PathHandler pathHandler) {
        if (cbmcWorkUnit.getProcessStarterSource() == null) {
            return;
        }
        final BoundValues bounds = new BoundValues(c, s, v);
        final CBMCPropertyCheckWorkUnit.ElectionAndProperty elecAndProp =
                new CBMCPropertyCheckWorkUnit.ElectionAndProperty(description, propertyDescription);
        cbmcWorkUnit.initialize(bounds, codeGenerationOptions, loopBounds,
                                cbmcCodeFile, elecAndProp, this, pathHandler);
        if (previousState != null) {
            cbmcWorkUnit.setState(previousState);
            previousState = null;
        }
        this.workUnit = cbmcWorkUnit;
    }

    public final CBMCCodeFileData getCbmcCodeFile() {
        return cbmcCodeFile;
    }

    public final WorkUnitState getState() {
        if (workUnit == null) {
            return WorkUnitState.NO_WORK_UNIT;
        }
        return workUnit.getState();
    }

    public final CBMCPropertyCheckWorkUnit getWorkUnit() {
        return workUnit;
    }

    public final String getTestOutput() {
        synchronized (testRunLogs) {
            return String.join(LINE_BREAK, testRunLogs);
        }
    }

    public final void setTestRunLogs(final String logs) {
        final List<String> list = new ArrayList<>();
        final String[] arr = logs.split(LINE_BREAK);
        for (int i = 0; i < arr.length; ++i) {
            list.add(arr[i]);
        }
        this.testRunLogs = list;
        cbmcJsonRunningDataExtractor.initializeWithRawOutput(testRunLogs);
        cmbcJsonExampleExtractor.processCBMCJsonOutput(testRunLogs);
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

    public final void updateDataForCheck(final CBMCCodeFileData cbmcFile,
                                         final CodeGenOptions codeGenOptions) {
        this.cbmcCodeFile = cbmcFile;
        this.loopBounds = cbmcFile.getCodeInfo().getLoopBoundHandler();
        workUnit.updateDataForCheck(cbmcFile, loopBounds, codeGenOptions);
        descrChanged = false;
        propDescrChanged = false;
    }

    public final void setCb(final CBMCTestCallback cb) {
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

    public final PreAndPostConditionsDescription getPropDescr() {
        return propertyDescription;
    }

    public final CBMCTestConfiguration getTc() {
        return testConfiguration;
    }

    public final String getStatusString() {
        String status = getState().toString();
        if (getState() == WorkUnitState.FINISHED) {
            if (getJsonOutputHandler().didCBMCFindExample()) {
                status = SAT;
            } else {
                status = UNSAT;
            }
        }
        return status;
    }

    final CBMCCounterExample getGeneratedExample() {
        return cmbcJsonExampleExtractor.getGeneratedExample();
    }

    public final boolean didFindCounterExample() {
        return cmbcJsonExampleExtractor.didCBMCFindExample();
    }
}
