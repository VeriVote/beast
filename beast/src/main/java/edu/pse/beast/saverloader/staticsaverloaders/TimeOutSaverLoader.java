package edu.pse.beast.saverloader.staticsaverloaders;

import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;

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
        final String amount = DURATION_START + lineBreak() + timeOut.getDuration()
                            + lineBreak() + DURATION_END + lineBreak();
        final String timeunit = TIME_UNIT_START + lineBreak() + timeOut.getOrigUnit().name()
                                + lineBreak() + TIME_UNIT_END + lineBreak();
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
        String[] split = s.split(lineBreak() + DURATION_END + lineBreak());
        Long duration = Long.parseLong(split[0].replace(DURATION_START + lineBreak(), ""));
        split = split[1].split(lineBreak() + TIME_UNIT_END + lineBreak());
        TimeUnit timeUnit = TimeUnit.MINUTES;
        final String timeunit = split[0].replace(TIME_UNIT_START + lineBreak(), "");
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
