package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.List;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.propertychecker.OperatingSystems;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 * This class gives you access to an underlying, system specific compiler so our
 * program can check for deeper errors in the code, that we could not find on
 * our own.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class DeepErrorChecker {

    /** The error checker. */
    private static SystemSpecificErrorChecker errorChecker;

    /** The initialized. */
    private static boolean initialized;

    /**
     * Instantiates a new deep error checker.
     */
    private DeepErrorChecker() {

    }

    /**
     * Creates a new checker, that first of all determines its operating system
     * and then creates the correlating one.
     */
    private static void init() {
        final OperatingSystems os = determineOS();
        switch (os) {
        case Linux:
            errorChecker = new LinuxErrorChecker();
            break;
        case Windows:
            errorChecker = new WindowsErrorChecker();
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(
                    "MacOS is not supported yet, please implement the class "
                            + "CBMCProcess and add it then here in the "
                            + "CBMCProcessFactory to be created.");
            break;
        default:
            ErrorLogger.log("Warning, your OS could not be determined"
                            + " or is not supported yet.");
        }
        initialized = true;
    }

    /**
     * Checks the given list of c code (one entry per line) for errors.
     *
     * @param toCheck
     *            the code to check
     * @param lineOffset
     *            line offset
     * @return a list of codeErros
     */
    public static List<CodeError> checkCodeForErrors(final List<String> toCheck,
                                                     final int lineOffset) {
        if (!initialized) {
            init();
        }
        return errorChecker.checkCodeForErrors(toCheck, lineOffset);
    }

    /**
     * Determines the operating system that this program is running on.
     *
     * @return the OperatingSystem as the enum
     */
    private static OperatingSystems determineOS() {
        final String environment = System.getProperty("os.name");
        OperatingSystems determinedOS = null;
        if (environment.toLowerCase().contains("linux")) {
            determinedOS = OperatingSystems.Linux;
        } else if (environment.toLowerCase().contains("windows")) {
            determinedOS = OperatingSystems.Windows;
        } else if (environment.toLowerCase().contains("mac")) {
            determinedOS = OperatingSystems.Mac;
        } else {
            ErrorLogger.log("Sorry, your OS (" + environment
                            + ") is not supported");
        }
        return determinedOS;
    }
}
