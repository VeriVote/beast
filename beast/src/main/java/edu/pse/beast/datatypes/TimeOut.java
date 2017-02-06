package edu.pse.beast.datatypes;

import java.util.concurrent.TimeUnit;

import edu.pse.beast.toolbox.ErrorLogger;

public class TimeOut {
    private final long amount;
    private final TimeUnit originalUnit;
    private final boolean active;

    /**
     * Creates a new TimeOut. Internally it saves the time in milliseconds.
     * 
     * @param unit
     *            the unit for the input time. For reference
     *            {@link java.util.concurrent.TimeUnit}
     * @param duration
     *            the duration how long the timeout is in the specified unit.
     */
    public TimeOut(TimeUnit unit, long duration) {
        amount = unit.toMillis(duration);
        originalUnit = unit;
        if (amount != 0) {
            active = true;
        } else {
            active = false;
        }
    }

    /**
     * creates an "empty" timeout, that shows that the checker should run until
     * he is finished or the user stopps the check from the outside.
     */
    public TimeOut() {
        amount = 0;
        originalUnit = TimeUnit.SECONDS;
        active = false;
    }

    /**
     * 
     * @return the duration of the timeout in milliseconds
     */
    public long getDuration() {
        if (!active) {
            ErrorLogger.log("The timeout isn't active, so this shouldn't be called");
        }
        return amount;
    }

    /**
     * 
     * @return the unit with which the unit was given, so it might be used for
     *         saving in plain text again.
     */
    public TimeUnit getOrigUnit() {
        if (!active) {
            ErrorLogger.log("The timeout isn't active, so this shouldn't be called");
        }
        return originalUnit;
    }

    /**
     * 
     * @return true, when the timeout
     */
    public boolean isActive() {
        return active;
    }
}
