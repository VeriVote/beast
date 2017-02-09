package edu.pse.beast.propertychecker;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.SuperFolderFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinuxProcess extends CBMCProcess {

	private final String relativePathToCBMC64 = "/linux/cbmcLin/cbmc";

	public LinuxProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
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
			ErrorLogger.log(
					"Can't find the cbmc program in the subfolger \"linux/cbmcLin/\", please download it from the cbmc website and place it there!");
		} else if (!new File(cbmc).canExecute()) {
			ErrorLogger.log(
					"This program doesn't have the privileges to execute this program, please install BEAST into another folder where it has the rights to execute programs, or start it with elevated privileges.");
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

			System.out.println(
					"Started a new Process with the following command: " + String.join(" ", prossBuild.command()));

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

		if (process.isAlive()) {
			ErrorLogger.log("Warning, the program was unable to shut down the CBMC Process \n"
					+ "Please kill it manually, especially if it starts taking up a lot of ram");
		}
	}

}
