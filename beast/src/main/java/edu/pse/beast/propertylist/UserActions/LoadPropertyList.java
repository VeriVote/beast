package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class LoadPropertyList extends UserAction {

	private final PropertyList list;
	
	public LoadPropertyList(PropertyList list) {
		super("load");
		this.list = list;
	}

	@Override
	public void perform() {
		// TODO 
		
	}

}
