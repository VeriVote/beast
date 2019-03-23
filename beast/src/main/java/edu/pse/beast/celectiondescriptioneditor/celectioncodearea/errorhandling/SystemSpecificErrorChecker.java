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
 * this is the superclass for system specific error checkers that gets
 * implemented for the needed operating systems
 * 
 * @author Lukas Stapelbroek
 *
 */
public abstract class SystemSpecificErrorChecker {

    private final String pathToTempFolder = "/core/c_tempfiles/";


    /**
     * constructor creates an error checker that compiles the c code and passes it
     * on to a system specific compiler
     */
    public SystemSpecificErrorChecker() {
        // clear the folder where the files that get checked get saved to, because
        // sometimes they
        // persist from the last time BEAST was run
        try {
            final String dummy = "dummy.file";
            cleanDirectory(new File(SuperFolderFinder.getSuperFolder() + pathToTempFolder), dummy);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Taken from "org.apache.commons.io.FileUtils" Lists files in a directory,
     * asserting that the supplied directory satisfies exists and is a directory
     *
     * @param directory The directory to list
     * @return The files in the directory, never null.
     * @throws IOException if an I/O error occurs
     */
    private static File[] verifiedListFiles(File directory) throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
        final File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    /**
     * Taken and adapted from "org.apache.commons.io.FileUtils" Cleans a directory
     * without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException              in case cleaning is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or is
     *                                  not a directory
     */
    private static void cleanDirectory(final File directory, String keep) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                if (!file.getName().equals(keep)) { // Difference to original method
                    FileUtils.forceDelete(file);
                }
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * checks the code for errors
     * 
     * @param toCheck    the code to check
     * @param lineOffset line offset
     * @return all errors found in a list
     */
    public List<CodeError> checkCodeForErrors(List<String> toCheck, int lineOffset) {

        List<String> result = new ArrayList<String>();

        List<String> errors = new ArrayList<String>();

        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;

        String pathToNewFile = absolutePath + FileLoader.getNewUniqueName(absolutePath);
        // pathToNewFile = pathToNewFile.replaceAll("%20", " ");
        // create two links to files, so in case an object file gets created we can
        // delete it afterwards too

        File cFile = new File(pathToNewFile + ".c");
        // that will be created, to delete it afterwards
        File batFile = new File(pathToNewFile + ".bat");

        File objFile = new File(pathToNewFile + ".obj");

        // on windows we have to create a .bat file, so we create a reference to the
        // file

        // on windows we have to create a .bat file, so we create a reference to the
        // file
        // that will be created, to delete it afterwards
        File exeFile = new File(pathToNewFile + ".exe");

        // write the code to the file
        FileSaver.writeStringLinesToFile(toCheck, cFile);

        Process process = checkCodeFileForErrors(cFile);

        if (process != null) {
            CountDownLatch latch = new CountDownLatch(2);

            ThreadedBufferedReader outReader = new ThreadedBufferedReader(
                    new BufferedReader(new InputStreamReader(process.getInputStream())), result, latch, false);
            ThreadedBufferedReader errReader = new ThreadedBufferedReader(
                    new BufferedReader(new InputStreamReader(process.getErrorStream())), errors, latch, false);

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

            // deletes the temporary file, so it doesn't clog up the filesystem

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
            ErrorLogger.log("Process couldn't be started");
            return null;
        }
    }

    /**
     * checks a file for errors. Has to be implemented system specific
     * 
     * @param toCheck the file to check
     * @return a process that is currently checking the file
     */
    protected abstract Process checkCodeFileForErrors(File toCheck);

    /**
     * parses the system specific outputs from the process to the internal
     * "CodeError" format
     * 
     * @param result     the result list from the previously started process
     * @param errors     the error list from the previously started process
     * @param lineOffset line offset
     * @return a list of all found code errors in the list
     */
    protected abstract List<CodeError> parseError(List<String> result,
                                                  List<String> errors,
                                                  int lineOffset);
}