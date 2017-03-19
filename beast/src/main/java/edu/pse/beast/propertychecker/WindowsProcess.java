package edu.pse.beast.propertychecker;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import edu.pse.beast.propertychecker.jna.Win32Process;
import edu.pse.beast.toolbox.*;

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

public class WindowsProcess extends CBMCProcess {
    private long WAITINGTIMEFORTERMINATION = 3000;

    private final String relativePathToCBMC32 = "/windows/cbmcWIN/cbmc.exe";
    private final String relativePathToCBMC64 = "/windows/cbmcWIN/cbmc64.exe";

    private final String enableUserInclude = "-I";
    private final String userIncludeFolder = "/core/user_includes/";

    // we want to compile all available c files, so the user doesn't have to
    // specify anything
    private final String cFileEnder = ".c";

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
    public WindowsProcess(int voters, int candidates, int seats, String advanced, File toCheck,
            CheckerFactory parent) {
        super(voters, candidates, seats, advanced, toCheck, parent);
    }

    @Override
    protected Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced) {

        String userCommands = String.join(" ", advanced.split(";"));

        // trace is mandatory under windows, or the counter example can't get
        // generated
        userCommands = userCommands + " --trace";

        // set the values for the voters, candidates and seats
        String arguments = userCommands + " -D V=" + voters + " -D C=" + candidates + " -D S=" + seats;

        // enable the usage of includes in cbmc
        String userIncludeAndPath = "\"" + enableUserInclude + SuperFolderFinder.getSuperFolder()
                + userIncludeFolder + "\"";

        //get all Files from the form "*.c" so we can include them into cbmc,
        List<String> allFiles = FileLoader.listAllFilesFromFolder("\"" + SuperFolderFinder.getSuperFolder() + userIncludeFolder +"\"", cFileEnder);
        
        //we have to give all available "*c" files to cbmc, in case the user used his own includes, so we combine them here
        String compileAllIncludesInIncludePath = StringUtils.join(allFiles, " ");
        
        String vsCmd = null;
        Process startedProcess = null;

        // try to get the vsCMD
        try {
            vsCmd = WindowsOStoolbox.getVScmdPath();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (vsCmd == null) {
            ErrorForUserDisplayer.displayError(
                    "The program \"VsDevCmd.bat\" couldn't be found. It is required to run this program, so "
                            + "please supply it with it. \n"
                            + " To do so, download the Visual Studio Community Version, install it (including "
                            + "the C++ pack). \n "
                            + "Then, search for the VsDevCmd.bat in it, and copy and paste it into the foler "
                            + "/windows/ in the BEAST installation folder.");
            return null;
        } else {

            // surround the vsCMD string with quotes, in case it has spaces in
            // it
            vsCmd = "\"" + vsCmd + "\"";

            // determine the os architecture
            boolean is64bit = false;
            if (System.getProperty("os.name").contains("Windows")) {
                // only 64 bit windows contains this variable
                is64bit = (System.getenv("ProgramFiles(x86)") != null);
            } else {
                ErrorLogger.log("The Windows procedure to call cbmc was used, even though this operating "
                        + "system isn't Windows!");
            }

            String cbmcEXE = "";

            // load the system specific cbmc programs
            if (is64bit) {
                cbmcEXE = new File(SuperFolderFinder.getSuperFolder() + relativePathToCBMC64).getPath();
            } else {
                cbmcEXE = new File(SuperFolderFinder.getSuperFolder() + relativePathToCBMC32).getPath();
            }

            if (!new File(cbmcEXE).exists()) {
                ErrorForUserDisplayer.displayError(
                        "Can't find the program \"cbmc.exe\" in the subfolger \"windows/cbmcWin/\". \n "
                                + "Please download it from the cbmc website and place it there!");
            } else if (!new File(cbmcEXE).canExecute()) {
                ErrorForUserDisplayer
                        .displayError("This program doesn't have the privileges to execute this program. \n "
                                + "Please change the access rights for the program \"/windows/cbmcWin/cbmc.exe\" "
                                + "in the BEAST installation folder and try again.");
            } else {

                // surround it with quotes, in case there are spaces in the name
                cbmcEXE = "\"" + cbmcEXE + "\"";

                // because windows is weird the whole call that will get placed
                // inside
                // VScmd has to be in one giant string
                String cbmcCall =  vsCmd + " & " + cbmcEXE + " " + userIncludeAndPath + " " + "\""
                        + toCheck.getAbsolutePath() + "\"" + " " + compileAllIncludesInIncludePath + " " + arguments;

                List<String> callInList = new ArrayList<String>();
                
                callInList.add(cbmcCall);
                
                File batFile = new File(toCheck.getParent() + "\\" + toCheck.getName().replace(".c", ".bat"));
                
                FileSaver.writeStringLinesToFile(callInList, batFile);
                
                // this call starts a new VScmd instance and lets cbmc run in it
               // ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", cbmcCall);

                ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", "\"" + batFile.getAbsolutePath() + "\"");
                
                try {
                    startedProcess = prossBuild.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                return startedProcess;
            }
        }
        return null;
    }

    @Override
    protected void stopProcess() {

        if (!process.isAlive()) {
            ErrorLogger.log("Warning, process isn't alive anymore");
            return;
        } else {

            // get the process id of the parent process (cmd in our case here)
            int pid = getWindowsProcessId(process);

            // generate a call to cmd to get all child processes of our
            // processID
            String cmdCall = "wmic process where (ParentProcessId=" + pid + ") get Caption,ProcessId";

            List<String> children = new ArrayList<String>();

            // latch to synchronize on, so we can be sure that every
            // childprocess is found and written to this List that contains the
            // children
            CountDownLatch latch = new CountDownLatch(1);

            ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", cmdCall);

            Process cbmcFinder = null;

            int cbmcPID = -1;

            try {
                cbmcFinder = prossBuild.start();
            } catch (IOException e) {
                ErrorLogger.log("Windows Process: " + e.getMessage());
            }

            if (cbmcFinder != null) {
                new ThreadedBufferedReader(new BufferedReader(new InputStreamReader(cbmcFinder.getInputStream())),
                        children, latch);

                // because the process only takes a second of time anyways and
                // an interrupt could prevent the shutting
                // down of cbmc, I put the waiting in a loop
                while (cbmcFinder.isAlive() || latch.getCount() > 0) {
                    try {
                        cbmcFinder.waitFor();
                        latch.await();
                    } catch (InterruptedException e) {
                        ErrorLogger
                                .log("This thread should not to be interrupted while waiting for these results");
                    }
                }

                // traverse all children
                for (Iterator<String> iterator = children.iterator(); iterator.hasNext();) {
                    String line = (String) iterator.next();

                    // trim it down so it only has a single space in between, so
                    // we can split there
                    line = line.trim().replaceAll(" +", " ");
                    if (line.split(" ").length == 2) { // filter out misformed
                                                       // lines
                        // search for the 32 and 64 bit version,
                        // so we don't have to make a whole new class for each
                        // of them
                        if (line.split(" ")[0].equals("cbmc.exe") || line.split(" ")[0].equals("cbmc64.exe")) {
                            if (cbmcPID == -1) {
                                // extract the PID from the line
                                cbmcPID = Integer.parseInt(line.split(" ")[1]);
                            } else {
                                ErrorLogger.log("Found multiple CBMC instances in this process tree. This is not "
                                        + "intended, only one will be closed, "
                                        + "please close the others by hand.");
                            }
                        }
                    }

                }
            } else {
                ErrorLogger.log("Couldn't start process");
            }

            if (cbmcPID != -1) {

                // now wrap the newly gotten process in a win32Process object to
                // terminate it then
                Win32Process cbmcProcess;
                try {
                    cbmcProcess = new Win32Process(cbmcPID);
                    cbmcProcess.terminate();
                    Thread.sleep(WAITINGTIMEFORTERMINATION);

                } catch (IOException e) {
                    ErrorLogger.log("Unable to create a reference to the CBMC process!");
                } catch (InterruptedException e) {

                }
            } else {
                /*
                 * if we are here, we can't find a child named cbmc. That means
                 * that a) cbmc just stopped, so we wait some seconds to see it
                 * the parent stopped too by then b) cbmc just can't be stopped
                 * this way, so the user has to do it manually
                 */

                try {
                    Thread.sleep(WAITINGTIMEFORTERMINATION);
                } catch (InterruptedException e) {

                }
            }
        }

        if (process.isAlive()) {
            ErrorForUserDisplayer.displayError("There was an attempt to stop the cbmc process, but after "
                    + (WAITINGTIMEFORTERMINATION / 1000d) + " seconds of waiting"
                    + " the parent root process was still alive, even though it should "
                    + "terminate itself when cbmc stopped. Please check, that the cbmc instance was "
                    + "closed properly and if not, please close it yourself!");
        }
    }

    @Override
    protected String sanitizeArguments(String toSanitize) {
        return toSanitize;
    }

    /**
     * 
     * @param proc
     *            the process whose processID you want to find out
     * @return the processID of the given process or -1 if it couldn't be
     *         determined
     */
    private int getWindowsProcessId(Process proc) {

        // credits for the methode to:
        // http://cnkmym.blogspot.de/2011/10/how-to-get-process-id-in-windows.html

        if (proc.getClass().getName().equals("java.lang.Win32Process")
                || proc.getClass().getName().equals("java.lang.ProcessImpl")) {

            /* determine the pid on windows plattforms */
            try {
                // get the handle by reflection
                Field f = proc.getClass().getDeclaredField("handle");
                f.setAccessible(true);
                long handLong = f.getLong(proc);
                Kernel32 kernel = Kernel32.INSTANCE;

                WinNT.HANDLE handle = new WinNT.HANDLE();

                // get the immutable value and set it accessible so we don't run
                // into errors
                Field toSet = handle.getClass().getDeclaredField("immutable");

                toSet.setAccessible(true);

                boolean savedState = toSet.getBoolean(handle);
                handle.setPointer(Pointer.createConstant(handLong));

                int pid = kernel.GetProcessId(handle);

                // set it back to the original value and make it unaccessable
                // again
                toSet.setBoolean(handle, savedState);

                toSet.setAccessible(false);

                return pid;

            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }

}