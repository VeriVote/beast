package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;

public class CBMCProcessHandlerWindows implements CBMCProcessHandler {
    private static final String JSON_UI = "--json-ui ";
    private static final String BLANK = " ";
    private static final String PATH_SEP = "\"";
    private static final String WIN_SEP = "\\";
    private static final String AND = "&";
    private static final String DOT_C = ".c";
    private static final String DOT_BAT = ".bat";
    private static final String CMD = "cmd";
    private static final String SLASH_C = "/c";
    // private static final long WAITING_TIME_FOR_TERMINATION = 8000;
    // private static final double A_VERY_LONG_TIME = 1000d;
    /** The Constant CBMC_EXE. */
    private static final String CBMC_EXE = "cbmc.exe";
    // private String CBMC64_EXE = "cbmc64.exe";
    private static final String CBMC_DIR = "/windows/cbmcWIN/";

    /** The Constant RELATIVE_PATH_TO_CBMC. */
    private static final String RELATIVE_PATH_TO_CBMC = CBMC_DIR + CBMC_EXE;

    // only needed in Windows
    private String vsCmdPath; // =
    // "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";

    public CBMCProcessHandlerWindows(final String vsCmdPathString) {
        this.vsCmdPath = PATH_SEP + vsCmdPathString + PATH_SEP;
    }

    public final String getVsCmdPath() {
        return vsCmdPath;
    }

    public final void setVsCmdPath(final String vsCmdPathString) {
        this.vsCmdPath = vsCmdPathString;
    }

    @Override
    public final Process startCheckForParam(final int v, final int c, final int s,
                                            final File cbmcFile,
                                            final CodeGenLoopBoundHandler loopBounds,
                                            final CodeGenOptions codeGenOptions,
                                            final PathHandler pathHandler) throws IOException {
        final String cbmcPath =
                new File(pathHandler.getBaseDir().getAbsolutePath()
                        + RELATIVE_PATH_TO_CBMC).getPath();

        final String completeCommand = vsCmdPath + BLANK + AND + BLANK + PATH_SEP
                + cbmcPath + PATH_SEP + BLANK + cbmcFile.getAbsolutePath() + BLANK
                + CBMCArgumentHelper.getConstCommands(codeGenOptions, v, c, s)
                + BLANK + JSON_UI + loopBounds.generateCBMCString(v, c, s);

        final File batFile = new File(cbmcFile.getParent() + WIN_SEP
                + cbmcFile.getName().replace(DOT_C, DOT_BAT));

        SavingLoadingInterface.writeStringToFile(batFile, completeCommand);
        final ProcessBuilder pb =
                new ProcessBuilder(CMD, SLASH_C, batFile.getAbsolutePath());
        return pb.start();
    }

    @Override
    public final CBMCProcessStarterType getType() {
        return CBMCProcessStarterType.WINDOWS;
    }

    @Override
    public final void endProcess(final Process p) {
        throw new NotImplementedException();
    }
}
