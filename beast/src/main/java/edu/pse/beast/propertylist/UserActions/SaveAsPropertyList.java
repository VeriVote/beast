package edu.pse.beast.propertylist.UserActions;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class SaveAsPropertyList extends UserAction {
	
	private final PropertyList list;

	public SaveAsPropertyList(PropertyList list) {
		super("save_as");
		this.list = list;
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}
	
	

}
