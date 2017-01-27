/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp;

import edu.pse.beast.datatypes.boolexp.BooleanExpListNode;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
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
public class FormalPropertySyntaxTreeToAstTranslaterTest {
    
    public FormalPropertySyntaxTreeToAstTranslaterTest() {
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
    
    @Test
    public void testCreateASTComparison() {
        String exp = "ELECT1(c) == ELECT1(c)";
        FormalPropertyDescriptionLexer lexer = new FormalPropertyDescriptionLexer(new ANTLRInputStream(exp));
        CommonTokenStream tokenS = new CommonTokenStream(lexer);
        FormalPropertyDescriptionParser parser = new FormalPropertyDescriptionParser(tokenS);
        
        FormalPropertySyntaxTreeToAstTranslater translater = new FormalPropertySyntaxTreeToAstTranslater();
        InternalTypeContainer inputType = new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
        InternalTypeContainer output = new InternalTypeContainer(InternalTypeRep.CANDIDATE);       
        translater.generateFromSyntaxTree(parser.booleanExpList(), inputType, output);
    }

    @Test
    public void testCreateAST() {
       String exp = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : VOTES1(v) == c && VOTES1(v) == c;";
       FormalPropertyDescriptionLexer lexer = new FormalPropertyDescriptionLexer(new ANTLRInputStream(exp));
       CommonTokenStream tokenS = new CommonTokenStream(lexer);
       FormalPropertyDescriptionParser parser = new FormalPropertyDescriptionParser(tokenS);
       FormalPropertySyntaxTreeToAstTranslater translater = new FormalPropertySyntaxTreeToAstTranslater();
       //BooleanExpListNode ast = translater.generateFromSyntaxTree(parser.booleanExpList());
    }
    
}
