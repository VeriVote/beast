
package edu.pse.beast.highlevel;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST.
 * @author Jonas
 */
public class BEASTCommunicator implements CheckListener{
    private final CentralObjectProvider centralObjectProvider;
    /**
     * Constructor that takes an CentralObjectProvider that provides access to 
 the other important packages for the BEASTCommunicator.
     * @param centralObjectProvider CentralObjectProvider
     */
    public BEASTCommunicator(CentralObjectProvider centralObjectProvider) {
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
