package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import java.util.concurrent.TimeUnit;

/**
 * The TimeoutValueHandler handles all user inputs on the ParameterEditor
 * which change the timeout using one JSpinner and one JComboBox.
 * @author Jonas
 */
public class TimeoutValueHandler implements ChangeListener, ActionListener {
    private final JSpinner timeoutSpinner;
    private final JComboBox timeoutUnit;
    private TimeOut timeoutBefore;
    private boolean reacts;
    private boolean hasChanged;

    /**
     * Constructor
     * @param timeoutSpinner JSpinner for the timeout value
     * @param timeoutUnit JComboBox for the timeout unit
     */
    public TimeoutValueHandler(JSpinner timeoutSpinner, JComboBox timeoutUnit) {
        this.timeoutSpinner = timeoutSpinner;
        timeoutSpinner.setValue(1);
        this.timeoutUnit = timeoutUnit;
        timeoutBefore = getTimeout();
        setHasChanged(false);
    }
    /**
     * Getter for the timeout
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
            case 1:
                timeOut = new TimeOut(TimeUnit.MINUTES, timeoutInt.longValue());
                break;
            case 2:
                timeOut = new TimeOut(TimeUnit.HOURS, timeoutInt.longValue());
                break;
            case 3:
                timeOut = new TimeOut(TimeUnit.DAYS, timeoutInt.longValue());
                break;
            default:
                break;
        }
        return timeOut;
    }
    /**
     * Setter for the timeout
     * @param to new TimeOut
     */
    public void setValue(TimeOut to) {
        if (reacts) {
            if (to.getDuration() <= 0) {
                timeoutSpinner.setValue(0);
            } else {
                timeoutSpinner.setValue(to.getOrigUnit().convert(to.getDuration(), TimeUnit.MILLISECONDS));
            }
            setHasChanged(true);
            switch (to.getOrigUnit()) {
                case SECONDS:
                    timeoutUnit.setSelectedIndex(0);
                    break;
                case MINUTES:
                    timeoutUnit.setSelectedIndex(1);
                    break;
                case HOURS:
                    timeoutUnit.setSelectedIndex(2);
                    break;
                case DAYS:
                    timeoutUnit.setSelectedIndex(3);
                    break;
                default:
                    System.err.println("Timeout kann nicht auf diesen Wert gesetzt werden.");
                    break;
            }
            timeoutBefore = getTimeout();
        } else {
            timeoutSpinner.setValue(timeoutBefore.getOrigUnit().convert(timeoutBefore.getDuration(), TimeUnit.MILLISECONDS));
            switch (timeoutBefore.getOrigUnit()) {
                case SECONDS:
                    timeoutUnit.setSelectedIndex(0);
                    break;
                case MINUTES:
                    timeoutUnit.setSelectedIndex(1);
                    break;
                case HOURS:
                    timeoutUnit.setSelectedIndex(2);
                    break;
                case DAYS:
                    timeoutUnit.setSelectedIndex(3);
                    break;
                default:
                    System.err.println("Timeout kann nicht auf diesen Wert gesetzt werden.");
                    break;
            }
        }
    }
    /**
     * Toggles whether the timeout reacts to user input
     * (to not interrupt checks)
     * @param reacts whether it reacts
     */
    void setReacts(boolean reacts) {
        this.reacts = reacts;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setValue(getTimeout());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setValue(getTimeout());
    }
    /**
     * Returns whether the TimeOut was changed since last time saving.
     * @return hasChanged
     */
    protected boolean hasChanged() {
        return hasChanged;
    }
    /**
     * Sets whether the TimeOut was changed since last time saving.
     * @param hasChanged whether it changed
     */
    protected void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
}
