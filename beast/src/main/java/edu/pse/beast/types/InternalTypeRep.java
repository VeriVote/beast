/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.types;

/**
 * Represents the InternalType of the Variables
 *
 * @author Niels
 */
public enum InternalTypeRep {
    /**
     * VoterType
     */
    VOTER,
    /**
     * CandidateType
     */
    CANDIDATE,
    /**
     * SeatType
     */
    SEAT,
    /**
     * ApprovalType
     */
    APPROVAL,
    /**
     * WeightedApprovalType
     */
    WEIGHTEDAPPROVAL,
    /**
     * IntergerType
     */
    INTEGER,
    /**
     * NullType
     */
    NULL,
    /**
     * A tupel with at least 2 voting arrays in it
     */
    TUPEL
}
