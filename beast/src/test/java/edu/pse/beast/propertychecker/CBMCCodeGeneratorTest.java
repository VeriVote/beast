/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * All of these tests are very simple tests if the output contains Strings that
 * should be there. It divides the generated code by the comments that divide the
 * different parts of the CodeGeneration. Hence if a test fails, first check if you
 * didn't change a String that separated the different parts of the Code before.
 *
 * @author Niels
 *
 */
public class CBMCCodeGeneratorTest {

    private CBMCCodeGenerator instance;
    // you can change any of the following attributes.
    // just use initialize() to set up instance to use them.
    private ElectionDescription electionDescription;
    private final SymbolicVariableList symVariableList;
    private String pre;
    private String post;
    private ElectionTypeContainer inputType;
    private ElectionTypeContainer outputType;
    private final ArrayList<String> userCode;

    /**
     * this initializes the Attributes with easy values, that can be generated
     */
    public CBMCCodeGeneratorTest() {
        inputType = new ElectionTemplateHandler().getById(ElectionTypeContainer.ElectionInputTypeIds.SINGLE_CHOICE);
        outputType = new ElectionTemplateHandler().getById(ElectionTypeContainer.ElectionOutputTypeIds.CAND_OR_UNDEF);
        int votingDeclLine = 0;
        electionDescription = new ElectionDescription("name", inputType, outputType, votingDeclLine);
        userCode = new ArrayList<>();
        userCode.add("votingcode");
        userCode.add("abalsdf");
        electionDescription.setCode(userCode);

        symVariableList = new SymbolicVariableList();

        pre = "FOR_ALL_VOTERS(v) : VOTES1(v) == VOTES2(v);";
        post = "ELECT1 != ELECT2;";
        this.initialize();
    }

    /**
     * tests if the user code is in the output
     */
    @Test
    public void testUserCode() {
        System.out.println("testUserCode");
        userCode.clear();
        String test1 = "dfsgsdfgV]) { ";
        String test2 = "return 0; }";
        userCode.add(test1);
        userCode.add(test2);
        String userCodeComment = "//Code of the user";
        this.initialize();
        assertTrue(outputContains(test1, userCodeComment));
        assertTrue(outputContains(test2, userCodeComment));
    }

    /**
     * tests if the PreCondition Description contains the right operands
     */
    @Test
    public void testPreCondition() {
        System.out.println("testPreCondition");

        String preComment = "//preconditions";
        pre = "VOTES1 == VOTES2 && ELECT1 == ELECT2";
        initialize();
        assertTrue(outputContains("&&", preComment));

        pre = "VOTES1 == VOTES2 || ELECT1 == ELECT2";
        initialize();
        assertTrue(outputContains("||", preComment));

        pre = "VOTES1 < VOTES2;";
        initialize();
        assertTrue(outputContains("<", preComment));

        pre = "VOTES1 > VOTES2;";
        initialize();
        assertTrue(outputContains("<", preComment));

        pre = "VOTES1 == VOTES2;";
        initialize();
        assertTrue(outputContains("==", preComment));

        pre = "VOTES1 != VOTES2;";
        initialize();
        assertTrue(outputContains("!=", preComment));

        pre = "VOTES1 <= VOTES2;";
        initialize();
        assertTrue(outputContains("<=", preComment));

        pre = "VOTES1 >= VOTES2;";
        initialize();
        assertTrue(outputContains(">=", preComment));

    }

    /**
     * tests if the PostCondition Description contains the right operands
     */
    @Test
    public void testPostCondition() {
        System.out.println("testPreCondition");
        String postComment = "//postconditions";

        post = "VOTES1 == VOTES2 && ELECT1 == ELECT2";
        initialize();
        assertTrue(outputContains("&&", postComment));

        post = "VOTES1 == VOTES2 || ELECT1 == ELECT2";
        initialize();
        assertTrue(outputContains("||", postComment));

        post = "VOTES1 < VOTES2;";
        initialize();
        assertTrue(outputContains("<", postComment));

        post = "VOTES1 > VOTES2;";
        initialize();
        assertTrue(outputContains("<", postComment));

        post = "VOTES1 == VOTES2;";
        initialize();
        assertTrue(outputContains("==", postComment));

        post = "VOTES1 != VOTES2;";
        initialize();
        assertTrue(outputContains("!=", postComment));

        post = "VOTES1 <= VOTES2;";
        initialize();
        assertTrue(outputContains("<=", postComment));

        post = "VOTES1 >= VOTES2;";
        initialize();
        assertTrue(outputContains(">=", postComment));
    }

    /**
     *
     */
    @Test
    public void testSymbolicVariableList() {
        int max = symVariableList.getSymbolicVariables().size();
        for (int i = max - 1; i > 0; i--) {
            symVariableList.removeSymbolicVariable(i);
        }
        symVariableList.addSymbolicVariable("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        symVariableList.addSymbolicVariable("v", new InternalTypeContainer(InternalTypeRep.VOTER));
        symVariableList.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        symVariableList.addSymbolicVariable("i", new InternalTypeContainer(InternalTypeRep.INTEGER));
        initialize();

        String symbVarComment = "//Symbolic Variables initialisation";
        String test1 = "unsigned int v = nondet_uint();";
        String test2 = "unsigned int c = nondet_uint();";
        String test3 = "unsigned int s = nondet_uint();";
        String test4 = "unsigned int i = nondet_uint();";

        assertTrue(outputContains(test1, symbVarComment));
        assertTrue(outputContains(test2, symbVarComment));
        assertTrue(outputContains(test3, symbVarComment));
        assertTrue(outputContains(test4, symbVarComment));
    }

    private void initialize() {
        FormalPropertiesDescription preDescr = new FormalPropertiesDescription(pre);
        FormalPropertiesDescription postDescr = new FormalPropertiesDescription(post);

        electionDescription = new ElectionDescription("name", inputType, outputType, 0);
        electionDescription.setCode(userCode);

        PreAndPostConditionsDescription preAndPostConditionsDescription
                = new PreAndPostConditionsDescription("name", preDescr, postDescr, symVariableList);

        instance = new CBMCCodeGenerator(electionDescription, preAndPostConditionsDescription);

    }

    /**
     * this method checks if a string is after a specific comment and before the
     * next use of "//"
     *
     * @param wantedOutput the output you want in a part of the generated code
     * @param commentToDistinguishPart the comment which starts the codePart
     * @return if the wantedOutput is in the codePart
     */
    private boolean outputContains(String wantedOutput, String commentToDistinguishPart) {
        boolean contains = false;
        boolean insideWantedPart = false;

        for (String line : instance.getCode()) {
            if (!insideWantedPart) {
                insideWantedPart = line.contains(commentToDistinguishPart);
            } else {
                if (line.contains("//")) {
                    break;
                }
                contains = line.contains(wantedOutput);

                //System.out.println(line);
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }
}
