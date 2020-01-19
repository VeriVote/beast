package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

/**
 * This is the superclass for system specific error checkers that gets
 * implemented for the needed operating systems.
 *
 * @author Lukas Stapelbroek
 *
 */
public abstract class SystemSpecificErrorChecker {
    /**
     * Program that is to be used for checking.
     */
    static final String COMPILER_STRING = "gcc";

    /**
     * This flag prohibits that file are creates by the compiler and only the
     * syntax is checked.
     */
    static final String FIND_MISSING_RETURN_OPTION = "-Wreturn-type";

    /**
     * We want to compile to a specific name, so we can delete the file then
     * later on.
     */
    static final String SET_OUTPUT_FILE_NAME = "-o ";

    /** The Constant ENABLE_USER_INCLUDE. */
    static final String ENABLE_USER_INCLUDE = "-I/";

    /** The Constant USER_INCLUDE_FOLDER. */
    static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    /**
     * If gcc finds, that a return is missing, it prints out this error message.
     * The error then is of the format "FILENANE:LINE:COLUMN warning:control
     * reaches...".
     */
    static final String GCC_MISSING_RETURN_FOUND =
            "warning: control reaches end of non-void function";

    /**
     * If gcc finds that a function is missing, it gets displayed like this.
     */
    static final String GCC_MISSING_FUNCTION_FOUND = "warning: implicit declaration of function";

    /** The Constant PATH_TO_TEMP_FOLDER. */
    private static final String PATH_TO_TEMP_FOLDER = "/core/c_tempfiles/";

    /**
     * Constructor creates an error checker that compiles the c code and passes
     * it on to a system specific compiler.
     */
    public SystemSpecificErrorChecker() {
        // clear the folder where the files that get checked get saved to,
        // because
        // sometimes they persist from the last time when BEAST was run.
        try {
            final String dummy = "dummy.file";
            cleanDirectory(new File(SuperFolderFinder.getSuperFolder() + PATH_TO_TEMP_FOLDER),
                           dummy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Taken from "org.apache.commons.io.FileUtils". Lists files in a directory,
     * asserting that the supplied directory satisfies exists and is a
     * directory.
     *
     * @param directory
     *            The directory to list.
     * @return The files in the directory, never null.
     * @throws IllegalArgumentException
     *             if the directory does not exist or is no directory.
     * @throws IOException
     *             if an I/O error occurs.
     */
    private static File[] verifiedListFiles(final File directory)
            throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(
                    directory + " is not a directory");
        }
        final File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    /**
     * Taken and adapted from "org.apache.commons.io.FileUtils" Cleans a
     * directory without deleting it.
     *
     * @param directory
     *            directory to clean
     * @param keep
     *            the keep
     * @throws IOException
     *             in case cleaning is unsuccessful
     * @throws IllegalArgumentException
     *             if {@code directory} does not exist or is not a directory
     */
    private static void cleanDirectory(final File directory,
                                       final String keep) throws IOException {
        final File[] files = verifiedListFiles(directory);
        IOException exception = null;
        for (final File file : files) {
            try {
                if (!file.getName().equals(keep)) {
                    // Difference to original method
                    FileUtils.forceDelete(file);
                }
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    /**
     * Checks the code for errors.
     *
     * @param toCheck
     *            the code to check
     * @param lineOffset
     *            line offset
     * @return all errors found in a list
     */
    public List<CodeError> checkCodeForErrors(final List<String> toCheck,
                                              final int lineOffset) {
        List<String> result = new ArrayList<String>();
        List<String> errors = new ArrayList<String>();
        String absolutePath = SuperFolderFinder.getSuperFolder()
                                + PATH_TO_TEMP_FOLDER;
        final String pathToNewFile =
                absolutePath + FileLoader.getNewUniqueName(absolutePath);
        // pathToNewFile = pathToNewFile.replaceAll("%20", " ");
        // Create two links to files, so in case an object file gets created we
        // can
        // delete it afterwards, too.
        final File cFile = new File(pathToNewFile + FileLoader.C_FILE_ENDING);
        // that will be created, to delete it afterwards
        final File batFile = new File(pathToNewFile + FileLoader.BAT_FILE_ENDING);
        final File objFile = new File(pathToNewFile + FileLoader.OBJECT_FILE_ENDING);
        // on windows we have to create a .bat file, so we create a reference to
        // the
        // file that will be created, to delete it afterwards
        final File exeFile = new File(pathToNewFile + FileLoader.EXE_FILE_ENDING);
        // write the code to the file
        FileSaver.writeStringLinesToFile(toCheck, cFile);
        Process process = checkCodeFileForErrors(cFile);
        if (process != null) {
            CountDownLatch latch = new CountDownLatch(2);
            ThreadedBufferedReader outReader =
                    new ThreadedBufferedReader(
                            new BufferedReader(
                                    new InputStreamReader(
                                            process.getInputStream()
                                            )
                                    ),
                            result, latch, true);
            ThreadedBufferedReader errReader =
                    new ThreadedBufferedReader(
                            new BufferedReader(
                                    new InputStreamReader(
                                            process.getErrorStream())
                                    ),
                            errors, latch, false);
            // wait for the process to finish;
            try {
                process.waitFor();
                // wait for the readers to finish reading
                latch.await();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // parse the errors out of the returned lists
            List<CodeError> toReturn = parseError(result, errors, lineOffset);
            // deletes the temporary file, so it does not clog up the file
            // system
            if (GUIController.getController().getDeleteTmpFiles()) {
                cFile.delete();
                batFile.delete();
            }
            objFile.delete();
            exeFile.delete();
            outReader.finish();
            errReader.finish();
            return toReturn;
        } else {
            ErrorLogger.log("Process could not be started");
            return null;
        }
    }

    /**
     * Checks a file for errors. Has to be implemented system specifically.
     *
     * @param toCheck
     *            the file to check
     * @return a process that is currently checking the file
     */
    protected abstract Process checkCodeFileForErrors(File toCheck);

    /**
     * Parses the system specific outputs from the process to the internal
     * "CodeError" format.
     *
     * @param result
     *            the result list from the previously started process
     * @param errors
     *            the error list from the previously started process
     * @param lineOffset
     *            line offset
     * @return a list of all found code errors in the list
     */
    protected abstract List<CodeError> parseError(List<String> result,
                                                  List<String> errors,
                                                  int lineOffset);
}
