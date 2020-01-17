package edu.pse.beast.saverloader.staticsaverloaders;

import java.util.concurrent.TimeUnit;

import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;

/**
 * Implements static methods for creating saveStrings from TimeOut objects and
 * vice versa.
 *
 * @author Nikolai Schnell
 */
public final class TimeOutSaverLoader {
    /**
     * Instantiates a new time out saver loader.
     */
    private TimeOutSaverLoader() { }

    /**
     * Creates a String from a given TimeOut, that can then be saved to a file
     * and later given to createFromSaveString() to retrieve the saved object.
     *
     * @param timeOut
     *            the TimeOut
     * @return the saveString
     */
    public static String createSaveString(final TimeOut timeOut) {
        String amount = "<duration>\n" + timeOut.getDuration()
                    + "\n</duration>\n";
        String timeunit = "<timeunit>\n" + timeOut.getOrigUnit().name()
                    + "\n</timeunit>\n";
        return amount + timeunit;
    }

    /**
     * Creates a TimeOut object from a given, by createSaveString() generated,
     * saveString.
     *
     * @param s
     *            the SaveString
     * @return the TimeOut object
     */
    public static TimeOut createFromSaveString(final String s) {
        String[] split = s.split("\n</duration>\n");
        Long duration = Long.parseLong(split[0].replace("<duration>\n", ""));
        split = split[1].split("\n</timeunit>\n");
        TimeUnit timeUnit = TimeUnit.MINUTES;
        String timeunit = split[0].replace("<timeunit>\n", "");
        switch (timeunit) {
        case "MILLISECONDS":
            timeUnit = TimeUnit.MILLISECONDS;
            break;
        case "MINUTES":
            timeUnit = TimeUnit.MINUTES;
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            break;
        case "SECONDS":
            timeUnit = TimeUnit.SECONDS;
            duration = TimeUnit.MILLISECONDS.toSeconds(duration);
            break;
        case "HOURS":
            timeUnit = TimeUnit.HOURS;
            duration = TimeUnit.MILLISECONDS.toHours(duration);
            break;
        case "DAYS":
            timeUnit = TimeUnit.DAYS;
            duration = TimeUnit.MILLISECONDS.toDays(duration);
            break;
        default:
            break;
        }
        return new TimeOut(timeUnit, duration);
    }
}
