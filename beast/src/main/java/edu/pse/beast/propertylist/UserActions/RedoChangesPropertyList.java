package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class RedoChangesPropertyList extends UserAction {
	
	private final PropertyList list;

	public RedoChangesPropertyList(PropertyList list) {
		super("redo");
		this.list = list;
	}

	@Override
	public void perform() {
		// TODO
		
	}

}
