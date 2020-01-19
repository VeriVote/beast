package edu.pse.beast.codeareajavafx;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.scene.control.Tab;
import javafx.stage.FileChooser;

import edu.pse.beast.highlevel.MainApplicationClass;
import edu.pse.beast.highlevel.javafx.TabClass;

/**
 * The Class PropertyCombiner.
 *
 * @author Lukas Stapelbroek
 */
public class PropertyCombiner extends TabClass {

    /** The file ending. */
    private final String fileEnding;

    /** The initial dir. */
    private final String initialDir;

    /** The file extension description. */
    private final String fileExtensionDescription;

    /** The has save file. */
    private boolean hasSaveFile = false;

    /** The save file. */
    private File saveFile = null;

    /**
     * The constructor.
     *
     * @param associatedTab
     *            the associated tab
     * @param fileEnd
     *            the file end
     * @param initDir
     *            the init dir
     * @param fileExtensionDescr
     *            the file extension descr
     */
    public PropertyCombiner(final Tab associatedTab, final String fileEnd,
                            final String initDir,
                            final String fileExtensionDescr) {
        super(associatedTab);
        this.fileEnding = fileEnd;
        this.initialDir = initDir;
        this.fileExtensionDescription = fileExtensionDescr;
    }

    /**
     * Save.
     *
     * @param fileName
     *            the file name
     * @param preText
     *            the pre text
     * @param postText
     *            the post text
     */
    public void save(final String fileName, final String preText,
                     final String postText) {
        if (hasSaveFile) {
            if (saveFile != null) {
                save(saveFile, preText, postText);
            }
        } else {
            saveAs(fileName, preText, postText);
        }
    }

    /**
     * Save.
     *
     * @param toSaveIn
     *            the to save in
     * @param preText
     *            the pre text
     * @param postText
     *            the post text
     */
    public void save(final File toSaveIn, final String preText,
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

    /**
     * Save as.
     *
     * @param fileName
     *            the file name
     * @param preText
     *            the pre text
     * @param postText
     *            the post text
     */
    public void saveAs(final String fileName, final String preText,
                       final String postText) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(
                        fileExtensionDescription + " (*" + fileEnding + ")",
                        "*." + fileEnding));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));

        // fileChooser.selectedExtensionFilterProperty()
        // fileChooser.setSelectedExtensionFilter(
        //         new FileChooser.ExtensionFilter("Extension", "*" + fileEnding));
        // fileChooser.setSelectedExtensionFilter(
        //         new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        // fileChooser.setTitle("Save document");

        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile =
                fileChooser.showSaveDialog(MainApplicationClass.getMainStage());
        if (selectedFile != null) {
            save(selectedFile, preText, postText);
        }
    }

    /**
     * Load.
     *
     * @param preCodeArea
     *            the pre code area
     * @param postCodeArea
     *            the post code area
     */
    public void load(final NewPropertyCodeArea preCodeArea,
                     final NewPropertyCodeArea postCodeArea) {
        FileChooser fileChooser = new FileChooser();
        // fileChooser.setTitle("Load document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "Arbitrary RTFX file", "*" + fileEnding));
        File selectedFile =
                fileChooser.showOpenDialog(MainApplicationClass.getMainStage());
        if (selectedFile != null) {
            load(selectedFile, preCodeArea, postCodeArea);
        }
    }

    /**
     * Load.
     *
     * @param toLoadFrom
     *            the to load from
     * @param preCodeArea
     *            the pre code area
     * @param postCodeArea
     *            the post code area
     */
    public void load(final File toLoadFrom,
                     final NewPropertyCodeArea preCodeArea,
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
