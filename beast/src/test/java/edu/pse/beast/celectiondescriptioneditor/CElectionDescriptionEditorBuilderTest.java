/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import toBeImplemented.LanguageOptions;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionDescriptionEditorBuilderTest {
    
    public CElectionDescriptionEditorBuilderTest() {
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

    public static void main(String[] args) {    
        OptionsInterface opt = new OptionsInterface();
        ObjectRefsForBuilder refs = new ObjectRefsForBuilder(
                opt, new StringLoaderInterface("de"), 
                new LanguageOptions(), new SaverLoaderInterface());
        CElectionDescriptionEditorBuilder builder = new CElectionDescriptionEditorBuilder();
        builder.createCElectionDescriptionEditor(refs);
    }
    
}
