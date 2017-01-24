
package edu.pse.beast.highlevel;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST.
 * @author Jonas
 */
public class BEASTCommunicator implements CheckListener{
    private final AbstractCentralObjectProvider centralObjectProvider;
    /**
     * Constructor that takes an AbstractCentralObjectProvider that provides access to 
 the other important packages for the BEASTCommunicator.
     * @param centralObjectProvider AbstractCentralObjectProvider
     */
    public BEASTCommunicator(AbstractCentralObjectProvider centralObjectProvider) {
        this.centralObjectProvider = centralObjectProvider;
    }

    @Override
    public void startCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
