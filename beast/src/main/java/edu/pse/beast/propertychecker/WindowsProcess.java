package edu.pse.beast.propertychecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;

public class WindowsProcess extends CBMCProcess {
    protected int maxWaits = 20;
    
    
    
    public WindowsProcess(String arguments, File toCheck, CheckerFactory parent) {
        super(arguments, toCheck, parent);
        // TODO Auto-generated constructor stub
    }



    protected Process createProcess(File toCheck, String arguments) {
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
        
        //this call starts a new VScmd isntance and lets cbmc run in it
        String cbmcCall = "\"" + vsCmd + "\"" + " & " + cbmcEXE + " " + toCheck.getAbsolutePath();
        
        ProcessBuilder prossBuild = new ProcessBuilder("cmd.exe", "/c", cbmcCall, arguments);
        
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
        if (!super.process.isAlive()) {
            System.err.println("Warning, process isn't alive anymore");
            return;
        } else {
            try {
                // with this you can turn off cbmc in windows. it is a horrible
                // hack, but i haven't found a working improvement so far for
                // windows
                // This closes ALL CBMC instances, so keep that in mind.
                Runtime.getRuntime().exec("taskkill /F /IM cbmc.exe");
                // because the process wont shut down immediately we give it
                // some time.
                for (int i = 0; i < maxWaits; i++) {
                    try {
                        if (!process.isAlive()) {
                            // if it is shut down prematurely we will advance
                            break;
                        }
                        // sleep in 1 second intervals
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        ErrorLogger.log("There shouldn't be an interrupt here");
                    }
                }
            } catch (IOException e) {
                System.out.println("nochmal interrupted, was nicht sein sollte");
                e.printStackTrace();
            }
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
