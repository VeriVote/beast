package edu.pse.beast.highlevel;

/**
 * The MainClass creates an CentralObjectProvider which creates all other parts
 * of the program and with it a BEASTCommunicator which coordinates them.
 *
 * @author Jonas
 */
public class MainClass {

    /**
     * Starts BEAST
     *
     * @param args not used
     */
    public static void main(String[] args) {
        BEASTCommunicator communicator = new BEASTCommunicator();
        CentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
        communicator.setCentralObjectProvider(centralObjectProvider);
    }
}
