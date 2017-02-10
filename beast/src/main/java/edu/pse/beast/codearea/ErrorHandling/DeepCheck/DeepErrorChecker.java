package edu.pse.beast.codearea.ErrorHandling.DeepCheck;

import java.util.List;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.propertychecker.LinuxProcess;
import edu.pse.beast.propertychecker.OperatingSystems;
import edu.pse.beast.propertychecker.WindowsProcess;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;

public class DeepErrorChecker {
private final OperatingSystems os;
    
    public DeepErrorChecker() {
        this.os = determineOS();
    }
    
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