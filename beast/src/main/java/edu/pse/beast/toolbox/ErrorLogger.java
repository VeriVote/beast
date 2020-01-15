package edu.pse.beast.toolbox;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public final class ErrorLogger {
    private ErrorLogger() { }

    /**
     * Logs all the errors to the error stream.
     *
     * @param toLog the String to be logged to the error stream
     */
    public static void log(final String toLog) {
        System.err.println(toLog);
    }
}
