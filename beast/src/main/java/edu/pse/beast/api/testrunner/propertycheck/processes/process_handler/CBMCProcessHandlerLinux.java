package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.paths.PathHandler;

public class CBMCProcessHandlerLinux implements CBMCProcessHandler {
    private static final String RELATIVE_PATH_TO_CBMC = "/linux/cbmcLin/cbmc";

    @Override
    public CBMCProcessStarterType getType() {
        return CBMCProcessStarterType.LINUX;
    }

    @Override
    public Process startCheckForParam(final String sessionUUID,
                                      final int v, final int c, final int s,
                                      final String uuid,
                                      final CBMCTestCallback cb,
                                      final File cbmcFile,
                                      final String loopBounds,
                                      final CodeGenOptions codeGenOptions,
                                      final PathHandler pathHandler)
            throws IOException {
        final File cbmcProgFile =
                new File(pathHandler.getBaseDir().getAbsolutePath()
                        + RELATIVE_PATH_TO_CBMC);
        final String arguments =
                CBMCArgumentHelper.getConstCommands(codeGenOptions, v, c, s)
                + " " + loopBounds;
        return Runtime.getRuntime().exec(arguments, null, cbmcProgFile);
    }

    @Override
    public void endProcess(final Process p) {
        if (!p.isAlive()) {
            return;
        }
        p.destroyForcibly();
    }

}
