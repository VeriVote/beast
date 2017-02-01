package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.View.NewPropertyWindow;
import edu.pse.beast.toolbox.UserAction;

public class NewPropertyList extends UserAction {
	
	private final PropertyList controller;

	public NewPropertyList(PropertyList controller) {
		super("new");
		this.controller = controller;
	}

	@Override
	public void perform() {
		controller.getModel().userActionNewList();
	}

}
