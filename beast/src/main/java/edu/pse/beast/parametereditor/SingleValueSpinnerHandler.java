package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jonas
 */
public class SingleValueSpinnerHandler implements ChangeListener{
    private final JSpinner spinner;
    
    public SingleValueSpinnerHandler(JSpinner spinner) {
        this.spinner = spinner;
    }
    public Integer getValue() {
        return Integer.parseInt("" + spinner.getValue());
    }
    public void setValue(Integer val) {
        if (val <= 10000 && val >= 0) {
            spinner.setValue(val);
        } else {
            System.err.println("Bitte wählen sie eine Zahl zwischen 0 und 100000.");
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setValue(getValue());
    }
}
