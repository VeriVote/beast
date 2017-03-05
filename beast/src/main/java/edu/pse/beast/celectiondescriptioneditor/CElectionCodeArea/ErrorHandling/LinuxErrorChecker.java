package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.toolbox.ErrorLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * the linux implementation for checking the code
 * This implementation uses gcc for checking
 * @author Lukas
 *
 */
public class LinuxErrorChecker extends SystemSpecificErrorChecker {
    //program that is to be used for checking
    private final String compilerString = "gcc";
    
    //this flag prohibits that file are creates by the compiler and
    //only the syntax is checked
    private final String syntaxCheckOnly = "-fsyntax-only";

    @Override
    public Process checkCodeFileForErrors(File toCheck) {

        Process startedProcess = null;

        List<String> arguments = new ArrayList<String>();
        
        //add the arguments needed for the call
        arguments.add(compilerString);

        arguments.add(syntaxCheckOnly);

        arguments.add(toCheck.getAbsolutePath());

        ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));

        try {
            //start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return startedProcess;
    }

    @Override
    protected List<CodeError> parseError(List<String> result, List<String> errors) {
        List<CodeError> codeErrors = new ArrayList<CodeError>();

        //gcc gives the errors out in the error stream so we traverse it
        for (Iterator<String> iterator = errors.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            int lineNumber = -1;

            int linePos = -1;

            String varName = "";

            String message = "";

            //we only want error lines, no warning or something else
            if (line.contains("error:")) {

                //we want the format :line:position: ... error: 
                //so we need at least 4 ":" in the string to be sure to find a line and the position and the error
                if (line.split(":").length > 4) {

                    try {

                        //put the output in the containers for them
                        lineNumber = Integer.parseInt(line.split(":")[1]);

                        linePos = Integer.parseInt(line.split(":")[2]);

                        message = line.split("error:")[1];

                        if (message.contains("‘") && message.contains("’")) {
                            varName = message.split("‘")[1].split("’")[0];
                        }
                        
                        codeErrors.add(CCodeErrorFactory.generateCompilterError(lineNumber, linePos, varName, message));

                    } catch (NumberFormatException e) {
                        ErrorLogger.log("can't parse the current error line from gcc");
                    }
                }
            }
        }
        return codeErrors;
    }
}
