package edu.pse.beast.parametereditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;

/**
 * The TimeoutValueHandler handles all user inputs on the ParameterEditor which
 * change the TimeOut using one JSpinner and one JComboBox.
 *
 * @author Jonas Wohnig
 */
public class TimeoutValueHandler implements ChangeListener, ActionListener {

    /** The Constant ONE. */
    private static final int ONE = 1;

    /** The Constant TWO. */
    private static final int TWO = 2;

    /** The Constant THREE. */
    private static final int THREE = 3;

    /** The timeout spinner. */
    private final JSpinner timeoutSpinner;

    /** The timeout unit. */
    private final JComboBox<String> timeoutUnit;

    /** The timeout before. */
    private TimeOut timeoutBefore;

    /** The reacts. */
    private boolean reacts;

    /** The has changed. */
    private boolean hasChanged;

    /**
     * Constructor.
     *
     * @param timeoutJSpinner
     *            JSpinner for the timeout value
     * @param timeoutUnits
     *            JComboBox for the timeout unit
     */
    public TimeoutValueHandler(final JSpinner timeoutJSpinner,
                               final JComboBox<String> timeoutUnits) {
        this.timeoutSpinner = timeoutJSpinner;
        timeoutJSpinner.setValue(0);
        this.timeoutUnit = timeoutUnits;
        timeoutBefore = getTimeout();
        setHasChanged(false);
    }

    /**
     * Getter for the timeout.
     *
     * @return TimeOut
     */
    public TimeOut getTimeout() {
        TimeOut timeOut = new TimeOut();
        Integer timeoutInt;
        if (timeoutSpinner.getValue() == "∞") {
            timeoutInt = 0;
        } else {
            timeoutInt = Integer.parseInt(timeoutSpinner.getValue().toString());
        }
        switch (timeoutUnit.getSelectedIndex()) {
        case 0:
            timeOut = new TimeOut(TimeUnit.SECONDS, timeoutInt.longValue());
            break;
        case ONE:
            timeOut = new TimeOut(TimeUnit.MINUTES, timeoutInt.longValue());
            break;
        case TWO:
            timeOut = new TimeOut(TimeUnit.HOURS, timeoutInt.longValue());
            break;
        case THREE:
            timeOut = new TimeOut(TimeUnit.DAYS, timeoutInt.longValue());
            break;
        default:
            break;
        }
        return timeOut;
    }

    /**
     * Setter for the timeout. Determines if the desired timeout is allowed and
     * adjusts the spinner and combobox accordingly.
     *
     * @param to
     *            new TimeOut
     */
    public void setValue(final TimeOut to) {
        if (reacts) {
            if (to.getDuration() <= 0) {
                timeoutSpinner.setValue(0);
            } else {
                timeoutSpinner.setValue(
                        to.getOrigUnit().convert(to.getDuration(),
                                                 TimeUnit.MILLISECONDS)
                );
            }
            setHasChanged(true);
            switch (to.getOrigUnit()) {
            case SECONDS:
                timeoutUnit.setSelectedIndex(0);
                break;
            case MINUTES:
                timeoutUnit.setSelectedIndex(ONE);
                break;
            case HOURS:
                timeoutUnit.setSelectedIndex(TWO);
                break;
            case DAYS:
                timeoutUnit.setSelectedIndex(THREE);
                break;
            default:
                System.err.println("Timeout kann nicht auf diesen"
                                    + " Wert gesetzt werden.");
                break;
            }
            timeoutBefore = getTimeout();
        } else {
            timeoutSpinner.setValue(
                    timeoutBefore.getOrigUnit().convert(
                            timeoutBefore.getDuration(),
                            TimeUnit.MILLISECONDS)
            );
            switch (timeoutBefore.getOrigUnit()) {
            case SECONDS:
                timeoutUnit.setSelectedIndex(0);
                break;
            case MINUTES:
                timeoutUnit.setSelectedIndex(ONE);
                break;
            case HOURS:
                timeoutUnit.setSelectedIndex(TWO);
                break;
            case DAYS:
                timeoutUnit.setSelectedIndex(THREE);
                break;
            default:
                System.err.println("Timeout kann nicht auf diesen"
                                    + " Wert gesetzt werden.");
                break;
            }
        }
    }

    /**
     * Toggles whether the timeout reacts to user input (to not interrupt
     * checks).
     *
     * @param reactsToUser
     *            whether it reacts
     */
    void setReacts(final boolean reactsToUser) {
        this.reacts = reactsToUser;
    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        setValue(getTimeout());
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        setValue(getTimeout());
    }

    /**
     * Returns whether the TimeOut was changed since last time saving.
     *
     * @return hasChanged
     */
    protected boolean hasChanged() {
        return hasChanged;
    }

    /**
     * Sets whether the TimeOut was changed since last time saving.
     *
     * @param changed
     *            whether it changed
     */
    protected void setHasChanged(final boolean changed) {
        this.hasChanged = changed;
    }
}
