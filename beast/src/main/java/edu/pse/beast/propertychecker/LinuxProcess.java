package edu.pse.beast.propertychecker;

import java.io.File;
import java.io.IOException;

import edu.pse.beast.toolbox.ErrorLogger;

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
        return toSanitize.replace(" --trace", "");
    }

    @Override
    public Process createProcess(File toCheck, int voters, int candidates, int seats, String advanced) {
        
        

        String votersString = " -D V=" + voters;

        String candidatesString = " -D C=" + candidates;

        String seatsString = " -D S=" + seats;

        Process startedProcess = null;

        // TODO this is just a debug file
        toCheck = new File("./src/main/resources/c_tempfiles/test.c");
        ErrorLogger.log("LinuxProcess.java line 29 has to be removed, when the code creation works");

        ProcessBuilder prossBuild = new ProcessBuilder("cbmc", toCheck.getAbsolutePath(), votersString, candidatesString, seatsString);

        System.out.println(String.join(" ", prossBuild.command()));

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
            ErrorLogger.log("Warning, the program was unable to shut down the CBMC Process");
        }
    }

}
