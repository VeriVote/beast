package edu.pse.beast.propertylist.UserActions;

import javax.swing.JOptionPane;

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
		if (controller.getModel().getList().isEmpty()) return;
		if (JOptionPane.showConfirmDialog(controller.getView(), 
				controller.getMenuStringLoader().getStringFromID("areYouSure"),
				controller.getMenuStringLoader().getStringFromID("newList"), 
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
		{
			controller.getModel().userActionNewList();
		}
		
	}

}
