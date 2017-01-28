package edu.pse.beast.propertychecker;

import java.io.File;
import java.io.IOException;

import edu.pse.beast.toolbox.ErrorLogger;

public class LinuxProcess extends CBMCProcess {

    public LinuxProcess(String arguments, File toCheck, CheckerFactory parent) {
        super(arguments, toCheck, parent);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected String sanitizeArguments(String toSanitize) {
        // cbmc on linux already prints the trace
        
        //TODO make that fancier
        return toSanitize.replace(" --trace", "");
    }


    @Override
    public Process createProcess(File toCheck, String arguments) {
        Process startedProcess = null;

        ProcessBuilder prossBuild = new ProcessBuilder("cbmc", toCheck.getAbsolutePath(), arguments);
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
        
        if(process.isAlive()) {
            ErrorLogger.log("Warning, the program was unable to shut down the CBMC Process");
        }
    }


}
