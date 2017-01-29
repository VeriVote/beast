package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.View.NewPropertyWindow;
import edu.pse.beast.toolbox.UserAction;

public class NewPropertyList extends UserAction {
	
	private final PropertyList list;

	public NewPropertyList(PropertyList list) {
		super("new");
		this.list = list;
	}

	@Override
	public void perform() {
		// TODO 
		//new NewPropertyWindow();
	}

}
