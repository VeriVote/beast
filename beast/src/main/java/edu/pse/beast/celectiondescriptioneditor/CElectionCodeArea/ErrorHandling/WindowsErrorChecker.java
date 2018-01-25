package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.WindowsOStoolbox;

/**
 * this is the windows specific implementation to check code. It uses cl.exe
 * from the c++ pack for visual studio to check the code for errors
 * 
 * @author Lukas
 *
 */
public class WindowsErrorChecker extends SystemSpecificErrorChecker {

    // the compiler we use on windows, because it is also needed by cbmc
    private final String compilerString = "cl.exe";

    // used to enable includes from the users own written classes
    private final String enableUserInclude = "/I";
    private final String userIncludeFolder = "/core/user_includes/";

    // we want to compile all available c files, so the user doesn't have to
    // specify anything
    private final String compileAllIncludesInFolder = "*.c";

    @Override
    public Process checkCodeFileForErrors(File toCheck) {

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
    protected List<CodeError> parseError(List<String> result, List<String> errors) {
        List<CodeError> codeErrors = new ArrayList<CodeError>();

        // errors are displayed like "(LINENUMBER)" where linenumber is a whole
        // number
        Pattern lineExtractor = Pattern.compile("((.*)(\\([0-9]*\\))(.*))");

        // cl.exe prints out the results in the result list
        for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher linesMatcher = lineExtractor.matcher(line);

            int lineNumber = -1;

            String varName = "";

            String message = "";

            if (linesMatcher.find()) {
                try {

                    // we want the first occurance of such a "linenumber"
                    // indentifier, so we don't have to worry
                    // about code injection from strings or such
                    // then we split at "(" and ")" to extract the number
                    lineNumber = Integer.parseInt(linesMatcher.group(1).split("\\(")[1].split("\\)")[0]);

                    // get the error message here by splitting at a common
                    // (error/warning C[ERRORNUMBER]) identifier
                    String[] varAndMessage = line.split("([a-zA-Z]+ C[0-9]+:)");

                    // String msg = line.substring(line.lastIndexOf(":"));
                    // to prevent exceptions
                    if (varAndMessage.length > 1) {
                        String toSplit = varAndMessage[1];

                        // the variable and compilermessage is between ":"'s, so
                        // we split there.
                        if (toSplit.contains(":")) {
                            varName = toSplit.split(":")[0].replaceAll("\"", "");
                            message = toSplit.split(":")[1];
                        } else {
                            message = toSplit;
                        }
                    }

                    codeErrors.add(CCodeErrorFactory.generateCompilerError(lineNumber, -1, varName, message));

                } catch (NumberFormatException e) {
                    ErrorLogger.log("can't parse the current error line from cl.exe");
                }
            } else if (line.contains(" : error LNK")) {
                String[] splittedArray = line.split(":");

                if (splittedArray.length >= 2) {
                    String subString = splittedArray[2];
                    codeErrors.add(CCodeErrorFactory.generateCompilerError(-1, -1, "", subString));
                }
            }
        }
        return codeErrors;
    }
}
