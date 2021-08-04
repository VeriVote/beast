package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.paths.PathHandler;

public class CBMCProcessHandlerLinux implements CBMCProcessHandler {
    private static final String RELATIVE_PATH_TO_CBMC = "/linux/cbmcLin/cbmc";

    private static List<String> addToArguments(final List<String> argList,
                                               final List<String> toAdd) {
        for (final String a : toAdd) {
            if (a != null && !a.isBlank()) {
                argList.add(a);
            }
        }
        return argList;
    }

    @Override
    public final CBMCProcessStarterType getType() {
        return CBMCProcessStarterType.LINUX;
    }

    @Override
    public final Process startCheckForParam(final int v, final int c, final int s,
                                            final File cbmcFile,
                                            final CodeGenLoopBoundHandler loopBounds,
                                            final CodeGenOptions codeGenOptions,
                                            final PathHandler pathHandler)
            throws IOException {
        final String cbmcProgFile =
                new File(pathHandler.getBaseDir().getAbsolutePath()
                        + RELATIVE_PATH_TO_CBMC).getPath();
        final List<String> arguments =
                CBMCArgumentHelper.getConstCommandList(codeGenOptions, v, c, s);
        final List<String> bounds = loopBounds.generateCBMCStringList(v, c, s);

        List<String> args = new ArrayList<String>();
        args.add(cbmcProgFile);
        args.add(cbmcFile.getAbsolutePath());
        args = addToArguments(args, arguments);
        args = addToArguments(args, bounds);
        final ProcessBuilder pb =
                new ProcessBuilder(args.toArray(new String[0]));
        return pb.start();
    }

    @Override
    public final void endProcess(final Process p) {
        if (!p.isAlive()) {
            return;
        }
        p.destroyForcibly();
    }

}
