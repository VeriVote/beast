package edu.pse.beast.electionsimulator.programaccess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.WindowsOStoolbox;

/**
 * this is the windows specific implementation to check code. It uses cl.exe
 * from the c++ pack for visual studio to check the code for errors
 *
 * @author Lukas Stapelbroek
 *
 */
public class WindowsCompilerAndRunner extends SystemSpecificCompilerAndExecutioner {

    /** The Constant COMPILER_STRING. */
    // the compiler we use on windows, because it is also needed by cbmc
    private static final String COMPILER_STRING = "cl.exe";

    /** The Constant ENABLE_USER_INCLUDE. */
    // used to enable includes from the users own written classes
    private static final String ENABLE_USER_INCLUDE = "/I";

    /** The Constant USER_INCLUDE_FOLDER. */
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    // we want to compile all available c files, so the user does not need to
    /** The Constant COMPILE_ALL_INCLUDES_IN_FOLDER. */
    // specify anything
    private static final String COMPILE_ALL_INCLUDES_IN_FOLDER = "*.c";

    @Override
    protected Process compileCFile(final File toCheck) {
        String vsCmd = null;
        Process startedProcess = null;
        String userIncludeAndPath
              = ENABLE_USER_INCLUDE + "\"" + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER + "\"";
        // we must compile all includes that the user puts in that folder, in
        // case some of them are needed
        String compileAllIncludesInIncludePath
              = "\"" + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER + COMPILE_ALL_INCLUDES_IN_FOLDER + "\"";

        // try to get the vsCMD
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
            // because windows is weird the whole call that will get placed
            // inside
            // VScmd has to be in one giant string. Put the created file in the
            // output directory, so
            // it can be deleted afterwards
            String clExeCall
                  = "\"" + vsCmd + "\""
                    + " & " + COMPILER_STRING + " " + userIncludeAndPath + " "
                    + ("\"" + toCheck.getAbsolutePath() + "\"") + " "
                    + (" /Fo" + toCheck.getParent() + "\\ ")
                    + (" /Fe" + toCheck.getParent() + "\\ ")
                    + compileAllIncludesInIncludePath;

            List<String> callInList = new ArrayList<String>();
            callInList.add(clExeCall);
            File batFile
                  = new File(toCheck.getParent() + "\\"
                             + toCheck.getName().replace(".c", ".bat"));
            FileSaver.writeStringLinesToFile(callInList, batFile);

            // this call starts a new VScmd instance and lets cl.exe (the
            // compiler) run in it
            // ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c",
            // clExeCall);
            ProcessBuilder prossBuild
                  = new ProcessBuilder("cmd.exe", "/c", batFile.getAbsolutePath());

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
        String callString = "";
        callString = toRun + ".exe";
        // the absolute path to the file that holds
        callString = callString + " " + dataFile.getAbsolutePath();
        ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", callString);
        try {
            // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }
}
