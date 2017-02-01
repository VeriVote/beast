/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.internal;

/**
 * The following Enum is used to describe the Returnvalue of the votingmethod
 * It affects the type of all ELECTx. x being the number of the voting round. 
 * 
 * @author Niels
 */
public enum VotingMethodOutput {
    /**
     * //The output is a sole winner Candidate. So it is a Number between 0 and C. 0 meaning a tie.
     */
    CANDIDATE, 
    /**
     * The output is an Array of Seats with a depth of 0 to S-1. The values of those seats is the Candidate, that got them. 
     * 0 for no Candidate.  And 1 to C for a the Candidate or Party that holds the SEATs.
     */
    PARLAMENT
   
}
