package edu.pse.beast.propertylist;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;

/**
 * Class to perform and undo deletions from the property list.
 * @author Justin
 *
 */
public class DeleteDescriptionAction {
	PLModel model;
	PropertyItem item;
	BooleanExpEditor editor;

	/**
	 * Constructor
	 * @param model The data model of the property list
	 * @param item The deleted property item
	 */
	public DeleteDescriptionAction(PLModel model, PropertyItem item, BooleanExpEditor editor) {
		this.model = model;
		this.item = item;
		this.editor = editor;
	}

	
	/**
	 * Deletes the property.
	 */
	public void perform() {
		model.deleteProperty(item, editor);
	}

	/**
	 * Undo the deletion of the property.
	 */
	public void undo() {
		model.addDescription(item);
	}

}
