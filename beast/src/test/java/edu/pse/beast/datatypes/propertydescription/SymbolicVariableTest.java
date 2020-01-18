package edu.pse.beast.datatypes.propertydescription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * The tests for SymbolicVariable.
 *
 * @author Niels Hanselmann
 */
public class SymbolicVariableTest {
    /** The instance. */
    private final SymbolicVariable instance;

    /**
     * Sets up the test.
     */
    public SymbolicVariableTest() {
        InternalTypeContainer cont = new InternalTypeContainer(InternalTypeRep.INTEGER);
        this.instance = new SymbolicVariable("testId", cont);
    }

    /**
     * Test of getId method, of class SymbolicVariable.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        String result = instance.getId();
        assertEquals("testId", result);
    }

    /**
     * Test of getInternalTypeContainer method, of class SymbolicVariable.
     */
    @Test
    public void testGetInternalTypeContainer() {
        System.out.println("getInternalTypeContainer");
        InternalTypeContainer result = instance.getInternalTypeContainer();
        assertEquals(InternalTypeRep.INTEGER, result.getInternalType());
        assertFalse(result.isList());
    }

}
