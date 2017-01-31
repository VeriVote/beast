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
    private Integer valBefore;
    
    public SingleValueSpinnerHandler(JSpinner spinner) {
        this.spinner = spinner;
        valBefore = getValue();
    }
    public Integer getValue() {
        return Integer.parseInt("" + spinner.getValue());
    }
    public void setValue(Integer val) {
        if (val <= 10000 && val >= 0) {
            spinner.setValue(val);
        } else {
            spinner.setValue(valBefore);
        }
        valBefore = getValue();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setValue(getValue());
    }
}
