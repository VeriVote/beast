/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription.electiondescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;

/**
 * Datatype for the Input and Output of an Election
 * @author Niels
 */
public class ElectionTypeContainer {

    private final InternalTypeContainer internalType;
    private int lowerBound;
    private int upperBound;

    /**
     * 
     * @param internalType 
     */
    public ElectionTypeContainer(InternalTypeContainer internalType) {
        this.internalType = internalType;
    }
    /**
     * 
     * @return internalType
     */
    public InternalTypeContainer getInternalType() {
        return internalType;
    }
    /**
     * can be null!
     * @return lower Bound of voting method
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * can be null!
     * @return upper Bound of voting method
     */
    public int getUpperBound() {
        return upperBound;
    }

    /**
     * 
     * @param lowerBound lower bound
     */
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     *
     * @param upperBound upper bound
     */
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
