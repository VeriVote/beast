package edu.pse.beast.propertylist.UserActions;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
            // if success con.getsavebefore.setChanged(false)
            XMLEncoder encoder;
			try {
				encoder = new XMLEncoder(new FileOutputStream(file));
				encoder.writeObject(controller.getModel());
	            encoder.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }

    }

}
