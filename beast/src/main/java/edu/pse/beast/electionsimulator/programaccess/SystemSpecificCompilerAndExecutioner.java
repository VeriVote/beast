package edu.pse.beast.electionsimulator.programaccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;

import edu.pse.beast.propertychecker.Result;
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
public abstract class SystemSpecificCompilerAndExecutioner {
    /** The Constant COMMA. */
    private static final String COMMA = ",";
    /** The Constant EQUALS_SIGN. */
    private static final String EQUALS_SIGN = "=";

    /** The Constant COMPILATION_ERR_MSG. */
    private static final String COMPILATION_ERR_MSG =
            "Could not compile the source file.";

    /** The Constant PATH_TO_TEMP_FOLDER. */
    private static final String PATH_TO_TEMP_FOLDER = "/core/c_tempfiles/";
    /** The Constant DATA_FILE_ENDING. */
    private static final String DATA_FILE_ENDING = ".votingdata";
    /** The Constant absolutePath. */
    private static final String ABSOLUTE_PATH =
            SuperFolderFinder.getSuperFolder() + PATH_TO_TEMP_FOLDER;

    /** The data file. */
    private File dataFile;

    /** The c file. */
    private File cFile;

    /** The object file. */
    private File objectFile;

    /** The bat file. */
    private File batFile;

    /** The executable file. */
    private File exeFile;

    /** The out file. */
    private File outFile;

    /**
     * Constructor creates an error checker that compiles the c code and passes
     * it on to a system specific compiler.
     */
    public SystemSpecificCompilerAndExecutioner() {
        // Clear the folder where the files that get checked get saved to,
        // because sometimes they
        // persist from the last time BEAST was run.
        try {
            FileUtils.cleanDirectory(
                    new File(SuperFolderFinder.getSuperFolder() + PATH_TO_TEMP_FOLDER)
            );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Create the files.
     *
     * @param pathToFileModuloEnding
     *            the path to file modulo ending
     */
    private void createFiles(final String pathToFileModuloEnding) {
        dataFile = new File(pathToFileModuloEnding + DATA_FILE_ENDING);

        // Create two links to files, so in case an object file gets created we
        // can delete it afterwards, too.
        cFile = new File(pathToFileModuloEnding + FileLoader.C_FILE_ENDING);
        objectFile = new File(pathToFileModuloEnding + FileLoader.OBJECT_FILE_ENDING);

        // On Windows, we have to create a .bat file, so we create a reference to
        // the file that will be created, to delete it afterwards.
        batFile = new File(pathToFileModuloEnding + FileLoader.BAT_FILE_ENDING);

        // On Windows, a .exe file will be created
        // here it will be created, to delete it afterwards.
        exeFile = new File(pathToFileModuloEnding + FileLoader.EXE_FILE_ENDING);

        // The file that gets created on Linux that can then get called later.
        outFile = new File(pathToFileModuloEnding + FileLoader.OUT_FILE_ENDING);
    }

    /**
     * Delete the temporary files, so they do not clog up the file system.
     */
    private void deleteFiles() {
        cFile.delete();
        objectFile.delete();
        batFile.delete();
        exeFile.delete();
        outFile.delete();
        dataFile.delete();
    }

    /**
     * Run analysis.
     *
     * @param code
     *            the code
     * @param resultToStoreIn
     *            the result to store in
     * @return the list
     */
    public List<String> runAnalysis(final List<String> code,
                                    final Result resultToStoreIn) {
        // Array that returns the result
        final List<String> toReturn = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        List<String> errors = new ArrayList<String>();
        final String pathToNewFile =
                ABSOLUTE_PATH + FileLoader.getNewUniqueName(ABSOLUTE_PATH);
        createFiles(pathToNewFile);
        // Write the code to the file
        FileSaver.writeStringLinesToFile(code, cFile);
        final Process process = compileCFile(cFile);
        if (process != null) {
            CountDownLatch latch = new CountDownLatch(2);
            ThreadedBufferedReader outReader =
                    new ThreadedBufferedReader(
                            new BufferedReader(
                                    new InputStreamReader(process.getInputStream())
                                    ),
                            result, latch, false
                            );
            ThreadedBufferedReader errReader =
                    new ThreadedBufferedReader(
                            new BufferedReader(
                                    new InputStreamReader(process.getErrorStream())
                                    ),
                            errors, latch, false
                            );
            resultToStoreIn.setLastTmpResult(result);
            resultToStoreIn.setLastTmpResult(errors);
            // Wait for the process to finish;
            try {
                process.waitFor();
                // Wait for the readers to finish reading
                latch.await();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // Here the compilation is done
            result = new ArrayList<String>();
            errors = new ArrayList<String>();
            final Process programProcess = runWithData(pathToNewFile, dataFile);
            if (programProcess != null) {
                latch = new CountDownLatch(2);
                outReader =
                        new ThreadedBufferedReader(
                                new BufferedReader(
                                        new InputStreamReader(programProcess.getInputStream())
                                        ),
                                result, latch, false
                                );
                errReader =
                        new ThreadedBufferedReader(
                                new BufferedReader(
                                        new InputStreamReader(programProcess.getErrorStream())
                                        ),
                                errors, latch, false
                                );
                // Wait for the process to finish;
                try {
                    process.waitFor();
                    // Wait for the readers to finish reading
                    latch.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                for (Iterator<String> iterator = errors.iterator();
                        iterator.hasNext();) {
                    final String error = iterator.next();
                    System.out.println(error);
                }
                // Here the computation is done
                // the winning candidate gets printed as a number in the last
                // line
                // like this:
                // winner = x (,y, z ..) ( = if seats are selected)
                String winner = result.get(0);
                winner = winner.split(EQUALS_SIGN)[1].replaceAll("\\s+", "");
                if (winner.contains(COMMA)) {
                    final String[] winnerArray = winner.split(COMMA);
                    for (int i = 0; i < winnerArray.length; i++) {
                        toReturn.add(winnerArray[i]);
                    }
                } else {
                    toReturn.add(winner);
                }
            } else {
                ErrorLogger.log(COMPILATION_ERR_MSG);
                // Deletes the temporary file, so it does not clog up the
                // filesystem
            }
            outReader.finish();
            errReader.finish();
        } else {
            ErrorLogger.log(COMPILATION_ERR_MSG);
            // deletes the temporary file, so it does not clog up the filesystem
        }
        deleteFiles();
        return toReturn;
    }

    /**
     * Checks a file for errors. Has to be implemented system specific.
     *
     * @param toCheck
     *            the file to check
     * @return a process that is currently checking the file
     */
    protected abstract Process compileCFile(File toCheck);

    /**
     * Run with data.
     *
     * @param toRun
     *            a String that describes what name the file to run will have.
     *            The implementation will have to add the OS specific file
     *            ending.
     * @param file
     *            the data to be used by the program.
     * @return the running process that is running right now.
     */
    protected abstract Process runWithData(String toRun, File file);
}
