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
 * @author Lukas
 *
 */
public class LinuxErrorChecker extends SystemSpecificErrorChecker {

    // program that is to be used for checking
    private final String compilerString = "gcc";

    // this flag prohibits that file are creates by the compiler and
    // only the syntax is checked
    private final String findMissingReturnOption = "-Wreturn-type";

    // we want to compile to a specific name, so we can delete the file
    // then later on
    private final String setOutputFileName = "-o ";

    private final String enableUserInclude = "-I/";
    private final String userIncludeFolder = "/core/user_includes/";

    // we want to compile all available c files, so the user doesn't have to
    // specify anything
    private final String cFileEnder = ".c";

    // if gcc finds, that a return is missing, it prints out this error message.
    // The error then
    // stands in the format: "FILENANE:LINE:COLUMN warning:control reaches..."
    private final String gccMissingReturnFound = "warning: control reaches end of non-void function";

    // if gcc finds that a function is missing, it gets displayed like this:
    private final String gccMissingFunctionFound = "warning: implicit declaration of function";

    @Override
    public Process checkCodeFileForErrors(File toCheck) {

        String nameOfOutFile = toCheck.getName().replace(".c", ".out");

        File outFile = new File(toCheck.getParentFile(), nameOfOutFile);

        String compileToThis = setOutputFileName + outFile.getAbsolutePath();

        String userIncludeAndPath = enableUserInclude + SuperFolderFinder.getSuperFolder() + userIncludeFolder;

        // get all Files from the form "*.c" so we can include them into cbmc,
        List<String> allFiles = FileLoader.listAllFilesFromFolder(
                "\"" + SuperFolderFinder.getSuperFolder() + userIncludeFolder + "\"", cFileEnder);

        Process startedProcess = null;

        List<String> arguments = new ArrayList<String>();

        // add the arguments needed for the call
        arguments.add(compilerString);

        arguments.add(userIncludeAndPath);

        arguments.add(findMissingReturnOption);

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
    protected List<CodeError> parseError(List<String> result, List<String> errors, int lineOffset) {
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

                        codeErrors.add(CCodeErrorFactory.generateCompilerError(lineNumber, linePos, varName, message));

                    } catch (NumberFormatException e) {
                        ErrorLogger.log("can't parse the current error line from gcc");
                    }
                }
            } else if (line.contains(gccMissingReturnFound)) {

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

                    codeErrors.add(CCodeErrorFactory.generateCompilerError(lineNumber, linePos, varName, message));

                } catch (NumberFormatException e) {
                    ErrorLogger.log("can't parse the current error line from gcc");
                }

            } else if (line.contains(gccMissingFunctionFound)) {
                // we want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error

                String[] splittedLine = line.split(":");

                if (splittedLine.length >= 4)

                    try {

                        // the output has the form"FILENANE:LINE:COLUMN
                        // warning:control reaches..."

                        lineNumber = Integer.parseInt(line.split(":")[1]);

                        linePos = Integer.parseInt(line.split(":")[2]);

                        if (message.contains("‘") && message.contains("’")) {
                            varName = message.split("‘")[1].split("’")[0];
                        }

                        message = line.split("warning:")[1];

                        codeErrors.add(CCodeErrorFactory.generateCompilerError(lineNumber, linePos, varName, message));

                    } catch (NumberFormatException e) {
                        ErrorLogger.log("can't parse the current error line from gcc");
                    }
            }
        }
        return codeErrors;
    }
}
