package edu.pse.beast.electionsimulator.programaccess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.WindowsOStoolbox;

/**
 * This is the windows specific implementation to check code. It uses cl.exe
 * from the c++ pack for visual studio to check the code for errors.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class WindowsCompilerAndRunner
        extends SystemSpecificCompilerAndExecutioner {
    /** The Constant SLASH_C. */
    public static final String SLASH_C = "/c";
    /** The Constant "cmd.exe". */
    public static final String CMD_EXE = "cmd" + FileLoader.EXE_FILE_ENDING;

    /** The Constant "\\". */
    public static final String TWO_BACKSLASHES = "\\";
    /** The Constant "&". */
    public static final String AMPERSAND = "&";

    /** The Constant BLANK. */
    private static final String BLANK = " ";

    /** The compiler we use on windows, because it is also needed by cbmc. */
    private static final String COMPILER_STRING = "cl.exe";

    /** Used to enable includes from the users own written classes. */
    private static final String ENABLE_USER_INCLUDE = "/I";

    /** The Constant USER_INCLUDE_FOLDER. */
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    /** We want to compile all available c files, so the user does not need to
        specify anything. */
    private static final String COMPILE_ALL_INCLUDES_IN_FOLDER =
            "*" + FileLoader.C_FILE_ENDING;

    @Override
    protected Process compileCFile(final File toCheck) {
        String vsCmd = null;
        Process startedProcess = null;
        final String userIncludeAndPath =
                ENABLE_USER_INCLUDE + FileLoader.QUOTE
                + SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER
                + FileLoader.QUOTE;
        // We must compile all includes that the user puts in that folder, in
        // case some of them are needed.
        final String compileAllIncludesInIncludePath =
                FileLoader.QUOTE + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER + COMPILE_ALL_INCLUDES_IN_FOLDER
                + FileLoader.QUOTE;

        // Try to get the vsCMD.
        try {
            vsCmd = WindowsOStoolbox.getVScmdPath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (vsCmd == null) {
            ErrorForUserDisplayer.displayError(
                    "The program \"VsDevCmd.bat\" could not be found. "
                            + "It is required to run this program, so "
                            + "please supply it with it. \n"
                            + " To do so, download the Visual Studio Community Version, "
                            + "install it (including  the C++ pack). \n "
                            + "Then, search for the VsDevCmd.bat in it, "
                            + "and copy and paste it into the folder "
                            + "/windows/ in the BEAST installation folder.");
            return null;
        } else {
            // Because windows is weird the whole call that will get placed inside
            // VScmd has to be in one giant string. Put the created file in the
            // output directory, so it can be deleted afterwards.
            final String clExeCall =
                    FileLoader.QUOTE + vsCmd + FileLoader.QUOTE
                    + BLANK + AMPERSAND + BLANK + COMPILER_STRING + BLANK
                    + userIncludeAndPath + BLANK
                    + (FileLoader.QUOTE + toCheck.getAbsolutePath() + FileLoader.QUOTE)
                    + BLANK + (BLANK + "/Fo" + toCheck.getParent() + TWO_BACKSLASHES + BLANK)
                    + (BLANK + "/Fe" + toCheck.getParent() + TWO_BACKSLASHES + BLANK)
                    + compileAllIncludesInIncludePath;

            final List<String> callInList = new ArrayList<String>();
            callInList.add(clExeCall);
            final File batFile =
                    new File(toCheck.getParent() + TWO_BACKSLASHES
                            + toCheck.getName().replace(FileLoader.C_FILE_ENDING,
                                                        FileLoader.BAT_FILE_ENDING));
            FileSaver.writeStringLinesToFile(callInList, batFile);

            // this call starts a new VScmd instance and lets cl.exe (the
            // compiler) run in it
            // ProcessBuilder prossBuild = new ProcessBuilder(CMD_EXE, SLASH_C,
            // clExeCall);
            final ProcessBuilder prossBuild = new ProcessBuilder(CMD_EXE, SLASH_C,
                                                                 batFile.getAbsolutePath());

            try {
                startedProcess = prossBuild.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return startedProcess;
    }

    @Override
    protected Process runWithData(final String toRun, final File dataFile) {
        Process startedProcess = null;
        // Windows wants one big String to call programs
        String callString = toRun + FileLoader.EXE_FILE_ENDING;
        // the absolute path to the file that holds
        callString += BLANK + dataFile.getAbsolutePath();
        final ProcessBuilder prossBuild = new ProcessBuilder(CMD_EXE, SLASH_C,
                                                             callString);
        try {
            // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }
}
