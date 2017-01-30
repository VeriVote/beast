package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

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
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(Integer.parseInt("" + minSpinner.getValue()));
        result.add(Integer.parseInt("" + maxSpinner.getValue()));
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
        Integer min = Integer.parseInt("" + minSpinner.getValue());
        Integer max = Integer.parseInt("" + maxSpinner.getValue());
        setMinAndMax(min, max);
    }
}
