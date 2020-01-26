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
 * The Linux implementation for checking the code. This implementation uses gcc
 * for checking.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class LinuxErrorChecker extends SystemSpecificErrorChecker {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant COLON. */
    private static final String COLON = ":";

    /** The Constant TWO_BACKSLASHES. */
    private static final String TWO_BACKSLASHES = "\\";
    /** The Constant DOUBLE_QUOTE. */
    private static final String DOUBLE_QUOTE = "\"";
    /** The Constant QUOTE_START. */
    private static final String QUOTE_START = "‘";
    /** The Constant QUOTE_END. */
    private static final String QUOTE_END = "’";

    /** The Constant WARNING. */
    private static final String WARNING = "warning";
    /** The Constant ERROR. */
    private static final String ERROR = "error";

    /** The Constant ERROR_MSG. */
    private static final String ERROR_MSG =
            "Cannot parse the current error line from gcc.";

    /** The Constant FOUR. */
    private static final int FOUR = 4;

    @Override
    public Process checkCodeFileForErrors(final File toCheck) {
        final String nameOfOutFile =
                toCheck.getName().replace(FileLoader.C_FILE_ENDING, FileLoader.OUT_FILE_ENDING);
        final File outFile = new File(toCheck.getParentFile(), nameOfOutFile);
        final String compileToThis = SET_OUTPUT_FILE_NAME + outFile.getAbsolutePath();
        final String userIncludeAndPath =
                ENABLE_USER_INCLUDE + SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER;
        final List<String> arguments = new ArrayList<String>();
        // Add the arguments needed for the call
        arguments.add(COMPILER_STRING);
        arguments.add(userIncludeAndPath);
        arguments.add(FIND_MISSING_RETURN_OPTION);
        // Add the path to the created file that should be checked
        arguments.add(toCheck.getAbsolutePath());

        // Get all Files from the form "*.c" so we can include them into cbmc,
        final List<String> allFiles =
                FileLoader.listAllFilesFromFolder(
                        DOUBLE_QUOTE + SuperFolderFinder.getSuperFolder()
                            + USER_INCLUDE_FOLDER + DOUBLE_QUOTE,
                        FileLoader.C_FILE_ENDING);
        // Iterate over all "*.c" files from the include folder, to include them
        for (final Iterator<String> iterator = allFiles.iterator();
                iterator.hasNext();) {
            final String toBeIncludedFile = iterator.next();
            arguments.add(toBeIncludedFile.replace(DOUBLE_QUOTE, "")
                            .replace(BLANK, TWO_BACKSLASHES + BLANK));
        }
        // Defines the position to what place the compiled files should be sent
        arguments.add(compileToThis);
        final ProcessBuilder prossBuild =
                new ProcessBuilder(arguments.toArray(new String[0]));
        final Map<String, String> environment = prossBuild.environment();
        // Set the language for the following call to English
        environment.put("LC_ALL", "C");
        Process startedProcess = null;
        try {
            // Start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }

    @Override
    protected List<CodeError> parseError(final List<String> result,
                                         final List<String> errors,
                                         final int lineOffset) {
        final List<CodeError> codeErrors = new ArrayList<CodeError>();
        // gcc gives the errors out in the error stream so we traverse it
        for (final Iterator<String> iterator = errors.iterator();
                iterator.hasNext();) {
            final String line = iterator.next();
            int lineNumber = -1;
            int linePos = -1;
            String varName = "";
            String message = "";
            // we only want error lines, no warning or something else
            if (line.contains(ERROR + COLON)) {
                // we want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error
                if (line.split(COLON).length > FOUR) {
                    try {
                        // put the output in the containers for them
                        lineNumber =
                                Integer.parseInt(line.split(COLON)[1]) - lineOffset;
                        linePos = Integer.parseInt(line.split(COLON)[2]);
                        message = line.split(ERROR + COLON)[1];
                        if (message.contains(QUOTE_START) && message.contains(QUOTE_END)) {
                            varName = message.split(QUOTE_START)[1].split(QUOTE_END)[0];
                        }
                        codeErrors.add(
                                CCodeErrorFactory.generateCompilerError(
                                        lineNumber, linePos,
                                        varName, message)
                        );
                    } catch (NumberFormatException e) {
                        ErrorLogger.log(ERROR_MSG);
                    }
                }
            } else if (line.contains(GCC_MISSING_RETURN_FOUND)) {
                // We want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error.
                try {
                    // The output has the form"FILENANE:LINE:COLUMN
                    // warning:control reaches..."
                    lineNumber = Integer.parseInt(line.split(COLON)[1]);
                    linePos = Integer.parseInt(line.split(COLON)[2]);
                    varName = "";
                    message = "Missing return";
                    codeErrors.add(
                            CCodeErrorFactory.generateCompilerError(
                                    lineNumber, linePos,
                                    varName, message));
                } catch (NumberFormatException e) {
                    ErrorLogger.log(ERROR_MSG);
                }
            } else if (line.contains(GCC_MISSING_FUNCTION_FOUND)) {
                // We want the format :line:position: ... error:
                // so we need at least 4 ":" in the string to be sure to find a
                // line and the position and the error.
                final String[] splittedLine = line.split(COLON);
                if (splittedLine.length >= FOUR) {
                    try {
                        // the output has the form"FILENANE:LINE:COLUMN
                        // warning:control reaches..."
                        lineNumber = Integer.parseInt(line.split(COLON)[1]);
                        linePos = Integer.parseInt(line.split(COLON)[2]);
                        if (message.contains(QUOTE_START) && message.contains(QUOTE_END)) {
                            varName = message.split(QUOTE_START)[1].split(QUOTE_END)[0];
                        }
                        message = line.split(WARNING + COLON)[1];
                        codeErrors.add(
                                CCodeErrorFactory.generateCompilerError(
                                        lineNumber, linePos,
                                        varName, message)
                        );
                    } catch (NumberFormatException e) {
                        ErrorLogger.log("cannot parse the current"
                                        + " error line from gcc.");
                    }
                }
            }
        }
        return codeErrors;
    }
}
