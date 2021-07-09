package edu.pse.beast.api.testrunner.propertycheck.symbolic_vars;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonRunningDataExtractor;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCCounterExample;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCJsonResultExampleExtractor;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class CBMCTestRunWithSymbolicVars implements CBMCTestCallback {

    private CElectionDescription descr;
    private PreAndPostConditionsDescription propDescr;

    private CBMCPropertyCheckWorkUnit workUnit;
    private WorkUnitState prevState;

    private int V;
    private int S;
    private int C;

    private CodeGenOptions codeGenOptions;

    private String loopboundList;

    private CBMCCodeFileData cbmcCodeFile;

    private List<String> testRunLogs = new ArrayList<>();

    private boolean descrChanged = false;
    private boolean propDescrChanged = false;

    private CBMCTestCallback cb;

    private CBMCJsonResultExampleExtractor cmbcJsonExampleExtractor;

    private CBMCTestConfiguration tc;

    private CBMCJsonRunningDataExtractor cbmcJsonRunningDataExtractor;

    public CBMCTestRunWithSymbolicVars(int v, int s, int c,
            CodeGenOptions codeGenOptions, String loopbounds,
            CBMCCodeFileData cbmcCodeFile, CElectionDescription descr,
            PreAndPostConditionsDescription propDescr,
            CBMCTestConfiguration tc) {
        V = v;
        S = s;
        C = c;
        this.codeGenOptions = codeGenOptions;
        this.cbmcCodeFile = cbmcCodeFile;
        this.descr = descr;
        this.propDescr = propDescr;
        this.loopboundList = loopbounds;
        this.tc = tc;
        cbmcJsonRunningDataExtractor = new CBMCJsonRunningDataExtractor(descr,
                propDescr, s, c, v, cbmcCodeFile.getCodeInfo());
        cmbcJsonExampleExtractor = new CBMCJsonResultExampleExtractor(descr,
                propDescr, cbmcCodeFile.getCodeInfo(), s, c, v);
    }

    public CBMCJsonResultExampleExtractor getJsonOutputHandler() {
        return cmbcJsonExampleExtractor;
    }

    public void setJsonOutputHandler(
            CBMCJsonResultExampleExtractor jsonOutputHandler) {
        this.cmbcJsonExampleExtractor = jsonOutputHandler;
    }

    public void setPrevState(WorkUnitState prevState) {
        if (prevState == WorkUnitState.FINISHED) {
            this.prevState = prevState;
        }
    }

    @Override
    public void onPropertyTestRawOutput(String sessionUUID,
            CElectionDescription description,
            PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
            String uuid, String output) {
        testRunLogs.add(output);
        CBMCJsonMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        if (msg != null) {
            if (cb != null)
                cb.onNewCBMCMessage(msg);
        }
        if (cb != null)
            cb.onPropertyTestRawOutput(sessionUUID, description, propertyDescr,
                    s, c, v, uuid, output);
    }

    @Override
    public void onPropertyTestFinished(CElectionDescription description,
            PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
            String uuid) {
        cmbcJsonExampleExtractor.processCBMCJsonOutput(testRunLogs);
        if (cb != null)
            cb.onPropertyTestFinished(description, propertyDescr, s, c, v,
                    uuid);
    }

    @Override
    public void onPropertyTestStart(CElectionDescription description,
            PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
            String uuid) {
        this.testRunLogs.clear();

        if (cb != null)
            cb.onPropertyTestStart(description, propertyDescr, s, c, v, uuid);
    }

    @Override
    public void onPropertyTestStopped(CElectionDescription descr,
            PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
            String uuid) {
        if (cb != null)
            cb.onPropertyTestStopped(descr, propertyDescr, s, c, v, uuid);
    }

    public List<CBMCJsonMessage> getMessagesAsList() {
        return cbmcJsonRunningDataExtractor.getMessages();
    }

    public void setAndInitializeWorkUnit(CBMCPropertyCheckWorkUnit workUnit,
            PathHandler pathHandler) {
        if (workUnit.getProcessStarterSource() == null)
            return;
        workUnit.initialize(V, S, C, codeGenOptions, loopboundList,
                cbmcCodeFile, descr, propDescr, this, pathHandler);
        if (prevState != null) {
            workUnit.setState(prevState);
            prevState = null;
        }
        this.workUnit = workUnit;
    }

    public CBMCCodeFileData getCbmcCodeFile() {
        return cbmcCodeFile;
    }

    public WorkUnitState getState() {
        if (workUnit == null)
            return WorkUnitState.NO_WORK_UNIT;
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

    public void setTestRunLogs(String logs) {
        List<String> list = new ArrayList<>();
        String arr[] = logs.split("\n");
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

    public void updateDataForCheck(CBMCCodeFileData cbmcFile,
            CodeGenOptions codeGenOptions) {
        this.cbmcCodeFile = cbmcFile;
        this.loopboundList = cbmcFile.getCodeInfo().getLoopBoundHandler()
                .generateCBMCString(V, C, S);
        workUnit.updateDataForCheck(cbmcFile, loopboundList, codeGenOptions);
        descrChanged = false;
        propDescrChanged = false;
    }

    public void setCb(CBMCTestCallback cb) {
        this.cb = cb;
    }

    public String getLoopboundList() {
        return loopboundList;
    }

    public void setLoopboundList(String loopboundList) {
        this.loopboundList = loopboundList;
    }

    public CodeGenOptions getCodeGenOptions() {
        return codeGenOptions;
    }

    public void setCodeGenOptions(CodeGenOptions codeGenOptions) {
        this.codeGenOptions = codeGenOptions;
    }

    public int getV() {
        return V;
    }

    public int getS() {
        return S;
    }

    public int getC() {
        return C;
    }

    public void setV(int v) {
        V = v;
    }

    public void setS(int s) {
        S = s;
    }

    public void setC(int c) {
        C = c;
    }

    public CElectionDescription getDescr() {
        return descr;
    }

    public PreAndPostConditionsDescription getPropDescr() {
        return propDescr;
    }

    public CBMCTestConfiguration getTc() {
        return tc;
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
