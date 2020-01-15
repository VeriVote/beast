package edu.pse.beast.electionsimulator.programaccess;

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
public class LinuxCompilerAndRunner extends SystemSpecificCompilerAndExecutioner {
    // program that is to be used for checking
    private static final String COMPILER_STRING = "gcc";

    // this flag prohibits that file are creates by the compiler and
    // only the syntax is checked
    private static final String FIND_MISSING_RETURN_OPTION = "-Wreturn-type";

    // we want to compile to a specific name, so we can delete the file
    // then later on
    private static final String SET_OUTPUT_FILE_NAME = "-o ";
    private static final String ENABLE_USER_INCLUDE = "-I/";
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    // we want to compile all available c files, so the user does not need to
    // specify anything
    private static final String C_FILE_ENDING = ".c";
    private static final String OUT_FILE_ENDING = ".out";

    @Override
    protected Process compileCFile(final File toCheck) {
        // the name of the file
        final String nameOfOutFile = toCheck.getName().replace(C_FILE_ENDING, OUT_FILE_ENDING);
        final File outFile = new File(toCheck.getParentFile(), nameOfOutFile);
        final String compileToThis = SET_OUTPUT_FILE_NAME + outFile.getAbsolutePath();
        final String userIncludeAndPath
              = ENABLE_USER_INCLUDE + SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER;
        // get all Files from the form "*.c" so we can include them into cbmc,
        List<String> allFiles
              = FileLoader.listAllFilesFromFolder(
                        "\"" + SuperFolderFinder.getSuperFolder()
                        + USER_INCLUDE_FOLDER + "\"", C_FILE_ENDING);
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
        try { // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }

    @Override
    protected Process runWithData(final String toRun, final File dataFile) {
        Process startedProcess = null;
        // the list where the arguments for the call get saved in
        List<String> arguments = new ArrayList<String>();
        // on linux, our executable ends with .out
        // this argument calls the generated program
        arguments.add("./" + toRun + OUT_FILE_ENDING);
        // the absolute path to the file that holds
        arguments.add(dataFile.getAbsolutePath());
        ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));
        try { // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }
}
