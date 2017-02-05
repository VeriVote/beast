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
    private boolean reacts;
    
    public SingleValueSpinnerHandler(JSpinner spinner) {
        this.spinner = spinner;
        valBefore = getValue();
    }
    public Integer getValue() {
        return Integer.parseInt(spinner.getValue().toString());
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
        if (reacts) {
            setValue(getValue());
        } else {
            setValue(valBefore);
        }
    }
    protected void setReacts(boolean reacts) {
        this.reacts = reacts;
    }
}
