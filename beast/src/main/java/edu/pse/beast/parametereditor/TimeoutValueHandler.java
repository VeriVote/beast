package edu.pse.beast.parametereditor;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.event.ChangeListener;
import edu.pse.beast.datatypes.TimeOut;
import javax.swing.event.ChangeEvent;
import java.util.concurrent.TimeUnit;

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
        TimeOut timeOut = new TimeOut();
        if(timeoutUnit.getSelectedIndex() == 0) {
            timeOut = new TimeOut(TimeUnit.SECONDS, timeoutInt.longValue());
        } else if(timeoutUnit.getSelectedIndex() == 1) {
            timeOut = new TimeOut(TimeUnit.MINUTES, timeoutInt.longValue());
        } else if(timeoutUnit.getSelectedIndex() == 2) {
            timeOut = new TimeOut(TimeUnit.HOURS, timeoutInt.longValue());
        } else if(timeoutUnit.getSelectedIndex() == 3) {
            timeOut = new TimeOut(TimeUnit.DAYS, timeoutInt.longValue());
        }
        return timeOut;
    }
    public void setValue(TimeOut to) {
        timeoutSpinner.setValue(to.getOrigUnit().convert(to.getDuration(), TimeUnit.MILLISECONDS));
        if(to.getOrigUnit().equals(TimeUnit.SECONDS)) {
            timeoutUnit.setSelectedIndex(0);
        } else if(to.getOrigUnit().equals(TimeUnit.MINUTES)) {
            timeoutUnit.setSelectedIndex(1);
        } else if(to.getOrigUnit().equals(TimeUnit.HOURS)) {
            timeoutUnit.setSelectedIndex(2);
        } else if(to.getOrigUnit().equals(TimeUnit.DAYS)) {
            timeoutUnit.setSelectedIndex(3);
        } else {
            System.err.println("Timeout kann nicht auf diesen Wert gesetzt werden.");
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setValue(getTimeout());
    }
}
