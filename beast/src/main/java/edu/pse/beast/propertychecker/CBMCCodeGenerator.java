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

    public static ArrayList<String> generateCode(ElectionDescription electionDescription, PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        ArrayList<String> code = new ArrayList<>();
        code = addIncludes(code);
        code.addAll(electionDescription.getCode());
        code.addAll(addMainMethod(code, postAndPrePropertiesDescription));

        return code;
    }

    private static ArrayList<String> addIncludes(ArrayList<String> code) {
        code.add("#inlcude <stdlib.h>");
        code.add("#inlcude <stdint.h>");
        code.add("#inlcude <assert.h>");
        code.add("");
        code.add("int nondet_uint();");
        code.add("");
        code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
        code.add("#define assume(x) __CPROVER_assume(x)");
        code.add("");
        return code;
    }

    private static ArrayList<String> addMainMethod(ArrayList<String> code, PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        LinkedList<SymbolicVariable> symbolicVariableList = postAndPrePropertiesDescription.getSymbolicVariableList();
        for (SymbolicVariable symbVar : symbolicVariableList) {
            InternalTypeContainer internalType = symbVar.getInternalTypeContainer();
            String id = symbVar.getId();
            if (internalType.isList()) {

            } else {
                switch (internalType.getInternalType()) {
                    case VOTER:
                        
                        break;
                    case CANDIDATE:
                        break;
                    case SEAT:
                        break;
                    case APPROVAL:
                        break;
                    case WEIGHTEDAPPROVAL:
                        break;
                    case INTEGER:
                        code.add("int " + id + ";");
                        break;
                    default:
                        throw new AssertionError(internalType.getInternalType().name());

                }
            }
        }

        return code;
    }
}
