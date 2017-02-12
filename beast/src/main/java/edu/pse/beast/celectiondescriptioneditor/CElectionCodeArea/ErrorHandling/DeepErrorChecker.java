package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import java.util.List;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.propertychecker.OperatingSystems;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 * this class gives you access to an underlying, system specific compiler so our program
 * can check for deeper errors in the code, that we couldn't find on our own
 * @author Lukas
 *
 */
public class DeepErrorChecker {
private final OperatingSystems os;
    
    /**
     * creates a new checker, that first of all determines its operating system
     */
    public DeepErrorChecker() {
        this.os = determineOS();
    }
    
    /**
     * checks the given list of c code (one entry per line) for errors
     * @param toCheck the code to check
     * @return a list of codeErros
     */
    public List<CodeError> checkCodeForErrors(List<String> toCheck) {
        
        SystemSpecificErrorChecker errorChecker = null;
        
        switch (os) {
        case Linux:
            errorChecker = new LinuxErrorChecker();
            break;
        case Windows:
            errorChecker = new WindowsErrorChecker();
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(
                    "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
                            + "CBMCProcessFactory to be created");
            break;
        default:
            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
        }
        
        return errorChecker.checkCodeForErrors(toCheck);        
    }
    
    /**
     * determines the operating system that this program is running on
     * @return the OperatingSystem as the enum
     */
    private OperatingSystems determineOS() {
        String environment = System.getProperty("os.name");
        OperatingSystems determinedOS = null;
        if (environment.toLowerCase().contains("linux")) {
            determinedOS = OperatingSystems.Linux;
        } else if (environment.toLowerCase().contains("windows") && os == null) {
            determinedOS = OperatingSystems.Windows;
        } else if (environment.toLowerCase().contains("mac") && os == null) {
            determinedOS = OperatingSystems.Mac;
        } else {
            ErrorLogger.log("Sorry, your OS " + environment + " is not supported");
        }
        return determinedOS;
    }
}