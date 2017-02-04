package edu.pse.beast.propertylist.UserActions;

import java.io.File;

import javax.swing.JFileChooser;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author Justin
 */
public class SaveAsPropertyList extends UserAction {
	
	private final PropertyList controller;

	public SaveAsPropertyList(PropertyList controller) {
		super("save_as");
		this.controller = controller;
	}

	@Override
	public void perform() {
		JFileChooser fc = new JFileChooser();
		if (fc.showSaveDialog(controller.getView()) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// TODO save
		}
		
	}
	
	

}
