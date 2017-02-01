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
import java.util.List;

/**
 *
 * @author Niels
 */
public class CBMCCodeGeneratorTest {

    public static void main(String args[]) {
        InternalTypeContainer type = new InternalTypeContainer(InternalTypeRep.APPROVAL);
        ElectionTypeContainer inputType = new ElectionTypeContainer(type, "input");
        InternalTypeContainer type2 = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        type = new InternalTypeContainer(type2, InternalTypeRep.CANDIDATE);
        ElectionTypeContainer outputType = new ElectionTypeContainer(type, "output");

        ElectionDescription electionDescription = new ElectionDescription("name", inputType, outputType, 0);
        ArrayList<String> userCode = new ArrayList<>();
        userCode.add("votingcode");
        userCode.add("abalsdf");
        electionDescription.setCode(userCode);

        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();

        //String pre = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : (c == VOTES2(v) && (VOTE_SUM_FOR_CANDIDATE(c)>= 3 ==> c < 2));";
        String pre = "2 == 3;";
        String post = "1 == 4;";

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
}
