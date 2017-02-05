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
		
		/*if (controller.getSaveBeforeChangeHandler().isChangedSinceSave() &&
				JOptionPane.showConfirmDialog(controller.getView(), 
						controller.getMenuStringLoader().getStringFromID("wantToSaveBefore"),
						controller.getMenuStringLoader().getStringFromID("saveList"),
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			SaveAsPropertyList spl = new SaveAsPropertyList(controller);
			spl.perform();
		}*/ // TODO what makes more sense?
		
		
		
		if (controller.getSaveBeforeChangeHandler().isChangedSinceSave()) {
			int ask = JOptionPane.showConfirmDialog(controller.getView(), 
					controller.getMenuStringLoader().getStringFromID("wantToSaveBefore"),
					controller.getMenuStringLoader().getStringFromID("saveList"),
					JOptionPane.OK_CANCEL_OPTION);
			
			if (ask == JOptionPane.OK_OPTION) {
				SaveAsPropertyList spl = new SaveAsPropertyList(controller);
				spl.perform();
			}
			else {
				if (JOptionPane.showConfirmDialog(controller.getView(), 
						controller.getMenuStringLoader().getStringFromID("areYouSure"),
						controller.getMenuStringLoader().getStringFromID("newList"), 
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
				{
					controller.getSaveBeforeChangeHandler().setChangedSinceSave(false);
					controller.getModel().userActionNewList();
				}
				else return;
			}
		}
		
		controller.getSaveBeforeChangeHandler().setChangedSinceSave(false);
		controller.getModel().userActionNewList();
	}

}
