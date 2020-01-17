package edu.pse.beast.datatypes.electioncheckparameter;

import java.util.concurrent.TimeUnit;

/**
 * The Class TimeOut.
 */
public class TimeOut {

    /** The amount. */
    private final long amount;

    /** The original unit. */
    private final TimeUnit originalUnit;

    /** The active. */
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
    public TimeOut(final TimeUnit unit, final long duration) {
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
     * Gets the duration.
     *
     * @return the duration of the timeout in milliseconds
     */
    public long getDuration() {
        return amount;
    }

    /**
     * Gets the orig unit.
     *
     * @return the unit with which the unit was given, so it might be used for
     *         saving in plain text again.
     */
    public TimeUnit getOrigUnit() {
        return originalUnit;
    }

    /**
     * Checks if is active.
     *
     * @return true, when the timeout
     */
    public boolean isActive() {
        return active;
    }
}
