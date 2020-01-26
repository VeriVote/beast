package edu.pse.beast.datatypes.propertydescription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.FormalExpErrorFinderTreeListener;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * The tests for SymbolicVariableList.
 *
 * @author Niels Hanselmann
 */
public class SymbolicVariableListTest {
    /** A test string. */
    private static final String TEST = "test";
    /** Another test string. */
    private static final String TEST_TWO = "test2";
    /** Remove Symbolic variable string. */
    private static final String REMOVE_SYMB_VARIABLE =
        "removeSymbolicVariable";

    /**
     * Instantiates a new symbolic variable list test.
     */
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
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(TEST, internalTypeContainer);
        assertSame(((LinkedList<SymbolicVariable>)
                    instance.getSymbolicVariables())
                .getFirst().getInternalTypeContainer().getInternalType(),
                InternalTypeRep.INTEGER);
        assertEquals(((LinkedList<SymbolicVariable>)
                    instance.getSymbolicVariables())
                .getFirst().getId(),
                TEST);
    }

    /**
     * Test of isVarIDAllowed method, of class SymbolicVariableList.
     */
    @Test
    public void testIsVarIDAllowed() {
        System.out.println("isVarIDAllowed");
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final SymbolicVariableList instance = new SymbolicVariableList();
        assertTrue(instance.isVarIDAllowed(TEST));
        instance.addSymbolicVariable(TEST, internalTypeContainer);
        assertFalse(instance.isVarIDAllowed(TEST));
    }

    /**
     * Test of setSymbolicVariableList method, of class SymbolicVariableList.
     */
    @Test
    public void testSetSymbolicVariableList() {
        System.out.println("setSymbolicVariableList");
        final LinkedList<SymbolicVariable> symbolicVariableList =
                new LinkedList<SymbolicVariable>();
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final SymbolicVariable var =
                new SymbolicVariable(TEST, internalTypeContainer);
        symbolicVariableList.add(var);
        final SymbolicVariable var2 =
                new SymbolicVariable(TEST_TWO, internalTypeContainer);
        symbolicVariableList.add(var2);

        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.setSymbolicVariableList(symbolicVariableList);
        assertEquals(((LinkedList<SymbolicVariable>)
                    instance.getSymbolicVariables())
                .getFirst().getId(),
                TEST);
        assertEquals(instance.getSymbolicVariables().get(1).getId(), TEST_TWO);
    }

    /**
     * Test of getSymbolicVariables method, of class SymbolicVariableList.
     */
    @Test
    public void testGetSymbolicVariables() {
        System.out.println("getSymbolicVariables");
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(TEST, internalTypeContainer);
        assertSame(((LinkedList<SymbolicVariable>)
                    instance.getSymbolicVariables())
                .getFirst().getInternalTypeContainer().getInternalType(),
                InternalTypeRep.INTEGER);
        assertEquals(((LinkedList<SymbolicVariable>)
                    instance.getSymbolicVariables())
                .getFirst().getId(),
                TEST);
    }

    /**
     * Test of removeSymbolicVariable method, of class SymbolicVariableList.
     */
    @Test
    public void testRemoveSymbolicVariableString() {
        System.out.println(REMOVE_SYMB_VARIABLE);
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(TEST, internalTypeContainer);
        assertTrue(instance.removeSymbolicVariable(TEST));
        assertTrue(instance.isVarIDAllowed(TEST));
    }

    /**
     * Test of removeSymbolicVariable method, of class SymbolicVariableList.
     */
    @Test
    public void testRemoveSymbolicVariableInt() {
        System.out.println(REMOVE_SYMB_VARIABLE);
        final int index = 0;
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.addSymbolicVariable(TEST, internalTypeContainer);
        instance.removeSymbolicVariable(index);
        assertTrue(instance.isVarIDAllowed(TEST));
    }

    /**
     * Test of addListener method, of class SymbolicVariableList.
     */
    @Ignore("test is difficult")
    @Test
    public void testAddListener() {
        System.out.println("addListener");
        final VariableListListener listener =
                new FormalExpErrorFinderTreeListener(
                    null, null, GUIController.getController().getElectionDescription()
              );
        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.addListener(listener);
        final InternalTypeContainer internalTypeContainer =
                new InternalTypeContainer(InternalTypeRep.INTEGER);
        instance.addSymbolicVariable(TEST, internalTypeContainer);
    }

    /**
     * Test of removeListener method, of class SymbolicVariableList.
     */
    @Ignore("test is difficult")
    @Test
    public void testRemoveListener() {
        System.out.println("removeListener");
        final VariableListListener listener = null;
        final SymbolicVariableList instance = new SymbolicVariableList();
        instance.removeListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
