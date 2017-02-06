package edu.pse.beast.propertylist;

import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;

public class DeleteDescriptionAction {
	PLModelInterface model;
	PropertyItem item;

	public DeleteDescriptionAction(PLModelInterface model, PropertyItem item) {
		this.model = model;
		this.item = item;
	}
	
	
    public void perform() {
    	model.deleteProperty(item);
    }

    public void undo() {
    	model.addDescription(item);
    }

}
