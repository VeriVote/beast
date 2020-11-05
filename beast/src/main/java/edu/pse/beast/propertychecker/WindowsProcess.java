package edu.pse.beast.propertychecker;

import static edu.pse.beast.electionsimulator.programaccess.WindowsCompilerAndRunner.CMD_EXE;
import static edu.pse.beast.electionsimulator.programaccess.WindowsCompilerAndRunner.SLASH_C;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import edu.pse.beast.propertychecker.jna.Win32Process;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.ThreadedBufferedReader;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.WindowsOStoolbox;

/**
 * The Class WindowsProcess.
 *
 * @author Lukas Stapelbroek
 */
public final class WindowsProcess extends CBMCProcess {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant SEMICOLON. */
    private static final String SEMICOLON = ";";

    /** The Constant A_VERY_LONG_TIME. */
    private static final double A_VERY_LONG_TIME = 1000d;
    /** The Constant WAITING_TIME_FOR_TERMINATION. */
    private static final long WAITING_TIME_FOR_TERMINATION = 8000;

    /** The Constant CBMC_EXE. */
    private static final String CBMC_EXE = "cbmc.exe";
    /** The Constant CBMC64_EXE. */
    private static final String CBMC64_EXE = "cbmc64.exe";

    /** The Constant RELATIVE_PATH_TO_CBMC_64. */
    private static final String RELATIVE_PATH_TO_CBMC =
            "/windows/cbmcWIN/" + CBMC_EXE;

    /** The Constant ENABLE_USER_INCLUDE. */
    private static final String ENABLE_USER_INCLUDE = "-I";

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
    public WindowsProcess(final int voters, final int candidates,
                          final int seats, final String advanced,
                          final List<String> unwindset, final File toCheck,
                          final CheckerFactory parent, final Result result) {
        super(voters, candidates, seats, advanced, unwindset, toCheck, parent, result);
    }

    /**
     * Flatten list of strings into giant string for command line call
     * concatenated with white spaces in-between.
     *
     * @param list the list of strings
     * @return the flattened string
     */
    private static String flatten(final List<String> list) {
        String flat = "";
        int i = 0;
        for (final String l: list) {
            flat += (i != 0 && i != list.size() ? BLANK : "") + l;
            i++;
        }
        return flat;
    }

    /**
     * Find CBMC instance.
     *
     * @param pid
     *            the cbmc PID
     * @param line
     *            the line
     * @return the cbmc pid
     */
    private static int extractCBMCInstancePID(final int pid, final String line) {
        int cbmcPID = pid;
        // Trim it down so it only has a single space in between, so
        // we can split there.
        final String trimmedLine = line.trim().replaceAll(BLANK + "+", BLANK);

        // Filter out malformed lines.
        if (trimmedLine.split(BLANK).length == 2
                // Search for the 32 and 64 bit version,
                // so we do not have to make a whole new class for each
                // of them.
                &&  (trimmedLine.split(BLANK)[0].equals(CBMC_EXE)
                        || trimmedLine.split(BLANK)[0].equals(CBMC64_EXE))) {
            if (pid == -1) {
                // Extract the PID from the line.
                cbmcPID = Integer.parseInt(trimmedLine.split(BLANK)[1]);
            } else {
                ErrorLogger.log("Found multiple CBMC instances"
                                + " in this process tree. This"
                                + " is not intended, only one"
                                + " will be closed, please"
                                + " close the others by hand.");
            }
        }
        return cbmcPID;
    }

    /**
     * Find CBMC Process.
     * @param pid
     *            the cbmc PID
     * @return the cbmc pid
     */
    private static int findCBMCProcessPID(final int pid) {
        final String cmdCall = "wmic process where (ParentProcessId=" + pid
                                + ") get Caption,ProcessId";
        final List<String> children = new ArrayList<String>();
        // Latch to synchronize on, so we can be sure that every
        // child process is found and written to this List that contains the
        // children.
        final CountDownLatch latch = new CountDownLatch(1);
        final ProcessBuilder prossBuild =
                new ProcessBuilder(CMD_EXE, SLASH_C, cmdCall);
        Process cbmcFinder = null;
        int cbmcPID = -1;
        try {
            cbmcFinder = prossBuild.start();
        } catch (IOException e) {
            ErrorLogger.log("Windows Process: " + e.getMessage());
        }
        if (cbmcFinder != null) {
            new ThreadedBufferedReader(
                    new BufferedReader(
                            new InputStreamReader(
                                    cbmcFinder.getInputStream()
                                    )
                            ),
                            children, latch, false);
            // Since the process only takes a second of time anyways and
            // an interrupt could prevent the shutting
            // down of cbmc, I put the waiting in a loop.
            while (cbmcFinder.isAlive() || latch.getCount() > 0) {
                try {
                    cbmcFinder.waitFor();
                    latch.await();
                } catch (InterruptedException e) {
                    ErrorLogger.log("This thread should not to be "
                                    + "interrupted while waiting "
                                    + "for these results.");
                }
            }
            // Traverse all children.
            for (Iterator<String> iterator = children.iterator();
                    iterator.hasNext();) {
                final String line = iterator.next();
                cbmcPID = extractCBMCInstancePID(cbmcPID, line);
            }
        } else {
            ErrorLogger.log("Could not start process.");
        }
        return cbmcPID;
    }

    /**
     * Create CBMC arguments.
     * @param voters
     *            the amount of voters
     * @param candidates
     *            the amount of candidates
     * @param seats
     *            the amount of seats
     * @param unwindset
     *            the unwind set
     * @param advanced
     *            the user arguments
     * @return the string of arguments for cbmc
     */
    private static String createArguments(final int voters, final int candidates,
                                          final int seats, final List<String> unwindset,
                                          final String advanced) {
        String userCommands = String.join(BLANK, advanced.split(SEMICOLON));
        // Trace is mandatory under windows, or the counterexample cannot get
        // generated.
        userCommands = userCommands + BLANK + CBMC_XML_OUTPUT;
        // Set the values for the voters, candidates and seats.
        final String arguments =
                userCommands + BLANK
                + passConstant(UnifiedNameContainer.getVoter(), voters)
                + BLANK
                + passConstant(UnifiedNameContainer.getCandidate(), candidates)
                + BLANK
                + passConstant(UnifiedNameContainer.getSeats(), seats)
                + BLANK
                + flatten(unwindSet(unwindset));
        return arguments;
    }

    /**
     * Try to get visual studio command.
     * If this is not possible, this method returns null.
     *
     * @return the visual studio command
     */
    private static String tryToGetVsCmd() {
        String vsCmd = null;
        // Try to get the vsCMD.
        try {
            vsCmd = WindowsOStoolbox.getVScmdPath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (vsCmd != null) {
            // Surround the vsCMD string with quotes, in case it has spaces in it.
            vsCmd = quote(vsCmd);
            // Determine the OS architecture
            final boolean is64bit;
            if (System.getProperty("os.name").contains("Windows")) {
                // Only 64 bit windows contains this variable.
                is64bit = System.getenv("ProgramFiles(x86)") != null;
            } else {
                ErrorLogger.log("The Windows procedure to call cbmc was used, "
                        + "even though this operating  system is not Windows!");
                is64bit = false;
            }

            if (!is64bit) {
                ErrorForUserDisplayer.displayError(
                        "CBMC only runs on 64 bit systems. Therefore, BEAST cannot be used"
                                + " with CBMC on 32 bit systems");
                vsCmd = null;
            }
        } else {
            ErrorForUserDisplayer.displayError(
                    "The program \"VsDevCmd.bat\" could not be found. "
                            + "It is required to run this program, so "
                            + "please supply it with it. \n"
                            + " To do so, download the Visual Studio Community Version, "
                            + "install it (including the C++ pack). \n "
                            + "Then, search for the VsDevCmd.bat in it, and copy&paste "
                            + "it into the folder /windows/ in the BEAST installation folder.");
        }
        return vsCmd;
    }

    /**
     * Get the cmbc executable.
     * In case it is not executable or does not exist, this method returns null.
     *
     * @return the cmbc executable
     */
    private static String getCmbcExe() {
        String cbmcEXE = "";
        // Load the system specific cbmc programs.
        cbmcEXE = new File(SuperFolderFinder.getSuperFolder()
                + RELATIVE_PATH_TO_CBMC).getPath();

        if (!new File(cbmcEXE).exists()) {
            ErrorForUserDisplayer
                    .displayError("Cannot find the program " + BLANK
                            + quote(CBMC_EXE) + BLANK
                            + "in the subfolder \"windows/cbmcWin/\". \n "
                            + "Please download it from the cbmc website "
                            + "and place it there!");
            cbmcEXE = null;
        } else if (!new File(cbmcEXE).canExecute()) {
            ErrorForUserDisplayer.displayError(
                    "This program does not have the privileges "
                            + "to execute this program. \n "
                            + "Please change the access rights for the program "
                            + quote("/windows/cbmcWin/" + CBMC_EXE) + BLANK
                            + "in the BEAST installation folder and try again.");
            cbmcEXE = null;
        } else {
            // Surround it with quotes, in case there are spaces in the name
            cbmcEXE = quote(cbmcEXE);
        }
        return cbmcEXE;
    }

    /**
     * Gets the windows process id.
     *
     * @param proc
     *            the process whose processID you want to find out
     * @return the processID of the given process or -1 if it could not be
     *         determined
     */
    private static int getWindowsProcessId(final Process proc) {
        // Credits for the method to:
        // http://cnkmym.blogspot.de/2011/10/how-to-get-process-id-in-windows.html
        if (proc.getClass().getName().equals("java.lang.Win32Process")
                || proc.getClass().getName().equals("java.lang.ProcessImpl")) {
            /* Determine the pid on windows plattforms */
            try {
                // Get the handle by reflection
                final Field f = proc.getClass().getDeclaredField("handle");
                f.setAccessible(true);
                final long handLong = f.getLong(proc);
                final Kernel32 kernel = Kernel32.INSTANCE;
                final WinNT.HANDLE handle = new WinNT.HANDLE();
                // Get the immutable value and set it accessible so we do not
                // run into errors.
                final Field toSet = handle.getClass().getDeclaredField("immutable");
                toSet.setAccessible(true);
                final boolean savedState = toSet.getBoolean(handle);
                handle.setPointer(Pointer.createConstant(handLong));
                final int pid = kernel.GetProcessId(handle);
                // Set it back to the original value and make it unaccessible
                // again.
                toSet.setBoolean(handle, savedState);
                toSet.setAccessible(false);
                return pid;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    protected Process createProcess(final File toCheck, final int voters,
                                    final int candidates, final int seats,
                                    final List<String> unwindings,
                                    final String advanced) {
        final String vsCmd = tryToGetVsCmd();
        final String cbmcEXE = getCmbcExe();
        if (vsCmd == null || cbmcEXE == null) {
            return null;
        }

        final String arguments =
                createArguments(voters, candidates, seats, unwindings, advanced);
        // Enable the usage of includes in cbmc.
        final String userIncludeAndPath =
                quote(ENABLE_USER_INCLUDE
                        + SuperFolderFinder.getSuperFolder()
                        + USER_INCLUDE_FOLDER);
        // Get all Files from the form "*.c" so we can include them into cbmc.
        final List<String> allFiles =
                FileLoader.listAllFilesFromFolder(
                        quote(SuperFolderFinder.getSuperFolder()
                                + USER_INCLUDE_FOLDER),
                        FileLoader.C_FILE_ENDING);
        // We have to give all available "*c" files to cbmc, in case the user
        // used their own includes, so we combine them here.
        final String compileAllIncludesInIncludePath =
                StringUtils.join(allFiles, BLANK);

        // Because Windows is weird the whole call that will get placed
        // inside VScmd has to be in one giant string
        final String cbmcCall =
                vsCmd + BLANK + "&" + BLANK + cbmcEXE + BLANK
                + userIncludeAndPath + BLANK
                + quote(toCheck.getAbsolutePath()) + BLANK
                + compileAllIncludesInIncludePath + BLANK
                + CBMC_TRACE + BLANK
                + CBMC_XML_OUTPUT + BLANK
                + arguments;

        final List<String> callInList = new ArrayList<String>();
        callInList.add(cbmcCall);
        final File batFile =
                new File(toCheck.getParent() + "\\"
                        + toCheck.getName().replace(FileLoader.C_FILE_ENDING,
                                FileLoader.BAT_FILE_ENDING));
        FileSaver.writeStringLinesToFile(callInList, batFile);
        // This call starts a new VScmd instance and lets cbmc run in it.
        // ProcessBuilder prossBuild = new ProcessBuilder(CMD_EXE,
        //                                                SLASH_C,
        //                                                cbmcCall);
        final ProcessBuilder prossBuild =
                new ProcessBuilder(CMD_EXE, SLASH_C,
                                   quote(batFile.getAbsolutePath()));

        Process startedProcess = null;
        try {
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startedProcess;
    }

    @Override
    protected void stopProcess() {
        if (!getProcess().isAlive()) {
            ErrorLogger.log("Warning, process is not alive anymore.");
        } else {
            // Get the process id of the parent process (cmd in our case here).
            final int pid = getWindowsProcessId(getProcess());
            // Generate a call to cmd to get all child processes of our
            // processID.
            final int cbmcPID = findCBMCProcessPID(pid);
            if (cbmcPID != -1) {
                // Now wrap the newly gotten process in a win32Process object to
                // terminate it then.
                final Win32Process cbmcProcess;
                try {
                    cbmcProcess = new Win32Process(cbmcPID);
                    cbmcProcess.terminate();
                    Thread.sleep(WAITING_TIME_FOR_TERMINATION);
                } catch (IOException e) {
                    ErrorLogger.log("Unable to create a reference to the"
                                    + " CBMC process!");
                } catch (InterruptedException e) {
                    // Do nothing.
                }
            } else {
                /*
                 * If we are here, we cannot find a child named cbmc. That means
                 * that a) cbmc just stopped, so we wait some seconds to see it
                 * the parent stopped too by then b) cbmc just cannot be stopped
                 * this way, so the user has to do it manually.
                 */
                try {
                    Thread.sleep(WAITING_TIME_FOR_TERMINATION);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }

            ErrorForUserDisplayer.displayError(
                    "There was an attempt to stop the cbmc process, but after "
                            + (WAITING_TIME_FOR_TERMINATION / A_VERY_LONG_TIME)
                            + " seconds of waiting,"
                            + " the parent root process "
                            + "was still alive, even though it should "
                            + "terminate itself when cbmc stopped. Please check "
                            + "that the cbmc instance was "
                            + "closed properly and if not, please close it yourself!");
        }
    }

    @Override
    protected String sanitizeArguments(final String toSanitize) {
        return toSanitize;
    }
}
