package edu.pse.beast.propertychecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.UnifiedNameContainer;

/**
 * The Class LinuxProcess.
 *
 * @author Lukas Stapelbroek
 */
public final class LinuxProcess extends CBMCProcess {
    /** The Constant RELATIVE_PATH_TO_CBMC_64. */
    private static final String RELATIVE_PATH_TO_CBMC_64 = "/linux/cbmcLin/cbmc";

    /**
     * The Constant WAITING_TIME: The time in milliseconds to wait for
     * the termination of the process on linux.
     */
    private static final long WAITING_TIME = 3000;

    /** The Constant USER_INCLUDE_FOLDER. */
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    /**
     * Creates a new CBMC Checker for the windows OS.
     *
     * @param voters
     *            the amount of voters
     * @param candidates
     *            the amount of candidates
     * @param seats
     *            the amount of seats
     * @param advanced
     *            the string that represents the advanced options
     * @param unwindset
     *            the set of loop bounds with respective loop identifiers
     * @param toCheck
     *            the file to check with cbmc
     * @param parent
     *            the parent CheckerFactory, that has to be notified about
     *            finished checking
     * @param result
     *            the result
     */
    public LinuxProcess(final int voters, final int candidates, final int seats,
                        final String advanced, final List<String> unwindset,
                        final File toCheck, final CheckerFactory parent,
                        final Result result) {
        super(voters, candidates, seats, advanced, unwindset, toCheck, parent, result);
    }

    @Override
    protected String sanitizeArguments(final String toSanitize) {
        return toSanitize;
    }

    @Override
    public Process createProcess(final File toCheck, final int voters,
                                 final int candidates, final int seats,
                                 final List<String> unwindings,
                                 final String advanced) {
        final List<String> arguments = new ArrayList<String>(11);
        final String cbmc =
                new File(SuperFolderFinder.getSuperFolder()
                        + RELATIVE_PATH_TO_CBMC_64).getPath();
        // Enable the usage of includes in cbmc.
        final String userIncludeAndPath =
                getUserIncludeFiles(SuperFolderFinder.getSuperFolder()
                        + USER_INCLUDE_FOLDER);
        // Get all Files from the form "*.c" so we can include them into cbmc.
        final List<String> allFiles =
                FileLoader.listAllFilesFromFolder(
                        SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER,
                        FileLoader.C_FILE_ENDING);
        if (!new File(cbmc.replace(quote(), "")).exists()) {
            ErrorForUserDisplayer.displayError(
                    "Cannot find the cbmc program in the subfolder \"linux/cbmcLin/\", "
                            + "please download it from "
                            + "the cbmc website and place it there!");
        } else if (!new File(cbmc.replace(quote(), "")).canExecute()) {
            ErrorForUserDisplayer
                    .displayError("This program does not have the privileges "
                            + "to execute this program. \n "
                            + "Please change the access rights for the "
                            + "program \"/linux/cbmcLin/cbmc\" in the "
                            + "BEAST installation folder and try again");
        } else {
            arguments.add(cbmc.replace(quote(), ""));
            arguments.add(userIncludeAndPath.replace(quote(), ""));
            // Wrap it in quotes, in case the path has spaces in it.
            arguments.add(toCheck.getAbsolutePath().replace(quote(), ""));
            // Iterate over all "*.c" files from the include folder, to include
            // them
            for (Iterator<String> iterator = allFiles.iterator(); iterator.hasNext();) {
                final String toBeIncludedFile = iterator.next();
                arguments.add(toBeIncludedFile.replace(quote(), ""));
            }
            // We need the trace command to track the output on the command line
            arguments.add(CBMC_TRACE);
            arguments.add(CBMC_XML_OUTPUT);
            // Here we supply this call with the correct values for the candidates,
            // voters, and seats.
            arguments.add(passConstant(UnifiedNameContainer.getCandidate(), candidates));
            arguments.add(passConstant(UnifiedNameContainer.getVoter(), voters));
            arguments.add(passConstant(UnifiedNameContainer.getSeats(), seats));
            arguments.addAll(unwindSet(unwindings));
            // arguments.add("--unwind 7");
            if (advanced != null && advanced.length() > 0) {
                for (int i = 0; i < advanced.split(CCodeHelper.SEMICOLON).length; i++) {
                    final String sanitized =
                            sanitizeArguments(advanced.split(CCodeHelper.SEMICOLON)[i]);
                    if (sanitized.trim().length() > 0) {
                        arguments.add(sanitized);
                    }
                }
            }
            Process startedProcess = null;
            //final ProcessBuilder prossBuild = // ProcessBuilder produces problems
            //        new ProcessBuilder(arguments);//.toArray(new String[0]));
            //prossBuild.directory(new File(SuperFolderFinder.getSuperFolder()
            //                        + RELATIVE_PATH_TO_CBMC_64).getParentFile());
            // prossBuild.inheritIO();
            // prossBuild.environment().putAll(System.getenv());
            // prossBuild.redirectErrorStream(true);
            String argument = "";
            int a = 0;
            for (final String arg: arguments) {
                if (!argument.isEmpty() && a != arguments.size()) {
                    argument += " ";
                }
                argument += arg;
                a++;
            }
            try {
                startedProcess = // prossBuild.start();
                Runtime.getRuntime().exec(argument, null,
                                          new File(SuperFolderFinder.getSuperFolder()
                                                     + RELATIVE_PATH_TO_CBMC_64)
                                          .getParentFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return startedProcess;
        }
        return null;
    }

    @Override
    protected void stopProcess() {
        if (!getProcess().isAlive()) {
            ErrorLogger.log("Warning, process is not alive anymore");
            return;
        } else {
            getProcess().destroyForcibly();
        }
        try {
            Thread.sleep(WAITING_TIME);
        } catch (InterruptedException e) {
            // Do nothing
        }
        if (getProcess().isAlive()) {
            ErrorForUserDisplayer.displayError(
                    "Warning, the program was still alive after trying to stop it \n"
                            + "Please kill it manually if it is still alive, especially if "
                            + "it starts taking up a lot of ram");
        }
    }
}
