package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer.ElectionInputTypeIds;
import edu.pse.beast.electionSimulator.ElectionSimulation;

/**
 * The MainClass creates an CentralObjectProvider which creates all other parts
 * of the program and with it a BEASTCommunicator which coordinates them.
 *
 * @author Jonas
 */
public class MainClass {

    /**
     * Starts BEAST by creating a BEASTCommunicator and corresponding CentralObjectProvider.
     * If you want to replace one or more implementation of high level interfaces you have to
     * create a new implementation of CentralObjectProvider and replace PSECentralObjectProvider
     * with it here.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        BEASTCommunicator communicator = new BEASTCommunicator();
        PSECentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
        communicator.setCentralObjectProvider(centralObjectProvider);
    	
    	//  	new ElectionSimulation(centralObjectProvider);
    }
}
