package edu.pse.beast.datatypes.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 *
 * @author Niels
 */
public class InternalTypeContainerTest {

    /**
     * Test of isList method, of class InternalTypeContainer.
     */
    @Test
    public void testIsList() {
	System.out.println("isList");
	InternalTypeContainer instance = new InternalTypeContainer(InternalTypeRep.INTEGER);
	boolean expResult = false;
	boolean result = instance.isList();
	assertEquals(expResult, result);
	InternalTypeContainer instance2 = new InternalTypeContainer(instance, InternalTypeRep.SEAT);
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
	InternalTypeContainer instance = new InternalTypeContainer(InternalTypeRep.INTEGER);
	InternalTypeRep expResult = InternalTypeRep.INTEGER;
	InternalTypeRep result = instance.getInternalType();
	assertEquals(expResult, result);
    }

    /**
     * Test of getListedType method, of class InternalTypeContainer.
     */
    @Test
    public void testGetListedType() {
	System.out.println("getListedType");
	InternalTypeContainer test = new InternalTypeContainer(InternalTypeRep.INTEGER);
	InternalTypeContainer instance = new InternalTypeContainer(test, InternalTypeRep.SEAT);
	InternalTypeContainer result = instance.getListedType();
	assertEquals(test, result);
    }

    /**
     * Test of getAccesTypeIfList method, of class InternalTypeContainer.
     */
    @Test
    public void testGetAccesTypeIfList() {
	System.out.println("getAccesTypeIfList");
	InternalTypeContainer test = new InternalTypeContainer(InternalTypeRep.INTEGER);
	InternalTypeContainer instance = new InternalTypeContainer(test, InternalTypeRep.SEAT);
	InternalTypeRep expResult = InternalTypeRep.SEAT;
	InternalTypeRep result = instance.getAccesTypeIfList();
	assertEquals(expResult, result);
    }

    /**
     * Test of getListLvl method, of class InternalTypeContainer.
     */
    @Test
    public void testGetListLvl() {
	System.out.println("getListLvl");
	InternalTypeContainer test = new InternalTypeContainer(InternalTypeRep.INTEGER);
	InternalTypeContainer instance = new InternalTypeContainer(test, InternalTypeRep.SEAT);
	int result = test.getListLvl();
	assertEquals(0, result);
	result = instance.getListLvl();
	assertEquals(1, result);
    }

}
