package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class SavePropertyList extends UserAction {

    private final PropertyList controller;

    public SavePropertyList(PropertyList controller) {
        super("save");
        this.controller = controller;
    }

    @Override
    public void perform() {
        if (controller.getFileChooser().saveObject(controller.getModel(), false)) {
            controller.getChangeHandler().setChangedSinceSave(false);
            controller.getView().setWindowTitle(controller.getModel().getName());
        }
    }

}
