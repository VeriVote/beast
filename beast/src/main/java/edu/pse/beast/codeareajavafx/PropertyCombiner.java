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

    public PropertyCombiner(final Tab associatedTab, final String fileEnd,
                            final String initDir,
                            final String fileExtensionDescr) {
        super(associatedTab);
        this.fileEnding = fileEnd;
        this.initialDir = initDir;
        this.fileExtensionDescription = fileExtensionDescr;
    }

    public void save(final String fileName,
                     final String preText,
                     final String postText) {
        if (hasSaveFile) {
            if (saveFile != null) {
                save(saveFile, preText, postText);
            }
        } else {
            saveAs(fileName, preText, postText);
        }
    }

    public void save(final File toSaveIn,
                     final String preText,
                     final String postText) {
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

    public void saveAs(final String fileName,
                       final String preText,
                       final String postText) {
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

    public void load(final NewPropertyCodeArea preCodeArea,
                     final NewPropertyCodeArea postCodeArea) {
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

    public void load(final File toLoadFrom, final NewPropertyCodeArea preCodeArea,
                     final NewPropertyCodeArea postCodeArea) {
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
