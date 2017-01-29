/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import java.util.Arrays;
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
public class CBMCCodeGenerator_HolgerTest {
    
    public CBMCCodeGenerator_HolgerTest() {
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
     * Test of generateCode method, of class CBMCCodeGenerator_Holger.
     */
    @Test
    public void testGenerateCode() {
        String pre = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : (c == VOTES2(v) && (VOTE_SUM_FOR_CANDIDATE(c)>= 3 ==> c < 2));";
        String post = "VOTES2 == VOTES1;";
        
        PostAndPrePropertiesDescription descr = new PostAndPrePropertiesDescription(
                "test1",
                new FormalPropertiesDescription(pre),
                new FormalPropertiesDescription(post));
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("v", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        
        descr.setSymbolicVariableList(list);
        
        InternalTypeContainer input = new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
        InternalTypeContainer res = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        
        ElectionDescription electionDescr = new ElectionDescription(
                "descr",
                new ElectionTypeContainer(input, ""), 
                new ElectionTypeContainer(res, ""), 0);
        
        /**
         * candidates Array has to have the lentgh C+1 because Candidate 0 indicates that there are no winners.
         */
        String code = "unsigned int voting(unsigned int voters[V], unsigned int candidates[C+1], unsigned int seats[S]) {\n"+
                "return 0;\n" +
                "}\n";
        
        electionDescr.setCode(Arrays.asList(code.split("\n")));

        String generated = new CBMCCodeGenerator_Holger().generateCode(descr, electionDescr);
        System.out.println("");
        System.out.println("");
        
        System.out.println(generated);
    }
    
}
