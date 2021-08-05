package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.paths.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface CBMCProcessHandler {
    CBMCProcessStarterType getType();

    // at this point, the code file is final. This means the following
    // will not change for this run unless we update it anyways::
    // - names of the constants for V, C, S
    // - loop bound indices
    // we could update the V, C, S amounts if we wanted.
    // however, in this case, just create a new property check imo
    Process startCheckForParam(int v, int c, int s,
                               File cbmcFile,
                               CodeGenLoopBoundHandler loopBounds,
                               CodeGenOptions codeGenOptions,
                               PathHandler pathHandler) throws IOException;

    void endProcess(Process p);
}
