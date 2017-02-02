/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.toolbox.CCodeHelper;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class CBMCCodeGeneratorTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public static void main(String args[]) {
        
        InternalTypeContainer intype1 = new InternalTypeContainer (InternalTypeRep.APPROVAL);
        InternalTypeContainer intype2 = new InternalTypeContainer (intype1, InternalTypeRep.CANDIDATE);
        InternalTypeContainer intype3 = new InternalTypeContainer (intype2, InternalTypeRep.VOTER);
        ElectionTypeContainer inputType = new ElectionTypeContainer(intype3, "input");
        InternalTypeContainer type2 = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        InternalTypeContainer outtype = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        ElectionTypeContainer outputType = new ElectionTypeContainer(outtype, "output");

        ElectionDescription electionDescription = new ElectionDescription("name", inputType, outputType, 0);
        ArrayList<String> userCode = new ArrayList<>();
        userCode.add("votingcode");
        userCode.add("abalsdf");
        electionDescription.setCode(userCode);

        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();

        // String pre = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : (c == VOTES2(v) && (VOTE_SUM_FOR_CANDIDATE(c)>= 3 ==> c < 2));";
        String pre = "VOTES1 == VOTES2;";
        String post = "";

        FormalPropertiesDescription preDescr = new FormalPropertiesDescription(pre);
        FormalPropertiesDescription postDescr = new FormalPropertiesDescription(post);

        PostAndPrePropertiesDescription postAndPrePropertiesDescription = new PostAndPrePropertiesDescription("name", preDescr, postDescr, symbolicVariableList);

        SymbolicVariableList symVariableList = new SymbolicVariableList();
        symVariableList.addSymbolicVariable("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        symVariableList.addSymbolicVariable("a", new InternalTypeContainer(InternalTypeRep.VOTER));
        symVariableList.addSymbolicVariable("v", new InternalTypeContainer(InternalTypeRep.VOTER));

        postAndPrePropertiesDescription.setSymbolicVariableList(symVariableList);

        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescription, postAndPrePropertiesDescription);

        System.out.println();
        System.out.println("-----------------hier beginnt der generierte Code-------------------");
        System.out.println();
        ArrayList<String> code;
        code = generator.getCode();
        code.forEach((n) -> {
            System.out.println(n);
        });
    }

    /**
     * Test of getCode method, of class CBMCCodeGenerator.
     */
    @Test
    public void testGetCode() {
    }
    
    public String generateVoteArray(ElectionTypeContainer inputElectionType) {
        //generate vote arrays
        CCodeHelper cCodeHelper = new CCodeHelper();
        String[] counter = {"i", "j", "k", "l"};
        String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < MAX; ++COUNTER){\n";
        String voteArr = "";
        InternalTypeContainer cont = inputElectionType.getType();
        int listDepth = 0;
        while(cont.isList()) {
            String currentFor = forTemplate.replaceAll("COUNTER", counter[listDepth]);
            currentFor = currentFor.replaceAll("MAX", cCodeHelper.getListSize(cont));
            voteArr += currentFor;
            cont = cont.getListedType();
            listDepth++;
        }
        
        String min = cCodeHelper.getMin(inputElectionType, cont.getInternalType());
        String max = cCodeHelper.getMax(inputElectionType, cont.getInternalType());
        
        String voteDecl =  "assume(MIN <= votes1".replace("MIN", min);
        
        for(int i = 0; i < listDepth; ++i) {
            voteDecl += "[COUNTER]".replace("COUNTER", counter[i]);            
        }
        
        voteDecl += "<= MAX);\n".replace("MAX", max);
        voteArr += voteDecl;
        
        for(int i = 0; i < listDepth; ++i) {
            voteArr += "}\n";        
        }
        
        return voteArr;
    }

    /**
     * Test of generateVoteArray method, of class CBMCCodeGenerator.
     */
    @Test
    public void testGenerateVoteArray() {
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
        ElectionTypeContainer input = electionTemplateHandler.getById("list_of_yes_no_per_voter");
        String voteDecl = generateVoteArray(input);
        System.out.println(voteDecl);
        input = electionTemplateHandler.getById("one_candidate_per_voter");
        voteDecl = generateVoteArray(input);
        System.out.println(voteDecl);
        input = electionTemplateHandler.getById("list_of_candidates_per_voter");
        voteDecl = generateVoteArray(input);
        System.out.println(voteDecl);
        input = electionTemplateHandler.getById("list_of_integer_vals_per_voter");
        input.setLowerBound(0);
        input.setUpperBound(100);
        voteDecl = generateVoteArray(input);
        System.out.println(voteDecl);
    }
}
