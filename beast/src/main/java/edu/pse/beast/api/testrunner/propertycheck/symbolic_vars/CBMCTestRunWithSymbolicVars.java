package edu.pse.beast.api.testrunner.propertycheck.symbolic_vars;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
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
 * Contains all data to start a cbmc check for the codefile
 * @author Holger Klein
 *
 */
public class CBMCTestRunWithSymbolicVars implements CBMCTestCallback {

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;

    private CBMCPropertyCheckWorkUnit workUnit;
    private WorkUnitState previousState;

    private int v;
    private int s;
    private int c;

    private CodeGenOptions codeGenerationOptions;

    private String loopBounds;

    private CBMCCodeFileData cbmcCodeFile;

    private List<String> testRunLogs = new ArrayList<>();

    private boolean descrChanged;
    private boolean propDescrChanged;

    private CBMCTestCallback callBack;

    private CBMCJsonResultExampleExtractor cmbcJsonExampleExtractor;

    private CBMCTestConfiguration testConfiguration;

    private CBMCJsonRunningDataExtractor cbmcJsonRunningDataExtractor;

    public CBMCTestRunWithSymbolicVars(final int voteAmount,
                                       final int seatAmount,
                                       final int candidateAmount,
                                       final CodeGenOptions codeGenOptions,
                                       final String loopbounds,
                                       final CBMCCodeFileData codeFileData,
                                       final CElectionDescription descr,
                                       final PreAndPostConditionsDescription propDescr,
                                       final CBMCTestConfiguration tc) {
        v = voteAmount;
        s = seatAmount;
        c = candidateAmount;
        this.codeGenerationOptions = codeGenOptions;
        this.cbmcCodeFile = codeFileData;
        this.description = descr;
        this.propertyDescription = propDescr;
        this.loopBounds = loopbounds;
        this.testConfiguration = tc;
        cbmcJsonRunningDataExtractor =
                new CBMCJsonRunningDataExtractor(descr, propDescr, seatAmount, candidateAmount,
                                                 voteAmount, codeFileData.getCodeInfo());
        cmbcJsonExampleExtractor =
                new CBMCJsonResultExampleExtractor(descr, propDescr, codeFileData.getCodeInfo(),
                                                   seatAmount, candidateAmount, voteAmount);
    }

    public CBMCJsonResultExampleExtractor getJsonOutputHandler() {
        return cmbcJsonExampleExtractor;
    }

    public void setJsonOutputHandler(final CBMCJsonResultExampleExtractor jsonOutputHandler) {
        this.cmbcJsonExampleExtractor = jsonOutputHandler;
    }

    public void setPrevState(final WorkUnitState prevState) {
        if (prevState == WorkUnitState.FINISHED) {
            this.previousState = prevState;
        }
    }

    @Override
    public void onPropertyTestRawOutput(final String sessionUUID,
                                        final CElectionDescription descr,
                                        final PreAndPostConditionsDescription propertyDescr,
                                        final int seatAmount,
                                        final int candidateAmount,
                                        final int voteAmount,
                                        final String uuid, final String output) {
        testRunLogs.add(output);
        final CBMCJsonMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        if (msg != null && callBack != null) {
            callBack.onNewCBMCMessage(msg);
        }
        if (callBack != null) {
            callBack.onPropertyTestRawOutput(sessionUUID, descr, propertyDescr,
                    seatAmount, candidateAmount, voteAmount, uuid, output);
        }
    }

    @Override
    public void onPropertyTestFinished(final CElectionDescription descr,
                                       final PreAndPostConditionsDescription propertyDescr,
                                       final int seatAmount, final int candidateAmount,
                                       final int voteAmount, final String uuid) {
        cmbcJsonExampleExtractor.processCBMCJsonOutput(testRunLogs);
        if (callBack != null) {
            callBack.onPropertyTestFinished(descr, propertyDescr, seatAmount,
                                      candidateAmount, voteAmount, uuid);
        }
    }

    @Override
    public void onPropertyTestStart(final CElectionDescription descr,
                                    final PreAndPostConditionsDescription propertyDescr,
                                    final int seatAmount, final int candidateAmount,
                                    final int voteAmount, final String uuid) {
        this.testRunLogs.clear();

        if (callBack != null) {
            callBack.onPropertyTestStart(descr, propertyDescr, seatAmount,
                                   candidateAmount, voteAmount, uuid);
        }
    }

    @Override
    public void onPropertyTestStopped(final CElectionDescription descr,
                                      final PreAndPostConditionsDescription propertyDescr,
                                      final int seatAmount, final int candidateAmount,
                                      final int voteAmount, final String uuid) {
        if (callBack != null) {
            callBack.onPropertyTestStopped(descr, propertyDescr, seatAmount,
                                     candidateAmount, voteAmount, uuid);
        }
    }

    public List<CBMCJsonMessage> getMessagesAsList() {
        return cbmcJsonRunningDataExtractor.getMessages();
    }

    public void setAndInitializeWorkUnit(final CBMCPropertyCheckWorkUnit cbmcWorkUnit,
                                         final PathHandler pathHandler) {
        if (cbmcWorkUnit.getProcessStarterSource() == null) {
            return;
        }
        cbmcWorkUnit.initialize(v, s, c, codeGenerationOptions, loopBounds,
                            cbmcCodeFile, description, propertyDescription, this,
                            pathHandler);
        if (previousState != null) {
            cbmcWorkUnit.setState(previousState);
            previousState = null;
        }
        this.workUnit = cbmcWorkUnit;
    }

    public CBMCCodeFileData getCbmcCodeFile() {
        return cbmcCodeFile;
    }

    public WorkUnitState getState() {
        if (workUnit == null) {
            return WorkUnitState.NO_WORK_UNIT;
        }
        return workUnit.getState();
    }

    public CBMCPropertyCheckWorkUnit getWorkUnit() {
        return workUnit;
    }

    public String getTestOutput() {
        synchronized (testRunLogs) {
            return String.join("\n", testRunLogs);
        }
    }

    public void setTestRunLogs(final String logs) {
        final List<String> list = new ArrayList<>();
        final String[] arr = logs.split("\n");
        for (int i = 0; i < arr.length; ++i) {
            list.add(arr[i]);
        }
        this.testRunLogs = list;
        cbmcJsonRunningDataExtractor.initializeWithRawOutput(testRunLogs);
        cmbcJsonExampleExtractor.processCBMCJsonOutput(testRunLogs);
    }

    public void handleDescrCodeChange() {
        descrChanged = true;
    }

    public boolean isDescrChanged() {
        return descrChanged;
    }

    public void handlePropDescrChanged() {
        propDescrChanged = true;
    }

    public boolean isPropDescrChanged() {
        return propDescrChanged;
    }

    public void updateDataForCheck(final CBMCCodeFileData cbmcFile,
                                   final CodeGenOptions codeGenOptions) {
        this.cbmcCodeFile = cbmcFile;
        this.loopBounds = cbmcFile.getCodeInfo().getLoopBoundHandler()
                .generateCBMCString(v, c, s);
        workUnit.updateDataForCheck(cbmcFile, loopBounds, codeGenOptions);
        descrChanged = false;
        propDescrChanged = false;
    }

    public void setCb(final CBMCTestCallback cb) {
        this.callBack = cb;
    }

    public String getLoopboundList() {
        return loopBounds;
    }

    public void setLoopboundList(final String loopBoundList) {
        this.loopBounds = loopBoundList;
    }

    public CodeGenOptions getCodeGenerationOptions() {
        return codeGenerationOptions;
    }

    public void setCodeGenOptions(final CodeGenOptions codeGenOptions) {
        this.codeGenerationOptions = codeGenOptions;
    }

    public int getV() {
        return v;
    }

    public int getS() {
        return s;
    }

    public int getC() {
        return c;
    }

    public void setV(final int voteAmount) {
        v = voteAmount;
    }

    public void setS(final int seatAmount) {
        s = seatAmount;
    }

    public void setC(final int candidateAmount) {
        c = candidateAmount;
    }

    public CElectionDescription getDescr() {
        return description;
    }

    public PreAndPostConditionsDescription getPropDescr() {
        return propertyDescription;
    }

    public CBMCTestConfiguration getTc() {
        return testConfiguration;
    }

    public String getStatusString() {
        String status = getState().toString();
        if (getState() == WorkUnitState.FINISHED) {
            if (getJsonOutputHandler().didCBMCFindExample()) {
                status = "Verification failed";
            } else {
                status = "Verification succeded";
            }
        }
        return status;
    }

    CBMCCounterExample getGeneratedExample() {
        return cmbcJsonExampleExtractor.getGeneratedExample();
    }

    public boolean didFindCounterExample() {
        return cmbcJsonExampleExtractor.didCBMCFindExample();
    }
}
