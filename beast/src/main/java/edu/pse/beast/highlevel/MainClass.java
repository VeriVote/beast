package edu.pse.beast.highlevel;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The MainClass creates an CentralObjectProvider and with it a BEASTCommunicator
 which starts all processes to run BEAST.
 *
 * @author Jonas
 */
public class MainClass {

    /**
     * Starts BEAST
     *
     * @param args
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        
        BEASTCommunicator communicator = new BEASTCommunicator();
        
        CentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
        
        communicator.setCentralObjectProvider(centralObjectProvider);
          
    }
}
