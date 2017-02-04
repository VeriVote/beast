package edu.pse.beast.propertylist.UserActions;

import java.io.File;

import javax.swing.JFileChooser;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class LoadPropertyList extends UserAction {

	private final PropertyList controller;
	
	public LoadPropertyList(PropertyList controller) {
		super("load");
		this.controller = controller;
	}

	@Override
	public void perform() {
		// TODO ask model if it has been touched since last save
		
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(controller.getView()) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// TODO load the file
		}
		
	}

}
