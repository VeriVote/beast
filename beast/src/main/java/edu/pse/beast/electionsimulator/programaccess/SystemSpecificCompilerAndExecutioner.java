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
 * this is the superclass for system specific error checkers that gets
 * implemented for the needed operating systems
 *
 * @author Lukas Stapelbroek
 *
 */
public abstract class SystemSpecificCompilerAndExecutioner {

    private final String pathToTempFolder = "/core/c_tempfiles/";

    /**
     * constructor creates an error checker that compiles the c code and passes it
     * on to a system specific compiler
     */
    public SystemSpecificCompilerAndExecutioner() {
        // clear the folder where the files that get checked get saved to,
        // because sometimes they
        // persist from the last time BEAST was run
        try {
            FileUtils.cleanDirectory(new File(SuperFolderFinder.getSuperFolder() + pathToTempFolder));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<String> runAnalysis(List<String> code, Result resultToStoreIn) {
        // array that returns the result
        List<String> toReturn = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        List<String> errors = new ArrayList<String>();
        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
        String pathToNewFile = absolutePath + FileLoader.getNewUniqueName(absolutePath);
        File dataFile = new File(pathToNewFile + ".votingdata");

        // create two links to files, so in case an object file gets created we
        // can delete it afterwards too
        File cFile = new File(pathToNewFile + ".c");
        File objFile = new File(pathToNewFile + ".obj");

        // on windows we have to create a .bat file, so we create a reference to
        // the file
        // that will be created, to delete it afterwards
        File batFile = new File(pathToNewFile + ".bat");

        // on windows a .exe file will be created
        // here it will be created, to be delete it afterwards
        File exeFile = new File(pathToNewFile + ".exe");

        // the file that gets created on linux that can then get called later
        File outFile = new File(pathToNewFile + ".out");

        // write the code to the file
        FileSaver.writeStringLinesToFile(code, cFile);
        Process process = compileCfile(cFile);

        if (process != null) {
            CountDownLatch latch = new CountDownLatch(2);
            ThreadedBufferedReader outReader = new ThreadedBufferedReader(
                    new BufferedReader(new InputStreamReader(process.getInputStream())), result, latch, false);
            ThreadedBufferedReader errReader = new ThreadedBufferedReader(
                    new BufferedReader(new InputStreamReader(process.getErrorStream())), errors, latch, false);

            resultToStoreIn.setLastTmpResult(result);
            resultToStoreIn.setLastTmpResult(errors);

            // wait for the process to finish;
            try {
                process.waitFor();
                // wait for the readers to finish reading
                latch.await();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            // here the compilation is done
            result = new ArrayList<String>();
            errors = new ArrayList<String>();
            Process programProcess = runWithData(pathToNewFile, dataFile);

            if (programProcess != null) {
                latch = new CountDownLatch(2);
                outReader = new ThreadedBufferedReader(
                        new BufferedReader(new InputStreamReader(programProcess.getInputStream())), result, latch,
                        false);
                errReader = new ThreadedBufferedReader(
                        new BufferedReader(new InputStreamReader(programProcess.getErrorStream())), errors, latch,
                        false);

                // wait for the process to finish;
                try {
                    process.waitFor();
                    // wait for the readers to finish reading
                    latch.await();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                for (Iterator<String> iterator = errors.iterator(); iterator.hasNext();) {
                    String error = iterator.next();
                    System.out.println(error);
                }

                // here the computation is done

                // the winning candidate gets printed as a number in the last line
                // like this:
                // winner = x (,y, z ..) ( = if seats are selected)

                String winner = result.get(0);
                winner = winner.split("=")[1].replaceAll("\\s+", "");

                if (winner.contains(",")) {
                    String[] winnerArray = winner.split(",");
                    for (int i = 0; i < winnerArray.length; i++) {
                        toReturn.add(winnerArray[i]);
                    }
                } else {
                    toReturn.add(winner);
                }
            } else {
                ErrorLogger.log("Couldn't compile the source file");
                // deletes the temporary file, so it doesn't clog up the filesystem
            }
            outReader.finish();
            errReader.finish();
        } else {
            ErrorLogger.log("Couldn't compile the source file");
            // deletes the temporary file, so it doesn't clog up the filesystem
        }
        // deletes the temporary file, so it doesn't clog up the filesystem
        cFile.delete();
        objFile.delete();
        batFile.delete();
        exeFile.delete();
        outFile.delete();
        dataFile.delete();
        return toReturn;
    }

    /**
     * checks a file for errors. Has to be implemented system specific
     *
     * @param toCheck the file to check
     * @return a process that is currently checking the file
     */
    protected abstract Process compileCfile(File toCheck);

    /**
     *
     * @param toRun a String that describes what name the file to run will have. The
     *              implementation will have to add the OS specific file ending
     * @param data  the data to be used by the program
     * @return the running Process that is right now running
     */
    protected abstract Process runWithData(String toRun, File dataFile);
}