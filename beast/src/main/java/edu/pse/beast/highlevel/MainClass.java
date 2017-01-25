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
        
        CentralObjectProvider centralObjectProvider = new PSECentralObjectProvider();
        
        BEASTCommunicator communicator = new BEASTCommunicator(centralObjectProvider);
          
    }
}
