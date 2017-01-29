package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.event.ChangeListener;
import edu.pse.beast.datatypes.TimeOut;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Jonas
 */
public class TimeoutValueHandler implements ChangeListener{
    private final JSpinner timeoutSpinner;
    private final JComboBox timeoutUnit;
    
    public TimeoutValueHandler(JSpinner timeoutSpinner, JComboBox timeoutUnit) {
        this.timeoutSpinner = timeoutSpinner;
        this.timeoutUnit = timeoutUnit;
    }
    public TimeOut getTimeout() {
        Integer timeoutInt = Integer.parseInt("" + timeoutSpinner.getValue());
        String unit = "" + timeoutUnit.getSelectedItem();
        return new TimeOut(); //TODO: Create Timeout from Integer and String
    }
    public void setValue(TimeOut to) {
        timeoutSpinner.setValue(to.getDuration()); //TODO: Duration -> Integer
        timeoutUnit.setSelectedIndex(0); //TODO: Match units with selected item
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO: Implement
    }
}
