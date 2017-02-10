package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The SingleValueSpinnerHandler handles all user inputs on the ParameterEditor
 * which use one JSpinner.
 * @author Jonas
 */
public class SingleValueSpinnerHandler implements ChangeListener {
    private final JSpinner spinner;
    private Integer valBefore;
    private boolean reacts;
    private boolean hasChanged;
    
    /**
     * Constructor
     * @param spinner corresponding JSpinner
     */
    public SingleValueSpinnerHandler(JSpinner spinner) {
        this.spinner = spinner;
        valBefore = getValue();
        setHasChanged(false);
    }
    /**
     * Getter for the value of the JSpinner
     * @return Integer of the value
     */
    public Integer getValue() {
        return Integer.parseInt(spinner.getValue().toString());
    }
    /**
     * Setter for the value of the JSpinner
     * @param val new value
     */
    public void setValue(Integer val) {
        if (val <= 10000 && val >= 0) {
            spinner.setValue(val);
            setHasChanged(true);
        } else {
            spinner.setValue(valBefore);
        }
        valBefore = getValue();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (reacts) {
            setValue(getValue());
        } else {
            setValue(valBefore);
        }
    }
    /**
     * Toggles whether the value stored reacts to user input
     * (to not interrupt checks)
     * @param reacts whether it reacts
     */
    protected void setReacts(boolean reacts) {
        this.reacts = reacts;
    }
    /**
     * Returns whether the value of the JSpinners was changed since last time saving.
     * @return hasChanged
     */
    protected boolean hasChanged() {
        return hasChanged;
    }
    /**
     * Sets whether the value of the JSpinners was changed since last time saving.
     * @param hasChanged whether it changed
     */
    protected void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
}
