package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.electionSimulator.ElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * Loads a new property list.
 * @author Lukas
 */
public class OpenSimulationWindow extends UserAction {

	
	private final ElectionSimulation simulationWindow = new ElectionSimulation();
    private final PropertyList controller;

    /**
     * Constructor
     * @param controller A reference to the PropertyList controller
     */
    public OpenSimulationWindow(PropertyList controller) {
        super("simulation");
        this.controller = controller;
        controller.setMarginComputationBoxVisible(true);
    }

    @Override
    public void perform() {
    	//controller.setMarginComputationBoxVisible(!simulationWindow.isOpen());
        if (simulationWindow.isOpen()) {
        	simulationWindow.close();
        } else {
        	simulationWindow.open();
        }
    }
}
