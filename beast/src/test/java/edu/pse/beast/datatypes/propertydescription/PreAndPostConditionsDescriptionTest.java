package edu.pse.beast.datatypes.propertydescription;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 *
 * @author Niels
 */
public class PreAndPostConditionsDescriptionTest {


    /**
     * Test of getName method, of class PreAndPostConditionsDescription.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("name");
        String expResult = "name";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSymbolicVariableList method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testGetSymbolicVariableList() {
        System.out.println("getSymbolicVariableList");
        SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable("test", new InternalTypeContainer(InternalTypeRep.INTEGER));
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("name", null, null, null, symbVarList);
        List<SymbolicVariable> result = instance.getSymbolicVariableList();
        assertEquals(symbVarList.getSymbolicVariables().get(0), result.get(0));
    }

    /**
     * Test of getSymVarList method, of class PreAndPostConditionsDescription.
     */
    @Test
    public void testGetSymVarList() {
        System.out.println("getSymVarList");
        SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable("test", new InternalTypeContainer(InternalTypeRep.INTEGER));
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("name", null, null, null, symbVarList);
        SymbolicVariableList result = instance.getSymVarList();
        assertEquals(symbVarList.getSymbolicVariables().get(0), result.getSymbolicVariables().get(0));
    }

    /**
     * Test of setSymbolicVariableList method, of class
     * PreAndPostConditionsDescription.
     */
    @Ignore("Test needs to be updated after changes.")
    @Test
    public void testSetSymbolicVariableList() {
        System.out.println("setSymbolicVariableList");
        SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable("test", new InternalTypeContainer(InternalTypeRep.INTEGER));
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("name");
       // instance.setSymbolicVariableList(symbVarList);
        SymbolicVariableList result = instance.getSymVarList();
        assertEquals(symbVarList.getSymbolicVariables().get(0), result.getSymbolicVariables().get(0));
    }

    /**
     * Test of getPostConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testGetPostConditionsDescription() {
        System.out.println("getPostConditionsDescription");
        FormalPropertiesDescription post = new FormalPropertiesDescription("postTest");
        FormalPropertiesDescription pre = new FormalPropertiesDescription("preTest");
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("test", pre, post, null, null);
        FormalPropertiesDescription result = instance.getPostConditionsDescription();
        assertEquals(post, result);
    }

    /**
     * Test of getPreConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Test
    public void testGetPreConditionsDescription() {
        System.out.println("getPreConditionsDescription");
        FormalPropertiesDescription post = new FormalPropertiesDescription("postTest");
        FormalPropertiesDescription pre = new FormalPropertiesDescription("preTest");
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("test", pre, post, null, null);
        FormalPropertiesDescription result = instance.getPreConditionsDescription();
        assertEquals(pre, result);
    }

    /**
     * Test of setPostConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Ignore("Test needs to be updated after changes.")
    @Test
    public void testSetPostConditionsDescription() {
        System.out.println("setPostConditionsDescription");
        FormalPropertiesDescription post = new FormalPropertiesDescription("postTest");
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("test");
        //instance.setPostConditionsDescription(post);
        FormalPropertiesDescription result = instance.getPostConditionsDescription();
        assertEquals(post, result);
    }

    /**
     * Test of setPreConditionsDescription method, of class
     * PreAndPostConditionsDescription.
     */
    @Ignore("Test needs to be updated after changes.")
    @Test
    public void testSetPreConditionsDescription() {
        System.out.println("setPreConditionsDescription");
        FormalPropertiesDescription pre = new FormalPropertiesDescription("preTest");
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("test");
        // instance.setPreConditionsDescription(pre);
        FormalPropertiesDescription result = instance.getPreConditionsDescription();
        assertEquals(pre, result);
    }

    /**
     * Test of setNewName method, of class PreAndPostConditionsDescription.
     */
    @Ignore("Test needs to be updated after changes.")
    @Test
    public void testSetNewName() {
        System.out.println("setNewName");
        PreAndPostConditionsDescription instance = new PreAndPostConditionsDescription("test");
        //instance.setNewName("test2");
        assert (instance.getName().equals("test2"));
    }
}