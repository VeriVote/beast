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

/**
 * This class can run a cbmc check given a Java Thread.
 * Uses a {@link CBMCProcessHandlerSource} to
 * generate a cbmc process and pass it output onto
 * a {@link CBMCTestCallback}.
 *
 * @author Holger Klein
 *
 */
public class CBMCPropertyCheckWorkUnit implements WorkUnit {
    // fields to start the election check
    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;
    private int voterAmount;
    private int candidateAmount;
    private int seatAmount;
    private CBMCCodeFileData cbmcCodeFile;
    private String loopBounds;
    private CodeGenOptions codeGenerationOptions;

    // fields to handle the workunit state
    private CBMCTestCallback callBack;

    private String uuid;
    private String sessionUUID;

    private CBMCProcessHandlerSource processStarterSource;
    private Process process;

    private WorkUnitState state;
    private PathHandler pathHandler;

    public CBMCPropertyCheckWorkUnit(final CBMCProcessHandlerSource source,
                                     final String sessionUUIDString) {
        this.uuid = UUID.randomUUID().toString();
        this.processStarterSource = source;
        this.sessionUUID = sessionUUIDString;
        this.state = WorkUnitState.CREATED;
    }

    public void initialize(final int v, final int s, final int c,
                           final CodeGenOptions codeGenOptions,
                           final String loopBoundsString,
                           final CBMCCodeFileData codeFile,
                           final CElectionDescription descr,
                           final PreAndPostConditionsDescription propDescr,
                           final CBMCTestCallback cb,
                           final PathHandler handler) {
        this.pathHandler = handler;
        this.description = descr;
        this.propertyDescription = propDescr;
        this.voterAmount = v;
        this.candidateAmount = c;
        this.seatAmount = s;
        this.cbmcCodeFile = codeFile;
        this.loopBounds = loopBoundsString;
        this.codeGenerationOptions = codeGenOptions;
        this.callBack = cb;
        this.state = WorkUnitState.INITIALIZED;
    }

    public void setState(final WorkUnitState workUnitState) {
        this.state = workUnitState;
    }

    public int getC() {
        return candidateAmount;
    }

    public int getS() {
        return seatAmount;
    }

    public int getV() {
        return voterAmount;
    }

    public void setCallback(final CBMCTestCallback cb) {
        this.callBack = cb;
    }

    public boolean hasCallback() {
        return this.callBack != null;
    }

    public CBMCProcessHandlerSource getProcessStarterSource() {
        return processStarterSource;
    }

    public void updateDataForCheck(final CBMCCodeFileData cbmcFile,
                                   final String loopBoundsString,
                                   final CodeGenOptions codeGenOptions) {
        this.cbmcCodeFile = cbmcFile;
        this.loopBounds = loopBoundsString;
        this.codeGenerationOptions = codeGenOptions;
        this.state = WorkUnitState.INITIALIZED;
    }

    @Override
    public void doWork() {
        if (!processStarterSource.hasProcessHandler()) {
            return;
        }
        state = WorkUnitState.WORKED_ON;
        callBack.onPropertyTestStart(description, propertyDescription,
                                     seatAmount, candidateAmount,
                                     voterAmount, uuid);
        try {
            process =
                    processStarterSource.getProcessHandler()
                    .startCheckForParam(sessionUUID, voterAmount, candidateAmount,
                                        seatAmount, sessionUUID, callBack,
                                        cbmcCodeFile.getFile(), loopBounds,
                                        codeGenerationOptions, pathHandler);
            final BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            final List<String> cbmcOutput = new ArrayList<>();
            try {
                while ((line = reader.readLine()) != null) {
                    callBack.onPropertyTestRawOutput(sessionUUID, description,
                                                     propertyDescription,
                                                     seatAmount, candidateAmount,
                                                     voterAmount, uuid, line);
                    cbmcOutput.add(line);
                }
            } catch (IOException e) {
                // TODO errorhandling
                e.printStackTrace();
                state = WorkUnitState.STOPPED;
                processStarterSource.getProcessHandler().endProcess(process);
                return;
            }
            callBack.onPropertyTestRawOutputComplete(description, propertyDescription,
                                                     seatAmount, candidateAmount,
                                                     voterAmount, uuid, cbmcOutput);
            state = WorkUnitState.FINISHED;
            callBack.onPropertyTestFinished(description, propertyDescription,
                                            seatAmount, candidateAmount,
                                            voterAmount, uuid);
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
        callBack.onPropertyTestStopped(description, propertyDescription,
                                       seatAmount, candidateAmount,
                                       voterAmount, uuid);
    }

    @Override
    public void addedToQueue() {
        state = WorkUnitState.ON_QUEUE;
        callBack.onPropertyTestAddedToQueue(description, propertyDescription,
                                            seatAmount, candidateAmount,
                                            voterAmount, uuid);
    }

    @Override
    public WorkUnitState getState() {
        return state;
    }

    public CBMCCodeFileData getCbmcFile() {
        return cbmcCodeFile;
    }

    public void setCbmcFile(final CBMCCodeFileData cbmcFile) {
        this.cbmcCodeFile = cbmcFile;
    }

    public void shutdown() {
        process.destroyForcibly();
    }
}
