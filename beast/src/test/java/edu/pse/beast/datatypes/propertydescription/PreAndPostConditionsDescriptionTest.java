package edu.pse.beast.datatypes.propertydescription;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * The tests for PreAndPostConditionsDescription.
 *
 * @author Niels Hanselmann
 */
public class PreAndPostConditionsDescriptionTest {
    /** A test name string. */
    private static final String NAME = "name";
    /** A test string. */
    private static final String TEST = "test";
    /** A post test string. */
    private static final String POST_TEST = "postTest";
    /** A pre test string. */
    private static final String PRE_TEST = "preTest";
    /** Another test string. */
    private static final String TEST_TWO = "test2";

    /**
     * Test of getName method, of class PreAndPostConditionsDescription.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        final PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(NAME);
        final String result = instance.getName();
        assertEquals(NAME, result);
    }

    /**
     * Test of getSymbolicVariableList method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testGetSymbolicVariableList() {
        System.out.println("getSymbolicVariableList");
        final SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable(TEST, new InternalTypeContainer(InternalTypeRep.INTEGER));
        final PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(NAME, null,
                                                    null, null,
                                                    symbVarList);
        final List<SymbolicVariable> result = instance.getSymbolicVariablesAsList();
        assertEquals(symbVarList.getSymbolicVariables().get(0), result.get(0));
    }

    /**
     * Test of getSymVarList method, of class PreAndPostConditionsDescription.
     */
    @Test
    public void testGetSymVarList() {
        System.out.println("getSymVarList");
        final SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable(TEST,
                                        new InternalTypeContainer(InternalTypeRep.INTEGER));
        final PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(NAME, null, null, null, symbVarList);
        final SymbolicVariableList result = instance.getSymVarList();
        assertEquals(symbVarList.getSymbolicVariables().get(0),
                     result.getSymbolicVariables().get(0));
    }

    /**
     * Test of setSymbolicVariableList method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testSetSymbolicVariableList() {
        System.out.println("setSymbolicVariableList");
        final SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable(TEST, new InternalTypeContainer(InternalTypeRep.INTEGER));
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription(NAME);
        instance = new PreAndPostConditionsDescription(instance.getName(),
                                                       instance.getPreConditionsDescription(),
                                                       instance.getPostConditionsDescription(),
                                                       instance.getBoundedVarDescription(),
                                                       symbVarList);
        final SymbolicVariableList result = instance.getSymVarList();
        assertEquals(symbVarList.getSymbolicVariables().get(0),
                     result.getSymbolicVariables().get(0));
    }

    /**
     * Test of getPostConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testGetPostConditionsDescription() {
        System.out.println("getPostConditionsDescription");
        final FormalPropertiesDescription post =
                new FormalPropertiesDescription(POST_TEST);
        final FormalPropertiesDescription pre =
                new FormalPropertiesDescription(PRE_TEST);
        final PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(TEST, pre, post,
                                                    null, null);
        final FormalPropertiesDescription result = instance.getPostConditionsDescription();
        assertEquals(post, result);
    }

    /**
     * Test of getPreConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testGetPreConditionsDescription() {
        System.out.println("getPreConditionsDescription");
        final FormalPropertiesDescription post =
                new FormalPropertiesDescription(POST_TEST);
        final FormalPropertiesDescription pre =
                new FormalPropertiesDescription(PRE_TEST);
        final PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(TEST, pre, post,
                                                    null, null);
        final FormalPropertiesDescription result =
                instance.getPreConditionsDescription();
        assertEquals(pre, result);
    }

    /**
     * Test of setPostConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testSetPostConditionsDescription() {
        System.out.println("setPostConditionsDescription");
        final FormalPropertiesDescription post =
                new FormalPropertiesDescription(POST_TEST);
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription(TEST);
        instance = new PreAndPostConditionsDescription(instance.getName(),
                                                       instance.getPreConditionsDescription(),
                                                       post,
                                                       instance.getBoundedVarDescription(),
                                                       instance.getSymVarList());
        instance.getPostConditionsDescription();
        final FormalPropertiesDescription result =
                instance.getPostConditionsDescription();
        assertEquals(post, result);
    }

    /**
     * Test of setPreConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testSetPreConditionsDescription() {
        System.out.println("setPreConditionsDescription");
        final FormalPropertiesDescription pre =
                new FormalPropertiesDescription(PRE_TEST);
        PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(TEST);
        instance = new PreAndPostConditionsDescription(instance.getName(),
                                                       pre,
                                                       instance.getPostConditionsDescription(),
                                                       instance.getBoundedVarDescription(),
                                                       instance.getSymVarList());
        final FormalPropertiesDescription result = instance.getPreConditionsDescription();
        assertEquals(pre, result);
    }

    /**
     * Test of setNewName method, of class PreAndPostConditionsDescription.
     */
    @Test
    public void testSetNewName() {
        System.out.println("setNewName");
        final PreAndPostConditionsDescription instance =
                new PreAndPostConditionsDescription(TEST);
        instance.setName(TEST_TWO);
        assertEquals(instance.getName(), TEST_TWO);
    }
}
