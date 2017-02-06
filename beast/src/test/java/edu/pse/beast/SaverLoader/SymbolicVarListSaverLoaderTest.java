/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.SaverLoader;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.SymbolicVarListSaverLoader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Holger-Desktop
 */
public class SymbolicVarListSaverLoaderTest {
    
    public SymbolicVarListSaverLoaderTest() {
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
     * Test of createSaveString method, of class SymbolicVarListSaverLoader.
     */
    @Test
    public void testCreateSaveString() {
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        System.out.println(SymbolicVarListSaverLoader.createSaveString(list));
    }

    /**
     * Test of createFromSaveString method, of class SymbolicVarListSaverLoader.
     */
    @Test
    public void testCreateFromSaveString() {
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        String s = SymbolicVarListSaverLoader.createSaveString(list);
        SymbolicVariableList l = SymbolicVarListSaverLoader.createFromSaveString(s);
        for(SymbolicVariable var : l.getSymbolicVariables()) {
            System.out.println(var.getId() + " " + var.getInternalTypeContainer().toString());
        }
    }
    
}
