/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class SymbolicVariableTest {

    private final SymbolicVariable instance;

    /**
     * sets up the test
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
        assert (!result.isList());
    }

}
