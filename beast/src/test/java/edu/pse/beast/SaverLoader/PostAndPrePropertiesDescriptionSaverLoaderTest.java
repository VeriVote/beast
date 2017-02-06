/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.SaverLoader;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Holger-Desktop
 */
public class PostAndPrePropertiesDescriptionSaverLoaderTest {
    
    public PostAndPrePropertiesDescriptionSaverLoaderTest() {
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
     * Test of createSaveString method, of class PostAndPrePropertiesDescriptionSaverLoader.
     */
    @Test
    public void testCreateSaveString() throws Exception {
        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        PostAndPrePropertiesDescription description = new PostAndPrePropertiesDescription("postAndPre", pre, post, list);
        String save = PostAndPrePropertiesDescriptionSaverLoader.createSaveString(description);
        System.out.println(save);
    }

    /**
     * Test of createFromSaveString method, of class PostAndPrePropertiesDescriptionSaverLoader.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        PostAndPrePropertiesDescription description = new PostAndPrePropertiesDescription("postAndPre", pre, post, list);
        String save = PostAndPrePropertiesDescriptionSaverLoader.createSaveString(description);
        PostAndPrePropertiesDescription loaded = PostAndPrePropertiesDescriptionSaverLoader.createFromSaveString(save);
        System.out.println(loaded.getName());
        System.out.println(loaded.getSymbolicVariableList().get(3).getId());
    }
}
