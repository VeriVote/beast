package edu.pse.beast.toolbox;

/**
 *
 * @author Lukas
 *
 */
public class ErrorLogger {
    /**
     * logs all the errors to the error stream
     *
     * @param toLog the String to be logged to the error stream
     */
    public static void log(String toLog) {
        System.err.println(toLog);
    }
}