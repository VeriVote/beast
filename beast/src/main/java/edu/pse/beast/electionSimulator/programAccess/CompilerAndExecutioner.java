package edu.pse.beast.electionSimulator.programAccess;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.SystemSpecificErrorChecker;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.propertychecker.OperatingSystems;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;

import java.util.List;

/**
 * this class gives you access to an underlying, system specific compiler so our
 * program can check for deeper errors in the code, that we couldn't find on our
 * own
 * 
 * @author Lukas
 *
 */
public final class CompilerAndExecutioner {

    private static SystemSpecificCompilerAndExecutioner specificComAndExe = null;

    private static boolean initialized = false;

    private CompilerAndExecutioner() {
        
    }
    
    /**
     * creates a new checker, that first of all determines its operating system
     * and then creates the correlating one
     */
    private static void init() {
        OperatingSystems os = determineOS();

        switch (os) {
        case Linux:
        	specificComAndExe = new LinuxCompilerAndRunner();
            break;
        case Windows:
        	specificComAndExe = new WindowsCompilerAndRunner();
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(
                    "MacOS is not supported yet, to simulate specific elections, extend the \"SystemSpecificCompilerandExecutioner\" .");
            break;
        default:
            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
        }

        initialized = true;
    }

    /**
     * checks the given list of c code (one entry per line) for errors
     * 
     * @param toCheck
     *            the code to check
     * @return a list of codeErros
     */
    public static int compileAndRun(List<String> toCheck, int[][] votingData) {
        if (!initialized) {
            init();
        }
        return specificComAndExe.runAnalysis(toCheck, votingData);
    }

    /**
     * determines the operating system that this program is running on
     * 
     * @return the OperatingSystem as the enum
     */
    private static OperatingSystems determineOS() {
        String environment = System.getProperty("os.name");
        OperatingSystems determinedOS = null;
        if (environment.toLowerCase().contains("linux")) {
            determinedOS = OperatingSystems.Linux;
        } else if (environment.toLowerCase().contains("windows")) {
            determinedOS = OperatingSystems.Windows;
        } else if (environment.toLowerCase().contains("mac")) {
            determinedOS = OperatingSystems.Mac;
        } else {
            ErrorLogger.log("Sorry, your OS " + environment + " is not supported");
        }
        return determinedOS;
    }
}