package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jonas
 */
public class MinMaxSpinValueHandler implements ChangeListener{
    private final JSpinner minSpinner;
    private final JSpinner maxSpinner;
    
    public MinMaxSpinValueHandler(JSpinner minSpinner, JSpinner maxSpinner) {
        this.minSpinner = minSpinner;
        this.maxSpinner = maxSpinner;
    }
    public Integer[] getValues() {
        Integer[] result = new Integer[2];
        result[0] = Integer.parseInt("" + minSpinner.getValue());
        result[1] = Integer.parseInt("" + maxSpinner.getValue());
        return result;
    }
    public void setMinAndMax(Integer min, Integer max) {
        if (min <= 10000 && min >= 0 && max <= 10000 && max >= 0) {
        minSpinner.setValue(java.lang.Math.min(min, max));
        maxSpinner.setValue(java.lang.Math.max(min, max));
        } else {
            System.err.println("Bitte wählen sie eine Zahl zwischen 0 und 100000.");
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO: Implement
    }
}
