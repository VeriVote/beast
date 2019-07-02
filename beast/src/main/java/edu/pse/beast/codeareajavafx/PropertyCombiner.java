package edu.pse.beast.codeareajavafx;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import edu.pse.beast.highlevel.MainApplicationClass;
import edu.pse.beast.highlevel.javafx.TabClass;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;

public class PropertyCombiner extends TabClass {
    private final String fileEnding;
    private final String initialDir;
    private final String fileExtensionDescription;
    private boolean hasSaveFile = false;
    private File saveFile = null;

    public PropertyCombiner(Tab associatedTab, String fileEnding,
                            String initialDir,
                            String fileExtensionDescription) {
        super(associatedTab);
        this.fileEnding = fileEnding;
        this.initialDir = initialDir;
        this.fileExtensionDescription = fileExtensionDescription;
    }

    public void save(String fileName, String preText, String postText) {
        if (hasSaveFile) {
            if (saveFile != null) {
                save(saveFile, preText, postText);
            }
        } else {
            saveAs(fileName, preText, postText);
        }
    }

    public void save(File toSaveIn, String preText, String postText) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(toSaveIn);
            String combined = "pre:" + preText + "|postText" + postText;
            out.write(combined);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void saveAs(String fileName, String preText, String postText) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                fileExtensionDescription + " (*" + fileEnding + ")", "*." + fileEnding));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
//
//      fileChooser.selectedExtensionFilterProperty()
//      fileChooser.setSelectedExtensionFilter(
//              new FileChooser.ExtensionFilter("Extension", "*" + fileEnding));
//      fileChooser.setSelectedExtensionFilter(
//          new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        // fileChooser.setTitle("Save document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser.showSaveDialog(MainApplicationClass.getMainStage());
        if (selectedFile != null) {
            save(selectedFile, preText, postText);
        }
    }

    public void load(NewPropertyCodeArea preCodeArea, NewPropertyCodeArea postCodeArea) {
        FileChooser fileChooser = new FileChooser();
        // fileChooser.setTitle("Load document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setSelectedExtensionFilter(
            new FileChooser.ExtensionFilter("Arbitrary RTFX file",
                                            "*" + fileEnding));
        File selectedFile = fileChooser.showOpenDialog(MainApplicationClass.getMainStage());
        if (selectedFile != null) {
            load(selectedFile, preCodeArea, postCodeArea);
        }
    }

    public void load(File toLoadFrom, NewPropertyCodeArea preCodeArea,
                     NewPropertyCodeArea postCodeArea) {
        try {
            FileInputStream fis = new FileInputStream(toLoadFrom);
            DataInputStream dis = new DataInputStream(fis);
            String newText = dis.readUTF();
            fis.close();
            preCodeArea.deleteText(0, preCodeArea.getLength());
            postCodeArea.deleteText(0, postCodeArea.getLength());

            // super.appendText(newText);
            preCodeArea.appendText(newText);
            postCodeArea.appendText(newText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}