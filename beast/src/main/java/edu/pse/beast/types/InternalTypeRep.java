package edu.pse.beast.types;

/**
 * Represents the InternalType of the Variables
 *
 * @author Niels Hanselmann
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
