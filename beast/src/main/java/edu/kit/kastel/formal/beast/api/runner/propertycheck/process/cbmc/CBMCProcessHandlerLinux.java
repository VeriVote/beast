package edu.kit.kastel.formal.beast.api.runner.propertycheck.process.cbmc;

import java.io.File;
import java.io.IOException;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCProcessHandlerLinux implements CBMCProcessHandler {
    private static final String EMPTY = "";
    private static final String RELATIVE_PATH_TO_CBMC = "/linux/cbmcLin/cbmc";

    public final String getPathSep() {
        return EMPTY;
    }

    @Override
    public final CBMCProcessStarterType getType() {
        return CBMCProcessStarterType.LINUX;
    }

    @Override
    public final Process startCheckForParam(final int v, final int c, final int s, final File cFile,
                                            final CodeGenLoopBoundHandler loopBounds,
                                            final CodeGenOptions codeGenOptions,
                                            final PathHandler pathHandler) throws IOException {
        final File cbmc =
                new File(pathHandler.getBaseDir().getAbsolutePath() + RELATIVE_PATH_TO_CBMC);
        final String arg =
                CBMCArgumentHelper.argumentListToString(
                        getArguments(v, c, s, cFile, loopBounds, codeGenOptions, cbmc));
        final File directory = cbmc.getParentFile();
        final Process startedProcess = Runtime.getRuntime().exec(arg, null, directory);
        return startedProcess;
        // final ProcessBuilder pb = // ProcessBuilder produces problems
        //         new ProcessBuilder(args.toArray(new String[0]));
        // return pb.start();
    }

    @Override
    public final void endProcess(final Process p) {
        if (!p.isAlive()) {
            return;
        }
        p.destroyForcibly();
    }

}
