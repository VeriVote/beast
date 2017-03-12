/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.FormalExpErrorFinderTreeListener;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Niels
 */
public class SymbolicVariableListTest {

    public SymbolicVariableListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSymbolicVariable method, of class SymbolicVariableList.
     */
    @Test
    public void testAddSymbolicVariable() {
        System.out.println("addSymbolicVariable");
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(id, internalTypeContainer);
        assert (instance.getSymbolicVariables().getFirst().getInternalTypeContainer().getInternalType()
                == InternalTypeRep.INTEGER);
        assert (instance.getSymbolicVariables().getFirst().getId().equals("test"));
    }

    /**
     * Test of isVarIDAllowed method, of class SymbolicVariableList.
     */
    @Test
    public void testIsVarIDAllowed() {
        System.out.println("isVarIDAllowed");
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        SymbolicVariableList instance = new SymbolicVariableList();
        assert (instance.isVarIDAllowed("test"));
        instance.addSymbolicVariable(id, internalTypeContainer);
        assert (!instance.isVarIDAllowed("test"));
    }

    /**
     * Test of setSymbolicVariableList method, of class SymbolicVariableList.
     */
    @Test
    public void testSetSymbolicVariableList() {
        System.out.println("setSymbolicVariableList");
        LinkedList<SymbolicVariable> symbolicVariableList = new LinkedList<>();
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        SymbolicVariable var = new SymbolicVariable(id, internalTypeContainer);
        symbolicVariableList.add(var);
        SymbolicVariable var2 = new SymbolicVariable("test2", internalTypeContainer);
        symbolicVariableList.add(var2);

        SymbolicVariableList instance = new SymbolicVariableList();
        instance.setSymbolicVariableList(symbolicVariableList);
        assert (instance.getSymbolicVariables().getFirst().getId().equals("test"));
        assert (instance.getSymbolicVariables().get(1).getId().equals("test2"));
    }

    /**
     * Test of getSymbolicVariables method, of class SymbolicVariableList.
     */
    @Test
    public void testGetSymbolicVariables() {
        System.out.println("getSymbolicVariables");
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(id, internalTypeContainer);
        assert (instance.getSymbolicVariables().getFirst().getInternalTypeContainer().getInternalType()
                == InternalTypeRep.INTEGER);
        assert (instance.getSymbolicVariables().getFirst().getId().equals("test"));
    }

    /**
     * Test of removeSymbolicVariable method, of class SymbolicVariableList.
     */
    @Test
    public void testRemoveSymbolicVariableString() {
        System.out.println("removeSymbolicVariable");
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(id, internalTypeContainer);
        assert (instance.removeSymbolicVariable("test"));
        assert (instance.isVarIDAllowed("test"));
    }

    /**
     * Test of removeSymbolicVariable method, of class SymbolicVariableList.
     */
    @Test
    public void testRemoveSymbolicVariableInt() {
        System.out.println("removeSymbolicVariable");
        int index = 0;
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(id, internalTypeContainer);
        instance.removeSymbolicVariable(index);
        assert (instance.isVarIDAllowed("test"));
    }

    /**
     * Test of addListener method, of class SymbolicVariableList.
     */
    @Ignore("test is difficult")
    @Test
    public void testAddListener() {
        System.out.println("addListener");
        VariableListListener listener = new FormalExpErrorFinderTreeListener(null, null);
        SymbolicVariableList instance = new SymbolicVariableList();
        instance.addListener(listener);
        String id = "test";
        InternalTypeContainer internalTypeContainer = new InternalTypeContainer(InternalTypeRep.INTEGER);
        instance.addSymbolicVariable(id, internalTypeContainer);
    }

    /**
     * Test of removeListener method, of class SymbolicVariableList.
     */
    @Ignore("test is difficult")
    @Test
    public void testRemoveListener() {
        System.out.println("removeListener");
        VariableListListener listener = null;
        SymbolicVariableList instance = new SymbolicVariableList();
        instance.removeListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
