package edu.pse.beast.propertychecker;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import edu.pse.beast.propertychecker.jna.Win32Process;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

public class WindowsProcess extends CBMCProcess {
    private long waitingTimeForTermination = 3000;

    private final String vsCMDinRes = "/cbmcWIN/VsDevCmd.bat";

    public WindowsProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
        super(voters, candidates, seats, advanced, toCheck, parent);
    }

    @Override
    protected Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced) {

        advanced = String.join(" ", advanced.split(";"));

        // trace is mandatory under windows, or the counter example can't get
        // generated
        advanced = advanced + " --trace";

        // set the values for the voters, candidates and seats
        String arguments = advanced + " -D V=" + voters + " -D C=" + candidates + " -D S=" + seats;

        String vsCmd = null;
        Process startedProcess = null;

        try {
            vsCmd = getVScmdPath();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (vsCmd == null) {
            ErrorLogger.log("Cant find the VScmd. Is it installed correctly?");
        }

        String cbmcEXE = FileLoader.getFileFromRes("/cbmcWIN/cbmc.exe");

        // TODO this is just a debug file
        toCheck = new File("./src/main/resources/c_tempfiles/multiDim_fail.c");
   //     toCheck = new File("./src/main/resources/c_tempfiles/test_failure.c");
        ErrorLogger.log("WindowsProcess.java line 66 has to be removed, when the code creation works");

        // because windows is weird the whole call that would get placed inside
        // VScmd has to be in one giant string
        String cbmcCall = "\"" + vsCmd + "\"" + " & " + cbmcEXE + " " + "\"" + toCheck.getAbsolutePath() + "\"" + " "
                + arguments;

        // this call starts a new VScmd instance and lets cbmc run in it
        ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", cbmcCall);

        try {
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return startedProcess;
    }

    @Override
    protected void stopProcess() {

        if (!process.isAlive()) {
            ErrorLogger.log("Warning, process isn't alive anymore");
            return;
        } else {

            int pid = getWindowsProcessId(process);

            String cmdCall = "wmic process where (ParentProcessId=" + pid + ") get Caption,ProcessId";

            List<String> children = new ArrayList<String>();
            CountDownLatch latch = new CountDownLatch(1);

            ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", cmdCall);

            Process cbmcFinder = null;

            int cbmcPID = -1;

            try {
                cbmcFinder = prossBuild.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (cbmcFinder != null) {
                new ThreadedBufferedReader(new BufferedReader(new InputStreamReader(cbmcFinder.getInputStream())),
                        children, latch);

                // because the process only takes a second of time anyways and
                // an interrupt could prevent the sutting
                // down of cbmc, I put the waiting in a loop
                while (cbmcFinder.isAlive() || latch.getCount() > 0) {
                    try {
                        cbmcFinder.waitFor();
                        latch.await();
                    } catch (InterruptedException e) {
                        ErrorLogger.log("This thread has not to be interrupted while waiting for these results");
                    }
                }

                for (Iterator<String> iterator = children.iterator(); iterator.hasNext();) {
                    String line = (String) iterator.next();

                    // trim it down so it only has a single space in between, so
                    // we can split there
                    line = line.trim().replaceAll(" +", " ");
                    if (line.split(" ").length == 2) { // filter out misformed
                                                       // lines
                        if (line.split(" ")[0].equals("cbmc.exe") || line.split(" ")[0].equals("cbmc64.exe")) {
                            if (cbmcPID == -1) {
                                // extract the PID from the line
                                cbmcPID = Integer.parseInt(line.split(" ")[1]);
                            } else {
                                ErrorLogger
                                        .log("Found multiple CBMC instances in this process tree. This is not intended, "
                                                + "" + "only one will be closed, please close the others by hand.");
                            }
                        }
                    }

                }
            } else {
                ErrorLogger.log("Couldn't start process");
            }

            if (cbmcPID != -1) {

                Win32Process cbmcProcess;
                try {
                    cbmcProcess = new Win32Process(cbmcPID);
                    cbmcProcess.terminate();
                    Thread.sleep(waitingTimeForTermination);

                } catch (IOException e) {
                    ErrorLogger.log("Unable to create a reference to the CBMC process!");
                } catch (InterruptedException e) {

                }
            } else {
                /*
                 * if we are here, we can't find a child named cbmc. That means
                 * that a) cbmc just stopped, so we wait some seconds to see it
                 * the parent stopped too by then 
                 * b) cbmc just can't be stopped this way, so the user has to do it manually
                 */

                try {
                    Thread.sleep(waitingTimeForTermination);
                } catch (InterruptedException e) {

                }
            }

        }

        if (process.isAlive()) {
            ErrorLogger.log(
                    "There was an attempt to stop the cbmc process, but after " + (waitingTimeForTermination / 1000d)
                            + " seconds of waiting" + " the parent root process was still alive, even though it should "
                            + "terminate itself when cbmc stopped. Please check, that the cbmc instance was "
                            + "closed properly and if not, please close it yourself!");
        }
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private String getVScmdPath() throws IOException {

        String vsCMD = FileLoader.getFileFromRes(vsCMDinRes);
        if (Files.isReadable(new File(vsCMD).toPath())) {
            return vsCMD;
        } else { // we were unable to locate the command promp in the resources
                 // and search now for it in the common install directories

            Path x86 = new File("C:/Program Files (x86)").toPath();
            Path x64 = new File("C:/Program Files").toPath();
            String searchTerm = "Microsoft Visual Studio";
            String pathToBatch = "/Common7/Tools/VsDevCmd.bat";

            ArrayList<String> toSearch = new ArrayList<>();
            Files.list(x86).filter(Files::isReadable).filter(path -> path.toString().contains(searchTerm))
                    .forEach(VSPath -> toSearch.add(VSPath.toString()));
            Files.list(x64).filter(Files::isReadable).filter(path -> path.toString().contains(searchTerm))
                    .forEach(VSPath -> toSearch.add(VSPath.toString()));

            for (Iterator<String> iterator = toSearch.iterator(); iterator.hasNext();) {
                String toCheck = ((String) iterator.next()) + pathToBatch;

                if (Files.isReadable(new File(toCheck).toPath())) {
                    return toCheck;
                }
            }

            String userInput = JOptionPane
                    .showInputDialog("The progam was unable to find a Developer Command Prompt for Visual Studio. \n"
                            + " Please search for it on your own and paste the path to the batch-file (without quotes) here! \n"
                            + " Please copy the .bat also to the resources folder "
                            + "(named \"VsDevCmd.bat\") so it can be foudn automatically.");

            // important that the check against null is done first, so invalid
            // inputs are caught without causing an error
            if (userInput != null && Files.isReadable(new File(userInput).toPath())
                    && userInput.contains("VsDevCmd.bat")) {
                ErrorLogger.log("The path has to be saved here, so the user doesn't have to supply it everytime");
                return userInput;
            } else {
                System.err.println("The provided path did not lead to the command prompt. Shutting down now.");
                return null;
            }
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

                Field toSet = handle.getClass().getDeclaredField("immutable");

                toSet.setAccessible(true);

                boolean savedState = toSet.getBoolean(handle);

                handle.setPointer(Pointer.createConstant(handLong));

                int pid = kernel.GetProcessId(handle);

              
                toSet.setBoolean(handle, savedState);
                
                toSet.setAccessible(false);
                
                return pid;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

}