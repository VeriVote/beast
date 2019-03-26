package edu.pse.beast.propertychecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CBMCProcessFactoryTest {

    private CBMCProcessFactory procFac;

    @Before
    public void setup() {
        procFac = new CBMCProcessFactory(null, null, null, null);
    }

    @Test
    public void successSuccesTest() {
        List<String> toTest = new ArrayList<String>();
        toTest.add("VERIFICATION SUCCESSFUL");

        assertTrue(procFac.checkAssertionSuccess(toTest));
    }

    @Test
    public void failureSuccesTest() {
        List<String> toTest = new ArrayList<String>();
        toTest.add("VERIFICATION FAILED");

        assertTrue(procFac.checkAssertionFailure(toTest));
    }

    @Test
    public void succesFailureTest() {
        assertFalse(procFac.checkAssertionSuccess(null));
    }

    @Test
    public void failFailureTest() {
        assertFalse(procFac.checkAssertionFailure(null));
    }
}