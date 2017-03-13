/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class PostAndPrePropertiesDescriptionTest {


    /**
     * Test of getName method, of class PostAndPrePropertiesDescription.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("name");
        String expResult = "name";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSymbolicVariableList method, of class
     * PostAndPrePropertiesDescription.
     */
    @Test
    public void testGetSymbolicVariableList() {
        System.out.println("getSymbolicVariableList");
        SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable("test", new InternalTypeContainer(InternalTypeRep.INTEGER));
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("name", null, null, symbVarList);
        List<SymbolicVariable> result = instance.getSymbolicVariableList();
        assertEquals(symbVarList.getSymbolicVariables().getFirst(), result.get(0));
    }

    /**
     * Test of getSymVarList method, of class PostAndPrePropertiesDescription.
     */
    @Test
    public void testGetSymVarList() {
        System.out.println("getSymVarList");
        SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable("test", new InternalTypeContainer(InternalTypeRep.INTEGER));
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("name", null, null, symbVarList);
        SymbolicVariableList result = instance.getSymVarList();
        assertEquals(symbVarList.getSymbolicVariables().getFirst(), result.getSymbolicVariables().get(0));
    }

    /**
     * Test of setSymbolicVariableList method, of class
     * PostAndPrePropertiesDescription.
     */
    @Test
    public void testSetSymbolicVariableList() {
        System.out.println("setSymbolicVariableList");
        SymbolicVariableList symbVarList = new SymbolicVariableList();
        symbVarList.addSymbolicVariable("test", new InternalTypeContainer(InternalTypeRep.INTEGER));
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("name");
        instance.setSymbolicVariableList(symbVarList);
        SymbolicVariableList result = instance.getSymVarList();
        assertEquals(symbVarList.getSymbolicVariables().getFirst(), result.getSymbolicVariables().get(0));
    }

    /**
     * Test of getPostPropertiesDescription method, of class
     * PostAndPrePropertiesDescription.
     */
    @Test
    public void testGetPostPropertiesDescription() {
        System.out.println("getPostPropertiesDescription");
        FormalPropertiesDescription post = new FormalPropertiesDescription("postTest");
        FormalPropertiesDescription pre = new FormalPropertiesDescription("preTest");
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("test", pre, post, null);
        FormalPropertiesDescription result = instance.getPostPropertiesDescription();
        assertEquals(post, result);
    }

    /**
     * Test of getPrePropertiesDescription method, of class
     * PostAndPrePropertiesDescription.
     */
    @Test
    public void testGetPrePropertiesDescription() {
        System.out.println("getPrePropertiesDescription");
        FormalPropertiesDescription post = new FormalPropertiesDescription("postTest");
        FormalPropertiesDescription pre = new FormalPropertiesDescription("preTest");
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("test", pre, post, null);
        FormalPropertiesDescription result = instance.getPrePropertiesDescription();
        assertEquals(pre, result);
    }

    /**
     * Test of setPostPropertiesDescription method, of class
     * PostAndPrePropertiesDescription.
     */
    @Test
    public void testSetPostPropertiesDescription() {
        System.out.println("setPostPropertiesDescription");
        FormalPropertiesDescription post = new FormalPropertiesDescription("postTest");
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("test");
        instance.setPostPropertiesDescription(post);
        FormalPropertiesDescription result = instance.getPostPropertiesDescription();
        assertEquals(post, result);
    }

    /**
     * Test of setPrePropertiesDescription method, of class
     * PostAndPrePropertiesDescription.
     */
    @Test
    public void testSetPrePropertiesDescription() {
        System.out.println("setPrePropertiesDescription");
        FormalPropertiesDescription pre = new FormalPropertiesDescription("preTest");
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("test");
        instance.setPrePropertiesDescription(pre);
        FormalPropertiesDescription result = instance.getPrePropertiesDescription();
        assertEquals(pre, result);
    }

    /**
     * Test of setNewName method, of class PostAndPrePropertiesDescription.
     */
    @Test
    public void testSetNewName() {
        System.out.println("setNewName");
        String newName = "";
        PostAndPrePropertiesDescription instance = new PostAndPrePropertiesDescription("test");
        instance.setNewName("test2");
        assert (instance.getName().equals("test2"));
    }

}
