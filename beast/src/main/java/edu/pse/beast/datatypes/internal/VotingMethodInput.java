/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.internal;

/**
 * The following enum is used to describe the Type of VotingMethod.
 * It affects the type of all VOTESx(v). v being a Voter and X being the number of the voting round.
 * It of course also effects the type of VOTESx. x being the number of the voting round.
 * And VOTES being with the VOTESx(v) of every voter v.
 * @author Niels
 */
public enum VotingMethodInput {
    /**
     * Every voter gets to pick one Candidate. So for Every Voter there is one unsigned int from 1 to C
     */
    SINGLECHOICE,
   
    /**
     * For every voter there is an Array of Ints for all Candidates. The candidate in position 0 is the most liked candidate by this voter
     * the candidate in position C is the least liked candidate by this voter
     */
    PREFERENCE,
    
    /**
     * For every voter there is and Array of Boolean (in C int) which has an entry for every Candidate from 1 to C. True (1 (or any number != 0) in C) means that he approves of a Candidate.
     * False (0 in C).
     * Candidate 0 
     */
    APPROVAL,
    /**
     * For every voter there is and Array of int with an entry for every Candidate from 1 to C. The value of the int represents how much the voter likes the candidate. 
     * The upper and lower bound of this can be set by the user. 
     */
    
    WEIGHTEDAPPROVAL,
    
    
    
}
