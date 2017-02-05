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
		if (controller.getSaveBeforeChangeHandler().getSaveLocation() != null) { // if it has been saved already
			// save the file with given filename
			// controller.getSaveBeforeChangeHandler().setSaveLocation, changedSinceSave(false)
		}
		else {
			SaveAsPropertyList spl = new SaveAsPropertyList(controller);
			spl.perform();
		}
		
	}

}
