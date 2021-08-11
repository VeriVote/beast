package edu.pse.beast.api.runner.propertycheck.process.cbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface CBMCProcessHandler {
    String getPathSep();

    /**
     * Prepare arguments for cbmc call.
     *
     * @param v the voter bound
     * @param c the candidate bound
     * @param s the seat bound
     * @param cFile the c file to be checked by cbmc
     * @param loopBounds the known loop bounds
     * @param codeGenOptions various options mostly for variable names etc
     * @param cbmc the cbmc runnable
     * @return the cbmc arguments
     */
    default List<String> getArguments(final int v, final int c, final int s,
                                      final File cFile, final CodeGenLoopBoundHandler loopBounds,
                                      final CodeGenOptions codeGenOptions, final File cbmc) {
        final List<String> args = new ArrayList<String>();
        args.add(getPathSep() + cbmc.getPath() + getPathSep());
        args.add(cFile.getAbsolutePath());
        CBMCArgumentHelper
        .addToArgs(args, CBMCArgumentHelper.getConstCommandList(codeGenOptions, v, c, s));
        args.add(CBMCArgumentHelper.getJsonOutputCommand());
        CBMCArgumentHelper.addToArgs(args, loopBounds.generateCBMCStringList(v, c, s));
        return args;
    }

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
