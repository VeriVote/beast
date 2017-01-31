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
    private Integer minBefore;
    private Integer maxBefore;
    
    public MinMaxSpinValueHandler(JSpinner minSpinner, JSpinner maxSpinner) {
        this.minSpinner = minSpinner;
        this.maxSpinner = maxSpinner;
        minBefore = Integer.parseInt("" + minSpinner.getValue());
        maxBefore = Integer.parseInt("" + maxSpinner.getValue());
    }
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(minBefore);
        result.add(maxBefore);
        return result;
    }
    public void setMinAndMax(Integer min, Integer max) {
        if (min <= 10000 && min >= 0 && max <= 10000 && max >= 0) {
        minSpinner.setValue(java.lang.Math.min(min, max));
        maxSpinner.setValue(java.lang.Math.max(min, max));
        } else {
            minSpinner.setValue(minBefore);
            maxSpinner.setValue(maxBefore);
            System.err.println("Bitte wählen sie Zahlen zwischen 0 und 100000.");
        }
        minBefore = Integer.parseInt("" + minSpinner.getValue());
        maxBefore = Integer.parseInt("" + maxSpinner.getValue());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Integer min = Integer.parseInt("" + minSpinner.getValue());
        Integer max = Integer.parseInt("" + maxSpinner.getValue());
        if(min <= 10000 && min >= 0 && max <= 10000 && max >= 0) {
            if(e.getSource().equals(minSpinner)) {
                if(min > maxBefore) {
                    max = min;
                }
            } else if(e.getSource().equals(maxSpinner)) {
                if(max < minBefore) {
                    min = max;
                }
            }
            minSpinner.setValue(min);
            maxSpinner.setValue(max);
        } else {
            minSpinner.setValue(minBefore);
            maxSpinner.setValue(maxBefore);
            System.err.println("Bitte wählen sie eine Zahl zwischen 0 und 100000.");
        }
        minBefore = Integer.parseInt("" + minSpinner.getValue());
        maxBefore = Integer.parseInt("" + maxSpinner.getValue());
    }
}
