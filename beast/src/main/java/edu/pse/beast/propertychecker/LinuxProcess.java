package edu.pse.beast.propertychecker;

import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.SuperFolderFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinuxProcess extends CBMCProcess {

    private final String relativePathToCBMC64 = "/linux/cbmcLin/cbmc";

    //the time in milliseconds to wait for the termination of the process on linux
    private final long waitingTime = 3000;
    
    /**
     * creates a new CBMC Checker for the windows OS
     * 
     * @param voters
     *            the amount of voters
     * @param candidates
     *            the amount of candidates
     * @param seats
     *            the amount of seats
     * @param advanced
     *            the string that represents the advanced options
     * @param toCheck
     *            the file to check with cbmc
     * @param parent
     *            the parent CheckerFactory, that has to be notified about
     *            finished checking
     */
    public LinuxProcess(int voters, int candidates, int seats, String advanced, File toCheck,
            CheckerFactory parent) {
        super(voters, candidates, seats, advanced, toCheck, parent);
    }

    @Override
    protected String sanitizeArguments(String toSanitize) {
        return toSanitize;
    }

    @Override
    public Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced) {

        List<String> arguments = new ArrayList<String>();

        String cbmc = new File(SuperFolderFinder.getSuperFolder() + relativePathToCBMC64).getPath();

        if (!new File(cbmc).exists()) {
            ErrorForUserDisplayer.displayError(
                    "Can't find the cbmc program in the subfolger \"linux/cbmcLin/\", please download it from "
                    + "the cbmc website and place it there!");
        } else if (!new File(cbmc).canExecute()) {
            ErrorForUserDisplayer
                    .displayError("This program doesn't have the privileges to execute this program. \n "
                            + "Please change the access rights for the program \"/linux/cbmcLin/cbmc\" in the "
                            + "BEAST installation folder and try again");
        } else {

            arguments.add(cbmc);

            arguments.add(toCheck.getAbsolutePath());

            // here we supply this call with the correct values for the voters,
            // candidates and seats
            arguments.add("-D V=" + voters);

            arguments.add("-D C=" + candidates);

            arguments.add("-D S=" + seats);

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
            ErrorLogger.log("Warning, process isn't alive anymore");
            return;
        } else {
            process.destroyForcibly();
        }
        
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
        }

        if (process.isAlive()) {
            ErrorLogger.log("Warning, the program was still alive after trying to stop it \n"
                    + "Please kill it manually if it is still alive, especially if it starts taking up a lot of ram");
        }
    }

}
