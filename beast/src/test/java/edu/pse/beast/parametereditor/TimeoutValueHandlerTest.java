package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

import org.junit.Test;

import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;

/**
 * The tests for TimeoutValueHandler.
 *
 * @author Jonas Wohnig
 */
public class TimeoutValueHandlerTest {
    /** The Constant ZERO. */
    private static final int ZERO = 0;
    /** The Constant ONE. */
    private static final int ONE = 1;
    /** The Constant TWO. */
    private static final int TWO = 2;
    /** The Constant THREE. */
    private static final int THREE = 3;
    /** The Constant FOUR. */
    private static final int FOUR = 4;
    /** The Constant ELEVEN. */
    private static final int ELEVEN = 11;
    /** The Constant TWENTY_SEVEN. */
    private static final int TWENTY_SEVEN = 27;
    /** The Constant FORTY_TWO. */
    private static final int FORTY_TWO = 42;
    /** The Constant THREE_THOUSAND_SIX_HUNDRED_FIFTY_THREE. */
    private static final int THREE_THOUSAND_SIX_HUNDRED_FIFTY_THREE = 3653;
    /** The Constant MINUS_ONE. */
    private static final int MINUS_ONE = -ONE;

    /** Test string "s". */
    private static final String S = "s";
    /** Test string "m". */
    private static final String M = "m";
    /** Test string "h". */
    private static final String H = "h";
    /** Test string "d". */
    private static final String D = "d";

    /**
     * Test of class TimeoutValueHandler testing both input and output at different
     * configurations.
     */
    @Test
    public void testTimeout() {
        System.out.println("set/getTimeout");
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement(S);
        model.addElement(M);
        model.addElement(H);
        model.addElement(D);
        model.addElement("faultyEntry");
        final JComboBox<String> comboBox = new JComboBox<String>();
        final JSpinner spinner = new JSpinner();
        comboBox.setModel(model);
        final TimeoutValueHandler handler = new TimeoutValueHandler(spinner, comboBox);
        handler.setReacts(true);
        spinner.setValue(ONE);
        comboBox.setSelectedIndex(TWO);
        spinner.setValue(ZERO);
        comboBox.setSelectedIndex(ZERO);
        handler.setValue(handler.getTimeout());
        TimeOut expResult = new TimeOut(TimeUnit.SECONDS, ZERO);
        TimeOut result = handler.getTimeout();
        final boolean minTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive();
        spinner.setValue(THREE_THOUSAND_SIX_HUNDRED_FIFTY_THREE);
        comboBox.setSelectedIndex(THREE);
        handler.setValue(handler.getTimeout());
        expResult = new TimeOut(TimeUnit.DAYS, THREE_THOUSAND_SIX_HUNDRED_FIFTY_THREE);
        result = handler.getTimeout();
        final boolean maxTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && result.isActive();
        spinner.setValue(MINUS_ONE);
        handler.setValue(handler.getTimeout());
        expResult = new TimeOut(TimeUnit.DAYS, ZERO);
        result = handler.getTimeout();
        final boolean falseValueTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive();
        spinner.setValue(FORTY_TWO);
        comboBox.setSelectedIndex(FOUR);
        handler.setValue(handler.getTimeout());
        expResult = new TimeOut(TimeUnit.SECONDS, ZERO);
        result = handler.getTimeout();
        // System.out.println(handler.hasChanged() + " max " +
        // handler.getTimeout().getDuration() + " " +
        // handler.getTimeout().getOrigUnit());
        // System.out.println(handler.hasChanged() + " max " + expResult.getDuration() +
        // " " + expResult.getOrigUnit());
        final boolean falseUnitTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit())
                && !result.isActive();
        assertTrue(minTest && maxTest && falseValueTest && falseUnitTest);
    }

    /**
     * Test of class TimeoutValueHandler's ability to toggle whether it reacts and
     * the log of whether it changed.
     */
    @Test
    public void testReactsAndHasChanged() {
        System.out.println("ReactsAndHasChanged");
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement(S);
        model.addElement(M);
        model.addElement(H);
        model.addElement(D);
        final JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(model);
        final JSpinner spinner = new JSpinner();
        final TimeoutValueHandler handler = new TimeoutValueHandler(spinner, comboBox);
        final boolean hasChangedBeforeTest = handler.hasChanged();
        handler.setReacts(true);
        spinner.setValue(ELEVEN);
        comboBox.setSelectedIndex(TWO);
        handler.setValue(handler.getTimeout());
        final boolean hasChangedAfterInput = handler.hasChanged();
        handler.setHasChanged(false);
        handler.setReacts(false);
        spinner.setValue(TWENTY_SEVEN);
        comboBox.setSelectedIndex(ONE);
        final boolean hasChangedAfterStopReact = handler.hasChanged();
        assertTrue(!hasChangedBeforeTest && hasChangedAfterInput && !hasChangedAfterStopReact);
    }

}
