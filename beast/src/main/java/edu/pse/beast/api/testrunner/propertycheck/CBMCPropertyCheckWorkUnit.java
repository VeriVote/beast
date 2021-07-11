package edu.pse.beast.api.testrunner.propertycheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerSource;
import edu.pse.beast.api.testrunner.threadpool.WorkUnit;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;

public class CBMCPropertyCheckWorkUnit implements WorkUnit {
    // fields to start the election check
    CElectionDescription descr;
    PreAndPostConditionsDescription propDescr;
    int v, c, s;
    CBMCCodeFileData cbmcCodeFile;
    private String loopBounds;
    private CodeGenOptions codeGenOptions;

    // fields to handle the workunit state
    CBMCTestCallback cb;

    String uuid;
    private String sessionUUID;

    CBMCProcessHandlerSource processStarterSource;
    private Process process;

    private WorkUnitState state;
    private PathHandler pathHandler;

    public CBMCPropertyCheckWorkUnit(
            CBMCProcessHandlerSource processStarterSource, String sessionUUID) {
        this.uuid = UUID.randomUUID().toString();
        this.processStarterSource = processStarterSource;
        this.sessionUUID = sessionUUID;
        this.state = WorkUnitState.CREATED;
    }

    public void initialize(int v, int s, int c, CodeGenOptions codeGenOptions,
            String loopBounds, CBMCCodeFileData cbmcCodeFile,
            CElectionDescription descr,
            PreAndPostConditionsDescription propDescr, CBMCTestCallback cb,
            PathHandler pathHandler) {
        this.pathHandler = pathHandler;
        this.descr = descr;
        this.propDescr = propDescr;
        this.v = v;
        this.c = c;
        this.s = s;
        this.cbmcCodeFile = cbmcCodeFile;
        this.loopBounds = loopBounds;
        this.codeGenOptions = codeGenOptions;
        this.cb = cb;
        this.state = WorkUnitState.INITIALIZED;
    }

    public void setState(WorkUnitState state) {
        this.state = state;
    }

    public int getC() {
        return c;
    }

    public int getS() {
        return s;
    }

    public int getV() {
        return v;
    }

    public void setCallback(CBMCTestCallback cb) {
        this.cb = cb;
    }

    public boolean hasCallback() {
        return this.cb != null;
    }

    public CBMCProcessHandlerSource getProcessStarterSource() {
        return processStarterSource;
    }

    public void updateDataForCheck(CBMCCodeFileData cbmcFile, String loopBounds,
            CodeGenOptions codeGenOptions) {
        this.cbmcCodeFile = cbmcFile;
        this.loopBounds = loopBounds;
        this.codeGenOptions = codeGenOptions;
        this.state = WorkUnitState.INITIALIZED;
    }

    @Override
    public void doWork() {
        if (!processStarterSource.hasProcessHandler()) {
            return;
        }
        state = WorkUnitState.WORKED_ON;
        cb.onPropertyTestStart(descr, propDescr, s, c, v, uuid);
        try {
            process = processStarterSource.getProcessHandler()
                    .startCheckForParam(sessionUUID, v, c, s, sessionUUID, cb,
                            cbmcCodeFile.getFile(), loopBounds, codeGenOptions,
                            pathHandler);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            List<String> cbmcOutput = new ArrayList<>();
            try {
                while ((line = reader.readLine()) != null) {
                    cb.onPropertyTestRawOutput(sessionUUID, descr, propDescr, s,
                            c, v, uuid, line);
                    cbmcOutput.add(line);
                }
            } catch (IOException e) {
                // TODO errorhandling
                e.printStackTrace();
                state = WorkUnitState.STOPPED;
                processStarterSource.getProcessHandler().endProcess(process);
                return;
            }
            cb.onPropertyTestRawOutputComplete(descr, propDescr, s, c, v, uuid,
                    cbmcOutput);
            state = WorkUnitState.FINISHED;
            cb.onPropertyTestFinished(descr, propDescr, s, c, v, uuid);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            state = WorkUnitState.STOPPED;
            processStarterSource.getProcessHandler().endProcess(process);
        }
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public void interrupt() {
        processStarterSource.getProcessHandler().endProcess(process);
        state = WorkUnitState.STOPPED;
        cb.onPropertyTestStopped(descr, propDescr, s, c, v, uuid);
    }

    @Override
    public void addedToQueue() {
        state = WorkUnitState.ON_QUEUE;
        cb.onPropertyTestAddedToQueue(descr, propDescr, s, c, v, uuid);
    }

    @Override
    public WorkUnitState getState() {
        return state;
    }

    public CBMCCodeFileData getCbmcFile() {
        return cbmcCodeFile;
    }

    public void setCbmcFile(CBMCCodeFileData cbmcFile) {
        this.cbmcCodeFile = cbmcFile;
    }

    public void shutdown() {
        process.destroyForcibly();
    }
}
