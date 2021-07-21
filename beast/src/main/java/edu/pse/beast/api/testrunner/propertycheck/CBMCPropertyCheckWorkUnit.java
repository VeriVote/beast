package edu.pse.beast.api.testrunner.propertycheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.CBMCTestCallback.BoundValues;
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

    public final void initialize(final CBMCTestCallback.BoundValues bounds,
                                 final CodeGenOptions codeGenOptions,
                                 final String loopBoundsString,
                                 final CBMCCodeFileData codeFile,
                                 final ElectionAndProperty elecAndProp,
                                 final CBMCTestCallback cb,
                                 final PathHandler handler) {
        this.pathHandler = handler;
        this.description = elecAndProp.election;
        this.propertyDescription = elecAndProp.property;
        this.voterAmount = bounds.voters;
        this.candidateAmount = bounds.candidates;
        this.seatAmount = bounds.seats;
        this.cbmcCodeFile = codeFile;
        this.loopBounds = loopBoundsString;
        this.codeGenerationOptions = codeGenOptions;
        this.callBack = cb;
        this.state = WorkUnitState.INITIALIZED;
    }

    public final void setState(final WorkUnitState workUnitState) {
        this.state = workUnitState;
    }

    public final int getC() {
        return candidateAmount;
    }

    public final int getS() {
        return seatAmount;
    }

    public final int getV() {
        return voterAmount;
    }

    public final void setCallback(final CBMCTestCallback cb) {
        this.callBack = cb;
    }

    public final boolean hasCallback() {
        return this.callBack != null;
    }

    public final CBMCProcessHandlerSource getProcessStarterSource() {
        return processStarterSource;
    }

    public final void updateDataForCheck(final CBMCCodeFileData cbmcFile,
                                         final String loopBoundsString,
                                         final CodeGenOptions codeGenOptions) {
        this.cbmcCodeFile = cbmcFile;
        this.loopBounds = loopBoundsString;
        this.codeGenerationOptions = codeGenOptions;
        this.state = WorkUnitState.INITIALIZED;
    }

    @Override
    public final void doWork() {
        if (processStarterSource.hasProcessHandler()) {
            state = WorkUnitState.WORKED_ON;
            final BoundValues bounds = new BoundValues(candidateAmount, seatAmount, voterAmount);
            callBack.onPropertyTestStart(description, propertyDescription,
                                         bounds, uuid);
            try {
                process = processStarterSource.getProcessHandler()
                            .startCheckForParam(voterAmount, candidateAmount, seatAmount,
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
                                                         bounds, uuid, line);
                        cbmcOutput.add(line);
                    }
                } catch (IOException e) {
                    // TODO error handling
                    e.printStackTrace();
                    state = WorkUnitState.STOPPED;
                    processStarterSource.getProcessHandler().endProcess(process);
                    return;
                }
                callBack.onPropertyTestRawOutputComplete(description, propertyDescription,
                                                         bounds, uuid, cbmcOutput);
                state = WorkUnitState.FINISHED;
                callBack.onPropertyTestFinished(description, propertyDescription,
                                                bounds, uuid);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                state = WorkUnitState.STOPPED;
                processStarterSource.getProcessHandler().endProcess(process);
            }
        }
    }

    @Override
    public final String getUUID() {
        return uuid;
    }

    @Override
    public final void interrupt() {
        processStarterSource.getProcessHandler().endProcess(process);
        state = WorkUnitState.STOPPED;
        final BoundValues bounds = new BoundValues(candidateAmount, seatAmount, voterAmount);
        callBack.onPropertyTestStopped(description, propertyDescription,
                                       bounds, uuid);
    }

    @Override
    public final void addedToQueue() {
        state = WorkUnitState.ON_QUEUE;
        final BoundValues bounds = new BoundValues(candidateAmount, seatAmount, voterAmount);
        callBack.onPropertyTestAddedToQueue(description, propertyDescription,
                                            bounds, uuid);
    }

    @Override
    public final WorkUnitState getState() {
        return state;
    }

    public final CBMCCodeFileData getCbmcFile() {
        return cbmcCodeFile;
    }

    public final void setCbmcFile(final CBMCCodeFileData cbmcFile) {
        this.cbmcCodeFile = cbmcFile;
    }

    public final void shutdown() {
        process.destroyForcibly();
    }

    public static final class ElectionAndProperty {
        final CElectionDescription election;
        final PreAndPostConditionsDescription property;

        public ElectionAndProperty(final CElectionDescription descr,
                                   final PreAndPostConditionsDescription propDescr) {
            this.election = descr;
            this.property = propDescr;
        }
    }
}
