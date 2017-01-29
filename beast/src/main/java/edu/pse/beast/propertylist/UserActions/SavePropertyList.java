package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class SavePropertyList extends UserAction {
	
	private final PropertyList list;

	public SavePropertyList(PropertyList list) {
		super("save");
		this.list = list;
	}

	@Override
	public void perform() {
		// TODO
		
	}

}
