package edu.pse.beast.propertylist.UserActions;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
		// TODO make method static somewhere
		
		if (controller.getSaveBeforeChangeHandler().isChangedSinceSave()) {
			int ask = JOptionPane.showConfirmDialog(controller.getView(), 
					controller.getMenuStringLoader().getStringFromID("wantToSaveBefore"),
					controller.getMenuStringLoader().getStringFromID("saveList"),
					JOptionPane.OK_CANCEL_OPTION);
			
			if (ask == JOptionPane.OK_OPTION) {
				SaveAsPropertyList spl = new SaveAsPropertyList(controller);
				spl.perform();
			}
			
		}
				
		
		
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(controller.getView()) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// TODO load the file
			// setChanged to false und setSaveLocation auf file.getFileLoc
		}
		
	}

}
