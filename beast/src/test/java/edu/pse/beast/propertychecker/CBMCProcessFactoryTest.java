package edu.pse.beast.propertychecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CBMCProcessFactoryTest {

    CBMCProcessFactory prosFac;

    @Before
    public void setup() {
        prosFac = new CBMCProcessFactory(null, null, null, null);
    }

    @Test
    public void successSuccesTest() {
        List<String> toTest = new ArrayList<String>();
        toTest.add("VERIFICATION SUCCESSFUL");

        assertTrue(prosFac.checkAssertionSuccess(toTest));
    }

    @Test
    public void failureSuccesTest() {
        List<String> toTest = new ArrayList<String>();
        toTest.add("VERIFICATION FAILED");

        assertTrue(prosFac.checkAssertionFailure(toTest));
    }

    @Test
    public void succesFailureTest() {
        assertFalse(prosFac.checkAssertionSuccess(null));
    }

    @Test
    public void failFailureTest() {
        assertFalse(prosFac.checkAssertionFailure(null));
    }
}