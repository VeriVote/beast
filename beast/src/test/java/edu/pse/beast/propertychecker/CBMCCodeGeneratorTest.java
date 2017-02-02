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

        InternalTypeContainer intype1 = new InternalTypeContainer(InternalTypeRep.APPROVAL);
        InternalTypeContainer intype2 = new InternalTypeContainer(intype1, InternalTypeRep.CANDIDATE);
        InternalTypeContainer intype3 = new InternalTypeContainer(intype2, InternalTypeRep.VOTER);
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

        String pre = "FOR_ALL_VOTERS(i) : ((i!=u && i!=w) ==> (VOTES1(i) == VOTES2(i)));";

        String post = "ELECT1 == ELECT2;";
        // String post = "1 == 2;";

        FormalPropertiesDescription preDescr = new FormalPropertiesDescription(pre);
        FormalPropertiesDescription postDescr = new FormalPropertiesDescription(post);

        PostAndPrePropertiesDescription postAndPrePropertiesDescription = new PostAndPrePropertiesDescription("name", preDescr, postDescr, symbolicVariableList);

        SymbolicVariableList symVariableList = new SymbolicVariableList();
        symVariableList.addSymbolicVariable("u", new InternalTypeContainer(InternalTypeRep.VOTER));
        symVariableList.addSymbolicVariable("w", new InternalTypeContainer(InternalTypeRep.VOTER));
        // symVariableList.addSymbolicVariable("i", new InternalTypeContainer(InternalTypeRep.VOTER));

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
