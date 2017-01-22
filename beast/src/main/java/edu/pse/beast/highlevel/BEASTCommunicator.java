
package edu.pse.beast.highlevel;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST.
 * @author Jonas
 */
public class BEASTCommunicator {
    private AbstractBeastFactory factory;
    /**
     * Constructor that takes an AbstractBeastFactory that provides access to 
     * the other important packages for the BEASTCommunicator.
     * @param fac AbstractBeastFactory
     */
    public BEASTCommunicator(AbstractBeastFactory fac) {
        factory = fac;
    }
    /**
     * Starts all actions that have to take place before checking the election
     * for the properties.
     */
    protected void beforeChecking() {
        
    }
    /**
     * Starts all actions that have to take place after checking the election
     * for the properties.
     */
    protected void afterChecking() {
        
    }
}
