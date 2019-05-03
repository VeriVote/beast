package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 * the linux implementation for checking the code This implementation uses gcc
 * for checking
 *
 * @author Lukas Stapelbroek
 *
 */
public class LinuxErrorChecker extends SystemSpecificErrorChecker {
    @Override
    public Process checkCodeFileForErrors(File toCheck) {
        String nameOfOutFile
            = toCheck.getName().replace(FileLoader.C_FILE_ENDING,
                                        FileLoader.OUT_FILE_ENDING);
        File outFile = new File(toCheck.getParentFile(), nameOfOutFile);
        String compileToThis = SET_OUTPUT_FILE_NAME + outFile.getAbsolutePath();
        String userIncludeAndPath
              = ENABLE_USER_INCLUDE + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER;
        // get all Files from the form "*.c" so we can include them into cbmc,
        List<String> allFiles
            = FileLoader.listAllFilesFromFolder(
                "\"" + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER + "\"", FileLoader.C_FILE_ENDING
            );
        Process startedProcess = null;
        List<String> arguments = new ArrayList<String>();
        // add the arguments needed for the call
        arguments.add(COMPILER_STRING);
        arguments.add(userIncludeAndPath);
        arguments.add(FIND_MISSING_RETURN_OPTION);
        // add the path to the created file that should be checked
        arguments.add(toCheck.getAbsolutePath());
        // iterate over all "*.c" files from the include folder, to include them
        for (Iterator<String> iterator = allFiles.iterator(); iterator.hasNext();) {
            String toBeIncludedFile = (String) iterator.next();
            arguments.add(toBeIncludedFile.replace("\"", "").replace(" ", "\\ "));
        }
        // defines the position to what place the compiled files should be sent
        arguments.add(compileToThis);
        ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));
        Map<String, String> environment = prossBuild.environment();
        environment.put("LC_ALL", "C"); // set the language for the following call to english
        try {
            // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }

    @Override
    protected List<CodeError> parseError(List<String> result,
                                         List<String> errors,
                                         int lineOffset) {
        List<CodeError> codeErrors = new ArrayList<CodeError>();
        // gcc gives the errors out in the error stream so we traverse it
        for (Iterator<String> iterator = errors.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();
            int lineNumber = -1;
            int linePos = -1;
            String varName = "";
            String message = "";
            // we only want error lines, no warning or something else
            if (line.contains("error:")) {
                // we want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error
                if (line.split(":").length > 4) {
                    try {
                        // put the output in the containers for them
                        lineNumber = Integer.parseInt(line.split(":")[1]) - lineOffset;
                        linePos = Integer.parseInt(line.split(":")[2]);
                        message = line.split("error:")[1];
                        if (message.contains("‘") && message.contains("’")) {
                            varName = message.split("‘")[1].split("’")[0];
                        }
                        codeErrors.add(
                            CCodeErrorFactory.generateCompilerError(lineNumber, linePos,
                                                                    varName, message));
                    } catch (NumberFormatException e) {
                        ErrorLogger.log("Cannot parse the current error line from gcc");
                    }
                }
            } else if (line.contains(GCC_MISSING_RETURN_FOUND)) {
                // we want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error
                try {
                    // the output has the form"FILENANE:LINE:COLUMN
                    // warning:control reaches..."
                    lineNumber = Integer.parseInt(line.split(":")[1]);
                    linePos = Integer.parseInt(line.split(":")[2]);
                    varName = "";
                    message = "Missing return";
                    codeErrors.add(
                        CCodeErrorFactory.generateCompilerError(lineNumber, linePos,
                                                                varName, message));
                } catch (NumberFormatException e) {
                    ErrorLogger.log("Cannot parse the current error line from gcc");
                }
            } else if (line.contains(GCC_MISSING_FUNCTION_FOUND)) {
                // we want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error
                String[] splittedLine = line.split(":");
                if (splittedLine.length >= 4) {
                    try {
                        // the output has the form"FILENANE:LINE:COLUMN
                        // warning:control reaches..."
                        lineNumber = Integer.parseInt(line.split(":")[1]);
                        linePos = Integer.parseInt(line.split(":")[2]);
                        if (message.contains("‘") && message.contains("’")) {
                            varName = message.split("‘")[1].split("’")[0];
                        }
                        message = line.split("warning:")[1];
                        codeErrors.add(
                            CCodeErrorFactory.generateCompilerError(lineNumber, linePos,
                                                                    varName, message));
                    } catch (NumberFormatException e) {
                        ErrorLogger.log("cannot parse the current error line from gcc");
                    }
                }
            }
        }
        return codeErrors;
    }
}