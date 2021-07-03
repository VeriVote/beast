package edu.pse.beast.zzz.electionsimulator.programaccess;

import java.util.List;

import edu.pse.beast.zzz.propertychecker.OperatingSystems;
import edu.pse.beast.zzz.propertychecker.Result;
import edu.pse.beast.zzz.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.zzz.toolbox.ErrorLogger;

/**
 * This class gives you access to an underlying, system specific compiler so our
 * program can check for deeper errors in the code, that we could not find on
 * our own.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class CompilerAndExecutionerOLD {

    /** The specific com and exe. */
    private static SystemSpecificCompilerAndExecutioner specificComAndExe;

    /** The initialized. */
    private static boolean initialized;

    /**
     * Instantiates a new compiler and executioner OLD.
     */
    private CompilerAndExecutionerOLD() { }

    /**
     * Creates a new checker, that first of all determines its operating system
     * and then creates the correlating one.
     */
    private static void init() {
        final OperatingSystems os = determineOS();
        switch (os) {
        case Linux:
            specificComAndExe = new LinuxCompilerAndRunner();
            break;
        case Windows:
            specificComAndExe = new WindowsCompilerAndRunner();
            break;
        case Mac:
            ErrorForUserDisplayer.displayError("MacOS is not supported yet. "
                    + "To simulate specific elections, "
                    + "extend the \"SystemSpecificCompilerandExecutioner\" .");
            break;
        default:
            ErrorLogger.log("Warning, your OS could not be determined or"
                            + " is not supported yet.");
        }

        initialized = true;
    }

    /**
     * Checks the given list of c code (one entry per line) for errors.
     *
     * @param toCheck
     *            the code to check
     * @param result
     *            the result
     * @return a list of codeErros
     */
    public static List<String> compileAndRun(final List<String> toCheck,
                                             final Result result) {
        if (!initialized) {
            init();
        }
        return specificComAndExe.runAnalysis(toCheck, result);
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