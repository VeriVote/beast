package edu.pse.beast.propertylist;

import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;

/**
 * Class to perform and undo deletions from the property list.
 * @author Justin
 *
 */
public class DeleteDescriptionAction {
	PLModelInterface model;
	PropertyItem item;

	/**
	 * Constructor
	 * @param model The data model of the property list
	 * @param item The deleted property item
	 */
	public DeleteDescriptionAction(PLModelInterface model, PropertyItem item) {
		this.model = model;
		this.item = item;
	}

	
	/**
	 * Deletes the property.
	 */
	public void perform() {
		model.deleteProperty(item);
	}

	/**
	 * Undo the deletion of the property.
	 */
	public void undo() {
		model.addDescription(item);
	}

}
