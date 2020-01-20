package edu.pse.beast.parametereditor;

import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The MinMaxSpinValueHandler handles all user inputs on the ParameterEditor
 * which set a minimum and a maximum for a value using two JSpinners.
 *
 * @author Jonas Wohnig
 */
public final class MinMaxSpinValueHandler implements ChangeListener {
    /** The Constant MIN_VALUE. */
    private static final int MIN_VALUE = 1;

    /** The Constant MAX_VALUE. */
    private static final int MAX_VALUE = 10000;

    /** The min spinner. */
    private final JSpinner minSpinner;

    /** The max spinner. */
    private final JSpinner maxSpinner;

    /** The min before. */
    private Integer minBefore;

    /** The max before. */
    private Integer maxBefore;

    /** The reacts. */
    private boolean reacts;

    /** The has changed. */
    private boolean hasChanged;

    /**
     * Constructor.
     *
     * @param minimumSpinner
     *            JSpinner for the minimum
     * @param maximumSpinner
     *            JSpinner for the maximum
     */
    public MinMaxSpinValueHandler(final JSpinner minimumSpinner,
                                  final JSpinner maximumSpinner) {
        this.minSpinner = minimumSpinner;
        this.maxSpinner = maximumSpinner;
        setMinAndMax(1, 1);
        setHasChanged(false);
    }

    /**
     * Getter for the natural numbers between and including the minimum and the
     * maximum set by the user.
     *
     * @return ArrayList of Integers representing these values
     */
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = minBefore; i <= maxBefore; i++) {
            result.add(i);
        }
        return result;
    }

    /**
     * Setter for the minimum and maximum. Only allows values 1 to 10000 and
     * ensures that min gets the lower value.
     *
     * @param min
     *            new minimum
     * @param max
     *            new maximum
     */
    public synchronized void setMinAndMax(final Integer min,
                                          final Integer max) {
        reacts = true;
        if (min <= MAX_VALUE && min >= MIN_VALUE && max <= MAX_VALUE
                && max >= MIN_VALUE) {
            minSpinner.setValue(java.lang.Math.min(min, max));
            maxSpinner.setValue(java.lang.Math.max(min, max));
        } else {
            minSpinner.setValue(minBefore);
            maxSpinner.setValue(maxBefore);
        }
        Integer minAfter = Integer.parseInt(minSpinner.getValue().toString());
        Integer maxAfter = Integer.parseInt(maxSpinner.getValue().toString());
        if (minBefore == null || !(minBefore.equals(minAfter)
                && maxBefore.equals(maxAfter))) {
            minBefore = minAfter;
            maxBefore = maxAfter;
            setHasChanged(true);
        }
        reacts = false;
    }

    @Override
    public synchronized void stateChanged(final ChangeEvent e) {
        String minString = minSpinner.getValue().toString();
        String maxString = maxSpinner.getValue().toString();
        if ((minString + maxString).chars().allMatch(Character::isDigit)
                && reacts) {
            Integer min = Integer.parseInt(minString);
            Integer max = Integer.parseInt(maxString);
            if (min <= MAX_VALUE && min >= MIN_VALUE && max <= MAX_VALUE
                    && max >= MIN_VALUE) {
                if (e.getSource().equals(minSpinner)) {
                    if (min > maxBefore) {
                        max = min;
                    }
                } else if (e.getSource().equals(maxSpinner)) {
                    if (max < minBefore) {
                        min = max;
                    }
                }
                minSpinner.setValue(min);
                maxSpinner.setValue(max);
                setHasChanged(true);
            } else {
                minSpinner.setValue(minBefore);
                maxSpinner.setValue(maxBefore);
            }
        } else {
            minSpinner.setValue(minBefore);
            maxSpinner.setValue(maxBefore);
        }
        minBefore = Integer.parseInt(minSpinner.getValue().toString());
        maxBefore = Integer.parseInt(maxSpinner.getValue().toString());
    }

    /**
     * Toggles whether the chosen minimum and maximum react to user input (to
     * not interrupt checks).
     *
     * @param reactsToUser
     *            whether they react
     */
    protected synchronized void setReacts(final boolean reactsToUser) {
        this.reacts = reactsToUser;
    }

    /**
     * Returns whether the values of the JSpinners were changed since last time
     * saving.
     *
     * @return hasChanged
     */
    protected synchronized boolean hasChanged() {
        return hasChanged;
    }

    /**
     * Sets whether the values of the JSpinners were changed since last time
     * saving.
     *
     * @param changed
     *            whether they changed
     */
    protected synchronized void setHasChanged(final boolean changed) {
        this.hasChanged = changed;
    }
}
