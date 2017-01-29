/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp;

import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslator;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
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
    
    private FormalPropertyDescriptionParser.BooleanExpListContext createFromString(String s) {
        FormalPropertyDescriptionLexer lexer = new FormalPropertyDescriptionLexer(new ANTLRInputStream(s));
        CommonTokenStream tokenS = new CommonTokenStream(lexer);
        FormalPropertyDescriptionParser parser = new FormalPropertyDescriptionParser(tokenS);
        return parser.booleanExpList();
    }
    
    @Test
    public void testCreateASTComparison() {
        FormalPropertySyntaxTreeToAstTranslator translater = new FormalPropertySyntaxTreeToAstTranslator();
        InternalTypeContainer inputType = new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
        InternalTypeContainer output = new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.CANDIDATE);  
        
        BooleanExpScope declaredVar = new BooleanExpScope();
        declaredVar.addTypeForId("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        declaredVar.addTypeForId("v", new InternalTypeContainer(InternalTypeRep.VOTER));        
        
//        String exp = "ELECT2(c) == VOTES2(v);";      
//        BooleanExpListNode created = translater.generateFromSyntaxTree(createFromString(exp), inputType, output, declaredVar);
      
        declaredVar = new BooleanExpScope();
        String exp = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : (c == VOTES2(v) && (VOTE_SUM_FOR_CANDIDATE(c)>= 3 ==> c < 2));";    
        
    }

    @Test
    public void testCreateAST() {
       String exp = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : VOTES1(v) == c && VOTES1(v) == c;";
       FormalPropertyDescriptionLexer lexer = new FormalPropertyDescriptionLexer(new ANTLRInputStream(exp));
       CommonTokenStream tokenS = new CommonTokenStream(lexer);
       FormalPropertyDescriptionParser parser = new FormalPropertyDescriptionParser(tokenS);
       FormalPropertySyntaxTreeToAstTranslator translater = new FormalPropertySyntaxTreeToAstTranslator();
    }
    
}
