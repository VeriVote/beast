package edu.pse.beast.propertychecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;

public class WindowsProcess extends CBMCProcess {
	protected int maxWaits = 5;

	public WindowsProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
		super(voters, candidates, seats, advanced, toCheck, parent);
	}

	@Override
	protected Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced) {

		// trace is mandatory under windows, or the counter example couldn't get
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
		toCheck = new File("./src/main/resources/c_tempfiles/test.c");
		ErrorLogger.log("WindowsProcess.java lien 48 has to be removed, when the code creation works");

		// because windows is weird the whole call that would get placed inside
		// VScmd has to be in one giant string
		String cbmcCall = "\"" + vsCmd + "\"" + " & " + cbmcEXE + " " + "\"" + toCheck.getAbsolutePath() + "\"" + " "
				+ arguments;

		// this call starts a new VScmd isntance and lets cbmc run in it
		ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", cbmcCall);

		System.out.println("AUFRUF:" + String.join(" ", prossBuild.command()));

		try {
			// save the new process in this var
			startedProcess = prossBuild.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
        	
        	
        	
    
        	OutputStreamWriter out = new OutputStreamWriter(process.getOutputStream());


        	 char ctrlBreak = (char)3;
        	 //Different testing way to send the ctrlBreak;
        	 try {
				out.write(ctrlBreak);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	
        	
        	
        	
         //   process.destroyForcibly();
            System.out.println("destroyed " + process.isAlive());
        }
		
		if (process.isAlive()) {
			ErrorLogger.log("Warning, the program was unable to shut down the CBMC Process \n"
					+ "Please kill it manually, especially if it starts taking up a lot of ram");
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getVScmdPath() throws IOException {
		// TODO: this could be cached, because it takes a significant time on
		// Windows every startup
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
						+ " Please search for it on your own and paste the path to the batch-file here!");

		// important that the check against null is done first, so invalid
		// inputs are caught without causing an error
		if (userInput != null && Files.isReadable(new File(userInput).toPath()) && userInput.contains("VsDevCmd.bat")) {
			return userInput;
		} else {
			System.err.println("The provided path did not lead to the command prompt. Shutting down now.");
			return null;
		}
	}

	@Override
	protected String sanitizeArguments(String toSanitize) {
		return toSanitize;
	}
}
