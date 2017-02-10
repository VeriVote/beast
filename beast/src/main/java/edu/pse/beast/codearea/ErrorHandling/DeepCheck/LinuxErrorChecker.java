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

public class LinuxErrorChecker extends SystemSpecificErrorChecker {
    private final String compilerString = "gcc";
    private final String syntaxCheckOnly = "-fsyntax-only";

    @Override
    public Process checkCodeFileForErrors(File toCheck) {

        Process startedProcess = null;

        List<String> arguments = new ArrayList<String>();

        arguments.add(compilerString);

        arguments.add(syntaxCheckOnly);

        arguments.add(toCheck.getAbsolutePath());

        ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));

        try {
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return startedProcess;
    }

    @Override
    protected List<CodeError> parseError(List<String> result, List<String> errors) {
        List<CodeError> codeErrors = new ArrayList<CodeError>();

        //gcc gives the errors out in the error stream
        for (Iterator<String> iterator = errors.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            int lineNumber = -1;

            int linePos = -1;

            String varName = "";

            String message = "";

            if (line.contains("error:")) {

                if (line.split(":").length > 3) {

                    try {

                        lineNumber = Integer.parseInt(line.split(":")[1]);

                        linePos = Integer.parseInt(line.split(":")[2]);

                        message = line.split("error:")[1];

                        if (message.contains("‘") && message.contains("’")) {
                            varName = message.split("‘")[1].split("’")[0];
                        }

                        codeErrors.add(generateCodeError(lineNumber, linePos, varName, message));

                    } catch (NumberFormatException e) {
                        ErrorLogger.log("can't parse the current error line from cl.exe");
                    }
                }
            }
        }
        return codeErrors;
    }
}
