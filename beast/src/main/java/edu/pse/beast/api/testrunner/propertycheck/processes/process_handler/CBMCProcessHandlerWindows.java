package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

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

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.propertychecker.jna.Win32Process;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

public class CBMCProcessHandlerWindows implements CBMCProcessHandler {
	private static final String BLANK = " ";
	private static final long WAITING_TIME_FOR_TERMINATION = 8000;
	private static final double A_VERY_LONG_TIME = 1000d;
	/** The Constant CBMC_EXE. */
	private String CBMC_EXE = "cbmc.exe";
	/** The Constant CBMC64_EXE. */
	private String CBMC64_EXE = "cbmc64.exe";

	/** The Constant RELATIVE_PATH_TO_CBMC_64. */
	private String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/" + CBMC_EXE;

	// only needed in windows
	private String vsCmdPath; // =
	// "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";

	public CBMCProcessHandlerWindows(String vsCmdPath) {
		this.vsCmdPath = "\"" + vsCmdPath + "\"";
	}

	public String getVsCmdPath() {
		return vsCmdPath;
	}

	public void setVsCmdPath(String vsCmdPath) {
		this.vsCmdPath = vsCmdPath;
	}

	@Override
	public Process startCheckForParam(String sessionUUID, int V, int C, int S,
			String uuid, CBMCTestCallback cb, File cbmcFile, String loopBounds,
			CodeGenOptions codeGenOptions) throws IOException {
		String cbmcPath = new File(
				SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_CBMC)
						.getPath();

		String Space = " ";
		String completeCommand = vsCmdPath + Space + "&" + Space + "\""
				+ cbmcPath + "\"" + Space + cbmcFile.getAbsolutePath() + Space
				+ CBMCArgumentHelper.getConstCommands(codeGenOptions, V, C, S)
				+ Space + "--json-ui " + loopBounds;

		final File batFile = new File(
				cbmcFile.getParent() + "\\" + cbmcFile.getName().replace(
						FileLoader.C_FILE_ENDING, FileLoader.BAT_FILE_ENDING));

		List<String> list = List.of(completeCommand);
		FileSaver.writeStringLinesToFile(list, batFile);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
				batFile.getAbsolutePath());
		return pb.start();
	}

	@Override
	public CBMCProcessStarterType getType() {
		return CBMCProcessStarterType.WINDOWS;
	}

	// =====================end process start

	private int extractCBMCInstancePID(final int pid, final String line) {
		int cbmcPID = pid;
		// Trim it down so it only has a single space in between, so
		// we can split there.
		final String trimmedLine = line.trim().replaceAll(BLANK + "+", BLANK);

		// Filter out malformed lines.
		if (trimmedLine.split(BLANK).length == 2
				// Search for the 32 and 64 bit version,
				// so we do not have to make a whole new class for each
				// of them.
				&& (trimmedLine.split(BLANK)[0].equals(CBMC_EXE)
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

	private int findCBMCProcessPID(final int pid) {
		final String cmdCall = "wmic process where (ParentProcessId=" + pid
				+ ") get Caption,ProcessId";
		final List<String> children = new ArrayList<String>();
		// Latch to synchronize on, so we can be sure that every
		// child process is found and written to this List that contains the
		// children.
		final CountDownLatch latch = new CountDownLatch(1);
		final ProcessBuilder prossBuild = new ProcessBuilder(CMD_EXE, SLASH_C,
				cmdCall);
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
							new InputStreamReader(cbmcFinder.getInputStream())),
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
			for (Iterator<String> iterator = children.iterator(); iterator
					.hasNext();) {
				final String line = iterator.next();
				cbmcPID = extractCBMCInstancePID(cbmcPID, line);
			}
		} else {
			ErrorLogger.log("Could not start process.");
		}
		return cbmcPID;
	}

	private int getWindowsProcessId(final Process proc) {
		// Credits for the method to:
		// http://cnkmym.blogspot.de/2011/10/how-to-get-process-id-in-windows.html
		if (proc.getClass().getName().equals("java.lang.Win32Process")
				|| proc.getClass().getName().equals("java.lang.ProcessImpl")) {
			/* Determine the pid on windows plattforms */
			try {
				Field field = proc.getClass().getDeclaredField("handle");
				if (field != null) {
					WinNT.HANDLE handle = new WinNT.HANDLE();
					field.setAccessible(true);
					handle.setPointer(
							Pointer.createConstant(field.getLong(proc)));
					return Kernel32.INSTANCE.GetProcessId(handle);
				}			
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
	public void endProcess(Process p) {
		if (!p.isAlive()) {
			return;
		} else {
			// Get the process id of the parent process (cmd in our case here).
			final int pid = getWindowsProcessId(p);
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

	// =====================end process end

}
