package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The SingleValueSpinnerHandler handles all user inputs on the ParameterEditor
 * which use one JSpinner.
 *
 * @author Jonas Wohnig
 */
public class SingleValueSpinnerHandler implements ChangeListener {
    private static final int MAX_VALUE = 10000;
    private final JSpinner spinner;
    private Integer valBefore;
    private boolean reacts;
    private boolean hasChanged;

    /**
     * Constructor.
     *
     * @param jSpinner corresponding JSpinner
     */
    public SingleValueSpinnerHandler(final JSpinner jSpinner) {
        this.spinner = jSpinner;
        valBefore = getValue();
        setHasChanged(false);
    }

    /**
     * Getter for the value of the JSpinner.
     *
     * @return Integer of the value
     */
    public Integer getValue() {
        return Integer.parseInt(spinner.getValue().toString());
    }

    /**
     * Setter for the value of the JSpinner. Only allows values positive integers up
     * to 10000.
     *
     * @param val new value
     */
    public void setValue(final Integer val) {
        if (val <= MAX_VALUE && val >= 0) {
            spinner.setValue(val);
            setHasChanged(true);
        } else {
            spinner.setValue(valBefore);
        }
        valBefore = getValue();
    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        if (reacts) {
            setValue(getValue());
        } else {
            setValue(valBefore);
        }
    }

    /**
     * Toggles whether the value stored reacts to user input (to not interrupt
     * checks).
     *
     * @param reactsToUser whether it reacts
     */
    protected void setReacts(final boolean reactsToUser) {
        this.reacts = reactsToUser;
    }

    /**
     * Returns whether the value of the JSpinners was changed since last time
     * saving.
     *
     * @return hasChanged
     */
    protected boolean hasChanged() {
        return hasChanged;
    }

    /**
     * Sets whether the value of the JSpinners was changed since last time saving.
     *
     * @param changed whether it changed
     */
    protected void setHasChanged(final boolean changed) {
        this.hasChanged = changed;
    }
}
