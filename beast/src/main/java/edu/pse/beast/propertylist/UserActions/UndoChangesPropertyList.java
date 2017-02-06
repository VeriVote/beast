package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.DeleteDescriptionAction;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class UndoChangesPropertyList extends UserAction {

    private final PropertyList controller;

    public UndoChangesPropertyList(PropertyList controller) {
        super("undo");
        this.controller = controller;
    }

    @Override
    public void perform() {
        DeleteDescriptionAction lastAct = controller.getLastAction();
        
        if (lastAct != null) {
        	lastAct.undo();
        }

    }

}
