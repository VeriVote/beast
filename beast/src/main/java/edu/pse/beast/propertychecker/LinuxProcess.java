package edu.pse.beast.propertychecker;

import edu.pse.beast.toolbox.ErrorLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinuxProcess extends CBMCProcess {

    public LinuxProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
        super(voters, candidates, seats, advanced, toCheck, parent);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected String sanitizeArguments(String toSanitize) {
        // the linux version of cbmc has the trace command automatically, so we
        // have to remove it
        // or else cbmc would throw an error
    	return toSanitize;
   //     return toSanitize.replace("--trace", "");
    }

    @Override
    public Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced) {

        List<String> arguments = new ArrayList<String>();

    //    advanced = advanced + " --trace";
        
        advanced = sanitizeArguments(advanced);
        
        String[] argumentsToPass = new String[5 + advanced.split(";").length];
        
        
        //cbmc on linux wants every argument in a seperate string
        arguments.add("cbmc");
        
        arguments.add(toCheck.getAbsolutePath());
        
        arguments.add("-D V=" + voters);
        
        arguments.add("-D C=" + candidates);
        
        arguments.add("-D S=" + seats);

    //    arguments.add("--trace");
        
        for (int i = 5; i < argumentsToPass.length; i++) {
            String sanitized = sanitizeArguments(advanced.split(";")[i - 5]);
            
            if(sanitized.trim().length() > 0) {
                arguments.add(sanitized);
            }
        }

        Process startedProcess = null;

        // TODO this is just a debug file
        toCheck = new File("./src/main/resources/c_tempfiles/c_temp_file_success.c");
        ErrorLogger.log("LinuxProcess.java line 47 has to be removed, when the code creation works");

        ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));

        System.out.println("Started a new Process with the following command: " + String.join(" ", prossBuild.command()));

        try {
            // save the new process in this var
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
            process.destroyForcibly();
        }
        
        if (process.isAlive()) {
			ErrorLogger.log("Warning, the program was unable to shut down the CBMC Process \n"
					+ "Please kill it manually, especially if it starts taking up a lot of ram");
		}
    }

}
