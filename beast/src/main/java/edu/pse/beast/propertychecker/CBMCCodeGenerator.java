/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import java.util.ArrayList;
import java.util.LinkedList;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;

/**
 *
 * @author Niels
 */
public class CBMCCodeGenerator {

    private ArrayList<String> code;
    private final ElectionDescription electionDescription;
    private final PostAndPrePropertiesDescription postAndPrePropertiesDescription;

    public CBMCCodeGenerator(ElectionDescription electionDescription, PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        this.electionDescription = electionDescription;
        this.postAndPrePropertiesDescription = postAndPrePropertiesDescription;
        code = new ArrayList<>();
        initializeCode();
    }

    public ArrayList<String> generateCode() {

        return code;
    }

    private void initializeCode() {
        addIncludes();
        code.addAll(electionDescription.getCode());
        addMainMethod();
    }

    private void addIncludes() {
        code.add("#inlcude <stdlib.h>");
        code.add("#inlcude <stdint.h>");
        code.add("#inlcude <assert.h>");
        code.add("");
        code.add("int nondet_uint();");
        code.add("int nondet_int();");
        code.add("");
        code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
        code.add("#define assume(x) __CPROVER_assume(x)");
        code.add("");
    }

    private void addMainMethod() {
        LinkedList<SymbolicVariable> symbolicVariableList = postAndPrePropertiesDescription.getSymbolicVariableList();
        for (SymbolicVariable symbVar : symbolicVariableList) {
            InternalTypeContainer internalType = symbVar.getInternalTypeContainer();
            String id = symbVar.getId();
            if (internalType.isList()) {

            } else {
                switch (internalType.getInternalType()) {
                    case VOTER: //fallthrough
                    case CANDIDATE:
                        code.add("unsigned int " + id + " = nondet_uint();");
                        break;

                    case SEAT:
                        break;
                    case APPROVAL:
                        break;
                    case WEIGHTEDAPPROVAL:
                        break;
                    case INTEGER:
                        code.add("int " + id + " = nondet_int();");
                        break;
                    default:
                        throw new AssertionError(internalType.getInternalType().name());

                }
            }
        }
    }
}
