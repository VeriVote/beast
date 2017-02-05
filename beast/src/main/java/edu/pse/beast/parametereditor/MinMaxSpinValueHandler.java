package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 *
 * @author Jonas
 */
public class MinMaxSpinValueHandler implements ChangeListener {

    private final JSpinner minSpinner;
    private final JSpinner maxSpinner;
    private Integer minBefore;
    private Integer maxBefore;
    private boolean reacts;

    public MinMaxSpinValueHandler(JSpinner minSpinner, JSpinner maxSpinner) {
        this.minSpinner = minSpinner;
        this.maxSpinner = maxSpinner;
        setMinAndMax(1, 1);
    }

    public ArrayList<Integer> getValues() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = minBefore; i <= maxBefore; i++) {
            result.add(i);
        }
        return result;
    }

    public void setMinAndMax(Integer min, Integer max) {
        if (min <= 10000 && min >= 1 && max <= 10000 && max >= 1) {
            minSpinner.setValue(java.lang.Math.min(min, max));
            maxSpinner.setValue(java.lang.Math.max(min, max));
        } else {
            minSpinner.setValue(minBefore);
            maxSpinner.setValue(maxBefore);
        }
        minBefore = Integer.parseInt(minSpinner.getValue().toString());
        maxBefore = Integer.parseInt(maxSpinner.getValue().toString());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        String minString = (minSpinner.getValue().toString());
        String maxString = (maxSpinner.getValue().toString());
        if ((minString + maxString).chars().allMatch(Character::isDigit) && reacts) {
            Integer min = Integer.parseInt(minString);
            Integer max = Integer.parseInt(maxString);
            if (min <= 10000 && min >= 1 && max <= 10000 && max >= 1) {
                if (e.getSource().equals(minSpinner)) {
                    if (min > maxBefore) {
                        max = min;
                    }
                } else if (e.getSource().equals(maxSpinner)) {
                    if (max < minBefore) {
                        min = max;
                    }
                }
                minSpinner.setValue(min);
                maxSpinner.setValue(max);
            } else {
                minSpinner.setValue(minBefore);
                maxSpinner.setValue(maxBefore);
            }
        } else {
            minSpinner.setValue(minBefore);
            maxSpinner.setValue(maxBefore);
        }
        minBefore = Integer.parseInt(minSpinner.getValue().toString());
        maxBefore = Integer.parseInt(maxSpinner.getValue().toString());
    }

    protected void setReacts(boolean reacts) {
        this.reacts = reacts;
    }
}
