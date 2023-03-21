package edu.kit.kastel.formal.beast.api.runner.propertycheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.kit.kastel.formal.beast.api.PropertyCheckCallback;
import edu.kit.kastel.formal.beast.api.PropertyCheckCallback.BoundValues;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.codefile.CodeFileData;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.process.cbmc.CBMCProcessHandlerSource;
import edu.kit.kastel.formal.beast.api.runner.threadpool.WorkUnit;
import edu.kit.kastel.formal.beast.api.runner.threadpool.WorkUnitState;

/**
 * This class can run a cbmc check given a Java Thread.
 * Uses a {@link CBMCProcessHandlerSource} to
 * generate a cbmc process and pass it output onto
 * a {@link PropertyCheckCallback}.
 *
 * @author Holger Klein
 *
 */
public class PropertyCheckWorkUnit implements WorkUnit {
    // fields to start the election check
    private CElectionDescription description;
    private PropertyDescription propertyDescription;
    private int voterAmount;
    private int candidateAmount;
    private int seatAmount;
    private CodeFileData codeFile;
    private CodeGenLoopBoundHandler loopBounds;
    private CodeGenOptions codeGenerationOptions;

    // fields to handle the workunit state
    private PropertyCheckCallback callBack;

    private String uuid;
    private String sessionUUID;

    private CBMCProcessHandlerSource processStarterSource;
    private Process process;

    private WorkUnitState state;
    private PathHandler pathHandler;

    public PropertyCheckWorkUnit(final CBMCProcessHandlerSource source,
                                     final String sessionUUIDString) {
        this.uuid = UUID.randomUUID().toString();
        this.processStarterSource = source;
        this.sessionUUID = sessionUUIDString;
        this.state = WorkUnitState.CREATED;
    }

    public final void initialize(final PropertyCheckCallback.BoundValues bounds,
                                 final CodeGenOptions codeGenOptions,
                                 final CodeGenLoopBoundHandler loopBoundsString,
                                 final CodeFileData file,
                                 final ElectionAndProperty elecAndProp,
                                 final PropertyCheckCallback cb,
                                 final PathHandler handler) {
        this.pathHandler = handler;
        this.description = elecAndProp.election;
        this.propertyDescription = elecAndProp.property;
        this.voterAmount = bounds.voters;
        this.candidateAmount = bounds.candidates;
        this.seatAmount = bounds.seats;
        this.codeFile = file;
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

    public final void setCallback(final PropertyCheckCallback cb) {
        this.callBack = cb;
    }

    public final boolean hasCallback() {
        return this.callBack != null;
    }

    public final CBMCProcessHandlerSource getProcessStarterSource() {
        return processStarterSource;
    }

    public final void updateDataForCheck(final CodeFileData cFile,
                                         final CodeGenLoopBoundHandler loopBoundsString,
                                         final CodeGenOptions codeGenOptions) {
        this.codeFile = cFile;
        this.loopBounds = loopBoundsString;
        this.codeGenerationOptions = codeGenOptions;
        this.state = WorkUnitState.INITIALIZED;
    }

    @Override
    public final void doWork() {
        if (processStarterSource.hasProcessHandler()) {
            state = WorkUnitState.RUNNING;
            final BoundValues bounds = new BoundValues(candidateAmount, seatAmount, voterAmount);
            callBack.onPropertyCheckStart(description, propertyDescription,
                                          bounds, uuid);
            try {
                process = processStarterSource.getProcessHandler()
                            .startCheckForParam(voterAmount, candidateAmount, seatAmount,
                                                codeFile.getFile(), loopBounds,
                                                codeGenerationOptions, pathHandler);
                try (BufferedReader reader =
                        new BufferedReader(new InputStreamReader(process.getInputStream(),
                                                                 StandardCharsets.UTF_8))) {
                    String line;
                    final List<String> output = new ArrayList<String>();
                    while ((line = reader.readLine()) != null) {
                        callBack.onPropertyCheckRawOutput(sessionUUID, description,
                                                          propertyDescription,
                                                          bounds, uuid, line);
                        output.add(line);
                    }
                    callBack.onPropertyCheckRawOutputComplete(description, propertyDescription,
                                                              bounds, uuid, output);
                    state = WorkUnitState.FINISHED;
                    callBack.onPropertyCheckFinished(description, propertyDescription,
                                                     bounds, uuid);
                }
            } catch (IOException e) {
                // TODO error handling
                e.printStackTrace();
                state = WorkUnitState.STOPPED;
                processStarterSource.getProcessHandler().endProcess(process);
                return;
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
        callBack.onPropertyCheckStopped(description, propertyDescription,
                                       bounds, uuid);
    }

    @Override
    public final void addedToQueue() {
        state = WorkUnitState.ON_QUEUE;
        final BoundValues bounds = new BoundValues(candidateAmount, seatAmount, voterAmount);
        callBack.onPropertyCheckAddedToQueue(description, propertyDescription,
                                            bounds, uuid);
    }

    @Override
    public final WorkUnitState getState() {
        return state;
    }

    public final CodeFileData getCodeFile() {
        return codeFile;
    }

    public final void setCodeFile(final CodeFileData cFile) {
        this.codeFile = cFile;
    }

    public final void shutdown() {
        process.destroyForcibly();
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class ElectionAndProperty {
        final CElectionDescription election;
        final PropertyDescription property;

        public ElectionAndProperty(final CElectionDescription descr,
                                   final PropertyDescription propDescr) {
            this.election = descr;
            this.property = propDescr;
        }
    }
}
