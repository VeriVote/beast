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
    /** The start duration constant. */
    private static final String DURATION_START = "<duration>";
    /** The end duration constant. */
    private static final String DURATION_END = "</duration>";
    /** The start time unit constant. */
    private static final String TIME_UNIT_START = "<timeunit>";
    /** The end time unit constant. */
    private static final String TIME_UNIT_END = "</timeunit>";
    /** The line break constant. */
    private static final String LINE_BREAK = "\n";

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
        final String amount = DURATION_START + LINE_BREAK + timeOut.getDuration()
                            + LINE_BREAK + DURATION_END + LINE_BREAK;
        final String timeunit = TIME_UNIT_START + LINE_BREAK + timeOut.getOrigUnit().name()
                                + LINE_BREAK + TIME_UNIT_END + LINE_BREAK;
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
        String[] split = s.split(LINE_BREAK + DURATION_END + LINE_BREAK);
        Long duration = Long.parseLong(split[0].replace(DURATION_START + LINE_BREAK, ""));
        split = split[1].split(LINE_BREAK + TIME_UNIT_END + LINE_BREAK);
        TimeUnit timeUnit = TimeUnit.MINUTES;
        final String timeunit = split[0].replace(TIME_UNIT_START + LINE_BREAK, "");
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
