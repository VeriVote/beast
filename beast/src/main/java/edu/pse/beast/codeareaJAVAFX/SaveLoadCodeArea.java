package edu.pse.beast.codeareaJAVAFX;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.highlevel.MainClass;
import javafx.stage.FileChooser;

public abstract class SaveLoadCodeArea extends CodeArea {
	private boolean hasSaveFile = false;
	private File saveFile = null;
	
	private final String initialDir;
	private final String fileEnding;
	private final String fileExtensionDescription;
	
	
	public SaveLoadCodeArea(String fileEnding, String initialDir, String fileExtensionDescription) {
		this.fileEnding = fileEnding;
		this.initialDir = initialDir;
		this.fileExtensionDescription = fileExtensionDescription;
	}
	
	public void save(String fileName) {
		if (hasSaveFile) {
			if (saveFile != null) {
	            save(saveFile);
	        }
		} else {
			saveAs(fileName);
		}
	}
	
	public void save(File toSaveIn) {
		
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(toSaveIn);
			
			out.write(super.getText());

        } catch (IOException ioE) {
            ioE.printStackTrace();
        } finally {
        	if (out != null) {
        		out.close();
        	}
        }
	}
	
	public void saveAs(String fileName) {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileExtensionDescription + " (*" + fileEnding + ")", "*" + fileEnding));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));

        
//        
//        fileChooser.selectedExtensionFilterProperty()
//        fileChooser.setSelectedExtensionFilter(
//                new FileChooser.ExtensionFilter("Extension", "*" + fileEnding));
//        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        //fileChooser.setTitle("Save document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser.showSaveDialog(MainClass.getMainStage());
        if (selectedFile != null) {
            save(selectedFile);
        }
	}
	
	public void load() {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Load document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileExtensionDescription + " (*" + fileEnding + ")", "*" + fileEnding));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(MainClass.getMainStage());
        if (selectedFile != null) {
            load(selectedFile);
        }
	}
	
	public void load(File toLoadFrom) {
		try {
            FileInputStream fis = new FileInputStream(toLoadFrom);
            DataInputStream dis = new DataInputStream(fis);
            
            String newText = dis.readUTF();            
            fis.close();

            super.deleteText(0, super.getLength());
            
            super.appendText(newText);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
