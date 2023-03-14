package edu.kit.kastel.formal.beast.api.runner.propertycheck.process.cbmc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.io.InputOutputInterface;
import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCProcessHandlerWindows implements CBMCProcessHandler {
    private static final String PATH_SEP = "\"";
    private static final String WIN_SEP = "\\";
    private static final String AND = " & ";
    private static final String DOT_C = ".c";
    private static final String DOT_BAT = ".bat";
    private static final String DOT_EXE = ".exe";
    private static final String CMD = "cmd";
    private static final String SLASH_C = "/c";
    // private static final long WAITING_TIME_FOR_TERMINATION = 8000;
    // private static final double A_VERY_LONG_TIME = 1000d;
    // private String CBMC64_EXE = "cbmc64.exe";

    /** The Constant RELATIVE_PATH_TO_CBMC. */
    private static final String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/cbmc";

    // only needed in Windows
    private String vsCmdPath; // =
    // "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";

    public CBMCProcessHandlerWindows(final String vsCmdPathString) {
        this.vsCmdPath = getPathSep() + vsCmdPathString + getPathSep();
    }

    public final String getPathSep() {
        return PATH_SEP;
    }

    public final String getVsCmdPath() {
        return vsCmdPath;
    }

    public final void setVsCmdPath(final String vsCmdPathString) {
        this.vsCmdPath = vsCmdPathString;
    }

    /**
     * Create a batch file for running cbmc via the visual studio shell.
     *
     * @param vsCmdPath the path to visual studio
     * @param cFile the c file to be checked
     * @param arg the full cbm call
     * @return the created bat file (Windows specific)
     * @throws IOException an exception when writing the bat file goes wrong
     */
    private static File createBatFile(final String vsCmdPath, final File cFile,
                                      final String arg) throws IOException {
        final String batPath =
                cFile.getParent() + WIN_SEP + cFile.getName().replace(DOT_C, DOT_BAT);
        final File batFile = PathHandler.toFile(batPath);
        InputOutputInterface.writeStringToFile(batFile, vsCmdPath + AND + arg);
        return batFile;
    }

    @Override
    public final Process startCheckForParam(final int v, final int c, final int s, final File cFile,
                                            final CodeGenLoopBoundHandler loopBounds,
                                            final CodeGenOptions codeGenOptions,
                                            final PathHandler pathHandler) throws IOException {
        final File cbmc =
                PathHandler.toFile(pathHandler.getBaseDir().getAbsolutePath()
                                    + RELATIVE_PATH_TO_CBMC + DOT_EXE);
        final String arg =
                CBMCArgumentHelper.argumentListToString(
                        getArguments(v, c, s, cFile, loopBounds, codeGenOptions, cbmc));
        final File batFile = createBatFile(getVsCmdPath(), cFile, arg);
        final String directory = batFile.getAbsolutePath();
        final ProcessBuilder pb = new ProcessBuilder(CMD, SLASH_C, directory);
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
