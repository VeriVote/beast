package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class UndoChangesPropertyList extends UserAction {

    private final PropertyList list;

    public UndoChangesPropertyList(PropertyList list) {
        super("undo");
        this.list = list;
    }

    @Override
    public void perform() {
        // TODO 

    }

}
