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

    // the compiler we use on windows, because it is also needed by cbmc
    private final String compilerString = "cl.exe";

    // used to enable includes from the users own written classes
    private final String enableUserInclude = "/I";
    private final String userIncludeFolder = "/core/user_includes/";

    // we want to compile all available c files, so the user doesn't have to
    // specify anything
    private final String compileAllIncludesInFolder = "*.c";

    @Override
    protected Process compileCfile(File toCheck) {

        String vsCmd = null;

        Process startedProcess = null;

        String userIncludeAndPath = enableUserInclude + "\"" + SuperFolderFinder.getSuperFolder() + userIncludeFolder
                + "\"";

        // we have to compile all includes that the user puts in that folder, in
        // case some of them are needed
        String compileAllIncludesInIncludePath = "\"" + SuperFolderFinder.getSuperFolder() + userIncludeFolder
                + compileAllIncludesInFolder + "\"";

        // try to get the vsCMD
        try {
            vsCmd = WindowsOStoolbox.getVScmdPath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (vsCmd == null) {
            ErrorForUserDisplayer.displayError(
                    "The program \"VsDevCmd.bat\" couldn't be found. It is required to run this program, so "
                            + "please supply it with it. \n"
                            + " To do so, download the Visual Studio Community Version, install it (including "
                            + "the C++ pack). \n "
                            + "Then, search for the VsDevCmd.bat in it, and copy and paste it into the foler "
                            + "/windows/ in the BEAST installation folder.");
            return null;
        } else {

            // because windows is weird the whole call that will get placed
            // inside
            // VScmd has to be in one giant string. Put the created file in the
            // output directory, so
            // it can be deleted afterwards
            String clExeCall = "\"" + vsCmd + "\"" + " & " + compilerString + " " + userIncludeAndPath + " "
                    + ("\"" + toCheck.getAbsolutePath() + "\"") + " " + (" /Fo" + toCheck.getParent() + "\\ ")
                    + (" /Fe" + toCheck.getParent() + "\\ ") + compileAllIncludesInIncludePath;

            List<String> callInList = new ArrayList<String>();

            callInList.add(clExeCall);

            File batFile = new File(toCheck.getParent() + "\\" + toCheck.getName().replace(".c", ".bat"));

            FileSaver.writeStringLinesToFile(callInList, batFile);

            // this call starts a new VScmd instance and lets cl.exe (the
            // compiler) run in it
            // ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c",
            // clExeCall);
            ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", batFile.getAbsolutePath());

            try {
                startedProcess = prossBuild.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return startedProcess;
    }

    @Override
    protected Process runWithData(String toRun, File dataFile) {
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