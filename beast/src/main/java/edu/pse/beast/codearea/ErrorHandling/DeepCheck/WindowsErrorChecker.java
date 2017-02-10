package edu.pse.beast.codearea.ErrorHandling.DeepCheck;

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
import edu.pse.beast.toolbox.WindowsOStoolbox;

public class WindowsErrorChecker extends SystemSpecificErrorChecker {

    private final String compilerString = "cl";

    @Override
    public Process checkCodeFileForErrors(File toCheck) {

        String vsCmd = null;

        Process startedProcess = null;

        // try to get the vsCMD
        try {
            vsCmd = WindowsOStoolbox.getVScmdPath();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
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
            // VScmd has to be in one giant string
            String clExeCall = "\"" + vsCmd + "\"" + " & " + compilerString + " " + toCheck.getAbsolutePath();

            // this call starts a new VScmd instance and lets cbmc run in it
            ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", clExeCall);

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

        Pattern lineExtractor = Pattern.compile("((.*)(\\([0-9]*\\))(.*))");

        for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher linesMatcher = lineExtractor.matcher(line);

            int lineNumber = -1;

            String varName = "";

            String message = "";

            if (linesMatcher.find()) {
                try {

                    lineNumber = Integer.parseInt(linesMatcher.group(1).split("\\(")[1].split("\\)")[0]);

                    String[] varAndMessage = line.split("(error C[0-9]*:)");

                    if (varAndMessage.length > 1) {
                        String toSplit = varAndMessage[1];

                        if (toSplit.contains(":")) {
                            varName = toSplit.split(":")[0].replaceAll("\"", "");
                            ;
                            message = toSplit.split(":")[1];
                        } else {
                            message = toSplit;
                        }
                    }

                    codeErrors.add(generateCodeError(lineNumber, -1, varName, message));

                } catch (NumberFormatException e) {
                    ErrorLogger.log("can't parse the current error line from cl.exe");
                }
            }
        }
        return codeErrors;
    }
}
