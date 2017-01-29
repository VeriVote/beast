/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.BooleanExpEditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.stringresource.StringResourceProvider;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScopehandler;
import java.util.ArrayList;
import javax.swing.JTextPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpEditorVariableErrorFinderTest {
    private BooleanExpANTLRHandler aNTLRHandler;
    private BooleanExpEditorVariableErrorFinder finder;
    private JTextPane pane;
    private StringResourceLoader stringRes;
    public BooleanExpEditorVariableErrorFinderTest() {
        pane = new JTextPane();
        aNTLRHandler = new BooleanExpANTLRHandler(pane.getStyledDocument());
        finder = new BooleanExpEditorVariableErrorFinder(aNTLRHandler);
        stringRes = new StringLoaderInterface("de").getBooleanExpEditorStringResProvider().getBooleanExpErrorStringRes();
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
     * Test of getErrors method, of class BooleanExpEditorVariableErrorFinder.
     */
    @Test
    public void testGetErrors() {
        ElectionTypeContainer input = new ElectionTemplateHandler().getById("list_of_candidates_per_voter");
        ElectionTypeContainer res = new ElectionTemplateHandler().getById("one_candidate_or_zero");
        
        BooleanExpScopehandler scopehandler = new BooleanExpScopehandler();
        scopehandler.enterNewScope();
        scopehandler.addVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        
        String exp = "FOR_ALL_VOTERS(v) : FOR_ALL_CANDIDATES(c) : (VOTES1(v)(c) == 0 ==> c == cand)";
        
        pane.setText(exp);
        
        finder.setUp(scopehandler, input, res);
        assertEquals(0, finder.getErrors().size());
        
        exp = "FOR_ALL_VOTERS(v) : FOR_ALL_CANDIDATES(c) : (VOTES1(v)(d) == 0 ==> d == cand)";
        pane.setText(exp);
        assertEquals(2, finder.getErrors().size());
        
        for(CodeError e : finder.getErrors()) {
            System.err.println(stringRes.getStringFromID(e.getMsg()));
        }
    }

   
    
}
