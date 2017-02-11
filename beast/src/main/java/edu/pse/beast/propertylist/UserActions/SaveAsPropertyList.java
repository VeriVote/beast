package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class SaveAsPropertyList extends UserAction {

    private final PropertyList controller;

    public SaveAsPropertyList(PropertyList controller) {
        super("save_as");
        this.controller = controller;
    }

    @Override
    public void perform() {
        if (controller.getFileChooser().saveObject(controller.getModel(), true)) {
            controller.getChangeHandler().setChangedSinceSave(false);
            controller.getView().setWindowTitle(controller.getModel().getName());
        }
    }
}
