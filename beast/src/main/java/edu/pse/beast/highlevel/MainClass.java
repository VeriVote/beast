package edu.pse.beast.highlevel;

import java.io.IOException;
import java.net.URISyntaxException;

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
     * @param args
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        
        BEASTCommunicator communicator = new BEASTCommunicator();
        
        CentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
        
        communicator.setCentralObjectProvider(centralObjectProvider);
          
    }
}
