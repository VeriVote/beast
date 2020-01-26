package edu.pse.beast.datatypes.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * The tests for InternalTypeContainer.
 *
 * @author Niels Hanselmann
 */
public class InternalTypeContainerTest {

    /**
     * Test of isList method, of class InternalTypeContainer.
     */
    @Test
    public void testIsList() {
        System.out.println("isList");
        final InternalTypeContainer instance =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        boolean expResult = false;
        boolean result = instance.isList();
        assertEquals(expResult, result);
        final InternalTypeContainer instance2 =
                new InternalTypeContainer(instance, InternalTypeRep.SEAT);
        expResult = true;
        result = instance2.isList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInternalType method, of class InternalTypeContainer.
     */
    @Test
    public void testGetInternalType() {
        System.out.println("getInternalType");
        final InternalTypeContainer instance =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final InternalTypeRep expResult = InternalTypeRep.INTEGER;
        final InternalTypeRep result = instance.getInternalType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getListedType method, of class InternalTypeContainer.
     */
    @Test
    public void testGetListedType() {
        System.out.println("getListedType");
        final InternalTypeContainer test =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final InternalTypeContainer instance =
                new InternalTypeContainer(test, InternalTypeRep.SEAT);
        final InternalTypeContainer result = instance.getListedType();
        assertEquals(test, result);
    }

    /**
     * Test of getAccessTypeIfList method, of class InternalTypeContainer.
     */
    @Test
    public void testGetAccessTypeIfList() {
        System.out.println("getAccessTypeIfList");
        final InternalTypeContainer test =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final InternalTypeContainer instance =
                new InternalTypeContainer(test, InternalTypeRep.SEAT);
        final InternalTypeRep expResult = InternalTypeRep.SEAT;
        final InternalTypeRep result = instance.getAccessTypeIfList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getListLvl method, of class InternalTypeContainer.
     */
    @Test
    public void testGetListLvl() {
        System.out.println("getListLvl");
        final InternalTypeContainer test =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final InternalTypeContainer instance =
                new InternalTypeContainer(test, InternalTypeRep.SEAT);
        int result = test.getListLvl();
        assertEquals(0, result);
        result = instance.getListLvl();
        assertEquals(1, result);
    }
}
