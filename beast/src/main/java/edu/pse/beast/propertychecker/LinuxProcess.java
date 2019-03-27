package edu.pse.beast.propertychecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.UnifiedNameContainer;

public class LinuxProcess extends CBMCProcess {
    private static final String RELATIVE_PATH_TO_CBMC_64 = "/linux/cbmcLin/cbmc";

    // the time in milliseconds to wait for the termination of the process on linux
    private static final long WAITING_TIME = 3000;

    private static final String ENABLE_USER_INCLUDE = "-I";
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    // we want to compile all available c files, so the user does not need to
    // specify anything
    private static final String C_FILE_ENDING = ".c";

    /**
     * creates a new CBMC Checker for the windows OS
     *
     * @param voters     the amount of voters
     * @param candidates the amount of candidates
     * @param seats      the amount of seats
     * @param advanced   the string that represents the advanced options
     * @param toCheck    the file to check with cbmc
     * @param parent     the parent CheckerFactory, that has to be notified about
     *                   finished checking
     * @param result     the result
     */
    public LinuxProcess(int voters, int candidates, int seats,
                        String advanced, File toCheck, CheckerFactory parent,
                        Result result) {
        super(voters, candidates, seats, advanced, toCheck, parent, result);
    }

    @Override
    protected String sanitizeArguments(String toSanitize) {
        return toSanitize;
    }

    @Override
    public Process createProcess(File toCheck, int voters, int candidates,
                                 int seats, String advanced) {
        List<String> arguments = new ArrayList<String>();
        String cbmc =
                "\"" + new File(SuperFolderFinder.getSuperFolder()
                                + RELATIVE_PATH_TO_CBMC_64)
                        .getPath() + "\"";

        // enable the usage of includes in cbmc
        String userIncludeAndPath =
                "\"" + ENABLE_USER_INCLUDE + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER + "\"";
        // get all Files from the form "*.c" so we can include them into cbmc,
        List<String> allFiles = FileLoader.listAllFilesFromFolder(
                "\"" + SuperFolderFinder.getSuperFolder()
                + USER_INCLUDE_FOLDER + "\"", C_FILE_ENDING);
        if (!new File(cbmc.replace("\"", "")).exists()) {
            ErrorForUserDisplayer.displayError(
                    "Cannot find the cbmc program in the subfolder \"linux/cbmcLin/\", "
                            + "please download it from "
                            + "the cbmc website and place it there!");
        } else if (!new File(cbmc.replace("\"", "")).canExecute()) {
            ErrorForUserDisplayer.displayError("This program does not have the privileges "
                    + "to execute this program. \n "
                    + "Please change the access rights for the "
                    + "program \"/linux/cbmcLin/cbmc\" in the "
                    + "BEAST installation folder and try again");
        } else {
            arguments.add(cbmc.replace("\"", ""));
            arguments.add(userIncludeAndPath.replace("\"", ""));
            // wrap it in quotes, in case the path has spaces in it
            arguments.add(toCheck.getAbsolutePath().replace("\"", ""));
            // iterate over all "*.c" files from the include folder, to include them
            for (Iterator<String> iterator = allFiles.iterator(); iterator.hasNext();) {
                String toBeIncludedFile = (String) iterator.next();
                arguments.add(toBeIncludedFile.replace("\"", ""));
            }
            // here we supply this call with the correct values for the voters,
            // candidates and seats
            arguments.add("-D " + UnifiedNameContainer.getVoter() + "=" + voters);
            arguments.add("-D " + UnifiedNameContainer.getCandidate() + "=" + candidates);
            arguments.add("-D " + UnifiedNameContainer.getSeats() + "=" + seats);
            // we need the trace command to track the output on the command line
            arguments.add("--trace");
            if (advanced != null && advanced.length() > 0) {
                for (int i = 0; i < advanced.split(";").length; i++) {
                    String sanitized = sanitizeArguments(advanced.split(";")[i]);
                    if (sanitized.trim().length() > 0) {
                        arguments.add(sanitized);
                    }
                }
            }
            Process startedProcess = null;
            ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));
            try {
                startedProcess = prossBuild.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return startedProcess;
        }
        return null;
    }

    @Override
    protected void stopProcess() {
        if (!process.isAlive()) {
            ErrorLogger.log("Warning, process is not alive anymore");
            return;
        } else {
            process.destroyForcibly();
        }

        try {
            Thread.sleep(WAITING_TIME);
        } catch (InterruptedException e) {
        }
        if (process.isAlive()) {
            ErrorForUserDisplayer.displayError(
                    "Warning, the program was still alive after trying to stop it \n"
                    + "Please kill it manually if it is still alive, especially if "
                    + "it starts taking up a lot of ram");
        }
    }
}