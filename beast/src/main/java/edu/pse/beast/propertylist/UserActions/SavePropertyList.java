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
		if (false) { // if it has been saved already
			// save the file with given filename
		}
		else {
			SaveAsPropertyList spl = new SaveAsPropertyList(controller);
			spl.perform();
		}
		
	}

}
