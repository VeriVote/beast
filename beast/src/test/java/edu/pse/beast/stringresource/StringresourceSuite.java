/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Niels
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({edu.pse.beast.stringresource.ParameterEditorStringResProviderTest.class,
    edu.pse.beast.stringresource.StringResourceProviderTest.class,
    edu.pse.beast.stringresource.BooleanExpEditorStringResProviderTest.class,
    edu.pse.beast.stringresource.OptionStringResProviderTest.class,
    edu.pse.beast.stringresource.CElectionEditorStringResProviderTest.class,
    edu.pse.beast.stringresource.StringLoaderInterfaceTest.class,
    edu.pse.beast.stringresource.StringResourceLoaderTest.class,
    edu.pse.beast.stringresource.PropertyListStringResProviderTest.class})

public class StringresourceSuite {

    /**
     * 
     * @throws Exception 
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    /**
     * 
     * @throws Exception 
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    /**
     * 
     * @throws Exception 
     */
    @Before
    public void setUp() throws Exception {
    }
    /**
     * 
     * @throws Exception 
     */
    @After
    public void tearDown() throws Exception {
    }

}
