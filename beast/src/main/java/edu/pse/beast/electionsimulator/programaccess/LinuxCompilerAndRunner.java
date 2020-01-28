package edu.pse.beast.electionsimulator.programaccess;

import static edu.pse.beast.toolbox.CCodeHelper.space;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 * The Linux implementation for checking the code. This implementation uses gcc
 * for checking.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class LinuxCompilerAndRunner
        extends SystemSpecificCompilerAndExecutioner {
    /**
     * The Constant COMPILER_STRING: Program that is to be used
     * for checking.
     */
    private static final String COMPILER_STRING = "gcc";

    /**
     * The Constant FIND_MISSING_RETURN_OPTION: This flag prohibits that
     * files are creates by the compiler and only the syntax is checked.
     */
    private static final String FIND_MISSING_RETURN_OPTION = "-Wreturn-type";

    /**
     * The Constant SET_OUTPUT_FILE_NAME: We want to compile to
     * a specific name, so we can delete the file then later on.
     */
    private static final String SET_OUTPUT_FILE_NAME = "-o ";

    /** The Constant ENABLE_USER_INCLUDE. */
    private static final String ENABLE_USER_INCLUDE = "-I/";

    /** The Constant USER_INCLUDE_FOLDER. */
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    @Override
    protected Process compileCFile(final File toCheck) {
        // The name of the file
        final String nameOfOutFile =
                toCheck.getName().replace(FileLoader.C_FILE_ENDING,
                                          FileLoader.OUT_FILE_ENDING);
        final File outFile =
                new File(toCheck.getParentFile(), nameOfOutFile);
        final String compileToThis =
                SET_OUTPUT_FILE_NAME + outFile.getAbsolutePath();
        final String userIncludeAndPath =
                ENABLE_USER_INCLUDE
                + SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER;
        Process startedProcess = null;
        final List<String> arguments = new ArrayList<String>();
        // Add the arguments needed for the call
        arguments.add(COMPILER_STRING);
        arguments.add(userIncludeAndPath);
        arguments.add(FIND_MISSING_RETURN_OPTION);
        // Add the path to the created file that should be checked
        arguments.add(toCheck.getAbsolutePath());
        // Get all Files from the form "*.c" so we can include them into cbmc,
        final List<String> allFiles = FileLoader.listAllFilesFromFolder(
                FileLoader.QUOTE + SuperFolderFinder.getSuperFolder()
                    + USER_INCLUDE_FOLDER + FileLoader.QUOTE,
                FileLoader.C_FILE_ENDING);
        // Iterate over all "*.c" files from the include folder, to include them
        for (Iterator<String> iterator = allFiles.iterator();
                iterator.hasNext();) {
            final String toBeIncludedFile = iterator.next();
            arguments.add(
                    toBeIncludedFile.replace(FileLoader.QUOTE, "")
                        .replace(space(), "\\ "));
        }
        // Defines the position to what place the compiled files should be sent
        arguments.add(compileToThis);
        final ProcessBuilder prossBuild =
                new ProcessBuilder(arguments.toArray(new String[0]));
        try { // Start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }

    @Override
    protected Process runWithData(final String toRun, final File dataFile) {
        Process startedProcess = null;
        // The list where the arguments for the call get saved in
        final List<String> arguments = new ArrayList<String>();
        // On Linux, our executable ends with .out
        // this argument calls the generated program
        arguments.add("./" + toRun + FileLoader.OUT_FILE_ENDING);
        // The absolute path to the file that holds
        arguments.add(dataFile.getAbsolutePath());
        final ProcessBuilder prossBuild =
                new ProcessBuilder(arguments.toArray(new String[0]));
        try { // Start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }
}
