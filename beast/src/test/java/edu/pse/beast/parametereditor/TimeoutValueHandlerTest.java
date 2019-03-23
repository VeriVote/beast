package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

import org.junit.Test;

import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;

/**
 *
 * @author Jonas
 */
public class TimeoutValueHandlerTest {

    /**
     * Test of class TimeoutValueHandler testing both input and output at different
     * configurations.
     */
    @Test
    public void testTimeout() {
        System.out.println("set/getTimeout");
        JComboBox<String> comboBox = new JComboBox<String>();
        JSpinner spinner = new JSpinner();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement("s");
        model.addElement("m");
        model.addElement("h");
        model.addElement("d");
        model.addElement("faultyEntry");
        comboBox.setModel(model);
        TimeoutValueHandler handler = new TimeoutValueHandler(spinner, comboBox);
        handler.setReacts(true);
        spinner.setValue(1);
        comboBox.setSelectedIndex(2);
        TimeOut expResult = new TimeOut(TimeUnit.SECONDS, 0);
        spinner.setValue(0);
        comboBox.setSelectedIndex(0);
        handler.setValue(handler.getTimeout());
        TimeOut result = handler.getTimeout();
        boolean minTest = (expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive());
        expResult = new TimeOut(TimeUnit.DAYS, 3653);
        spinner.setValue(3653);
        comboBox.setSelectedIndex(3);
        handler.setValue(handler.getTimeout());
        result = handler.getTimeout();
        boolean maxTest = (expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && result.isActive());
        expResult = new TimeOut(TimeUnit.DAYS, 0);
        spinner.setValue(-1);
        handler.setValue(handler.getTimeout());
        result = handler.getTimeout();
        boolean falseValueTest = (expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive());
        expResult = new TimeOut(TimeUnit.SECONDS, 0);
        spinner.setValue(42);
        comboBox.setSelectedIndex(4);
        handler.setValue(handler.getTimeout());
        result = handler.getTimeout();
        // System.out.println(handler.hasChanged() + " max " +
        // handler.getTimeout().getDuration() + " " +
        // handler.getTimeout().getOrigUnit());
        // System.out.println(handler.hasChanged() + " max " + expResult.getDuration() +
        // " " + expResult.getOrigUnit());
        boolean falseUnitTest = (expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive());
        assertTrue(minTest && maxTest && falseValueTest && falseUnitTest);
    }

    /**
     * Test of class TimeoutValueHandler's ability to toggle whether it reacts and
     * the log of whether it changed.
     */
    @Test
    public void testReactsAndHasChanged() {
        System.out.println("ReactsAndHasChanged");
        JComboBox<String> comboBox = new JComboBox<String>();
        JSpinner spinner = new JSpinner();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement("s");
        model.addElement("m");
        model.addElement("h");
        model.addElement("d");
        comboBox.setModel(model);
        TimeoutValueHandler handler = new TimeoutValueHandler(spinner, comboBox);
        boolean hasChangedBeforeTest = handler.hasChanged();
        handler.setReacts(true);
        spinner.setValue(11);
        comboBox.setSelectedIndex(2);
        handler.setValue(handler.getTimeout());
        boolean hasChangedAfterInput = handler.hasChanged();
        handler.setHasChanged(false);
        handler.setReacts(false);
        spinner.setValue(27);
        comboBox.setSelectedIndex(1);
        boolean hasChangedAfterStopReact = handler.hasChanged();
        assertTrue(!hasChangedBeforeTest && hasChangedAfterInput && !hasChangedAfterStopReact);
    }

}
