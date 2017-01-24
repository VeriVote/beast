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
                internalTypeInitialisation(internalType, id);

            }
        }
    }

    private void internalTypeInitialisation(InternalTypeContainer internalType, String id) {
        switch (internalType.getInternalType()) {
            case VOTER:
                code.add("unsigned int " + id + " = nondet_uint();");
                // a Voter is basically an unsigned int.
                // The number shows which vote from votesX (the Array of all votes) belongs to the voter.
                code.add("assume(0 <= " + id + " && " + id + " < V);");
                // The Voter has to be in the range of possible Voters. V is the total amount of Voters.
                break;
            case CANDIDATE:
                code.add("unsigned int " + id + " = nondet_uint();");
                // a Candidate is basically an unsigned int. Candidate 1 is 1 and so on
                code.add("assume(0 < " + id + " && " + id + " <= C);");
                // C is the number of total Candidates. 0 is NOT a Candidate
                break;
            case SEAT:
                // a Seat is a also an unsigned int. 
                // The return of a votingmethod (an Array) gives you the elected candidate(value) of the seat(id)
                code.add("unsigned int " + id + " = nondet_uint();");
                // there are S seats. From 0 to S-1
                code.add("assume(0 <= " + id + " && " + id + " < S);");
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
