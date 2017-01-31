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
import java.util.ArrayList;


/**
 *
 * @author Niels
 */
public class CBMCCodeGeneratorTest {
    
    public static void main(String args[]) {
        InternalTypeContainer type = new InternalTypeContainer(InternalTypeRep.WEIGHTEDAPPROVAL);
        ElectionTypeContainer inputType = new ElectionTypeContainer(type, "input");
        type = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        ElectionTypeContainer outputType = new ElectionTypeContainer(type, "output");

        ElectionDescription electionDescription = new ElectionDescription("", inputType, outputType, 0);

        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();

        String pre = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : (c == VOTES2(v) && (VOTE_SUM_FOR_CANDIDATE(c)>= 3 ==> c < 2));";
        String post = "VOTES2 == VOTES1;";
        
        FormalPropertiesDescription preDescr = new FormalPropertiesDescription(pre);
        FormalPropertiesDescription postDescr = new FormalPropertiesDescription(post);

        PostAndPrePropertiesDescription postAndPrePropertiesDescription = new PostAndPrePropertiesDescription("name", preDescr, postDescr, symbolicVariableList);

        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescription, postAndPrePropertiesDescription);

        ArrayList<String> code;
        code = generator.getCode();
        for (String n : code) {
            System.out.println(n);
        }
    }
}
