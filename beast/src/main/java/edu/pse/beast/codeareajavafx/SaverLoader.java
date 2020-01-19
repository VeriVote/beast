package edu.pse.beast.codeareajavafx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.stage.FileChooser;

import edu.pse.beast.highlevel.MainApplicationClass;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.saverloader.MinimalSaverInterface;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 * The Class SaverLoader.
 *
 * @author Lukas Stapelbroek
 */
public class SaverLoader {

    /** The Constant PROP_LIST_FILE_ENDING. */
    public static final String PROP_LIST_FILE_ENDING = ".propList";

    /** The Constant CHILD_PROP_FILE_ENDING. */
    public static final String CHILD_PROP_FILE_ENDING = ".child";

    /** The Constant PROJECT_FILE_ENDING. */
    public static final String PROJECT_FILE_ENDING = ".proj";

    /** The Constant ELEC_DESCR_FILE_ENDING. */
    public static final String ELEC_DESCR_FILE_ENDING = ".elec";

    /** The Constant OPT_FILE_ENDING. */
    public static final String OPT_FILE_ENDING = ".opt";

    /** The Constant PROP_DESCR_FILE_ENDING. */
    public static final String PROP_DESCR_FILE_ENDING = ".prop";

    /** The owner. */
    private final MinimalSaverInterface owner;

    /** The has changes. */
    private boolean hasChanges = false;
    // small hack, update values later on with listeners

    /** The has save file. */
    private boolean hasSaveFile = false;

    /** The save file. */
    private File saveFile = null;

    /** The initial dir. */
    private final String initialDir;

    /** The file ending. */
    private final String fileEnding;

    /** The file extension description. */
    private final String fileExtensionDescription;

    /**
     * The constructor.
     *
     * @param fileEnd
     *            the file end
     * @param fileExtensionDescr
     *            the file extension descr
     * @param ownerInterface
     *            the owner interface
     */
    public SaverLoader(final String fileEnd, final String fileExtensionDescr,
                       final MinimalSaverInterface ownerInterface) {
        this.initialDir = SuperFolderFinder.getSuperFolder() + "/projectFiles/";
        // make sure, that the initial folder exists
        new File(initialDir).mkdirs();
        this.fileEnding = fileEnd;
        this.fileExtensionDescription = fileExtensionDescr;
        this.owner = ownerInterface;
    }

    /**
     * Save.
     *
     * @param fileName
     *            the file name
     * @param text
     *            the text
     */
    public void save(final String fileName, final String text) {
        if (hasSaveFile) {
            if (saveFile != null) {
                saveToDisk(saveFile, text);
            }
        } else {
            saveAs(fileName, text);
        }
    }

    /**
     * Save.
     *
     * @param file
     *            the file
     * @param text
     *            the text
     */
    public void save(final File file, final String text) {
        if (hasSaveFile) {
            if (saveFile != null) {
                saveToDisk(saveFile, text);
            }
        } else {
            saveAs(file, text);
        }
    }

    /**
     * Saves the given text in the given file. Does NOT check if there already
     * exists a previous save file
     *
     * @param toSaveIn
     *            given file
     * @param text
     *            given text
     */
    public void saveToDisk(final File toSaveIn, final String text) {
        // hasChanges = false; //TODO enable again when it is updated with
        // listeners
        PrintWriter out = null;
        try {
            out = new PrintWriter(toSaveIn);
            out.write(text);
            hasSaveFile = true;
            saveFile = toSaveIn;
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
     * @param text
     *            the text
     */
    public void saveAs(final String fileName, final String text) {
        File selectedFile = showFileSaveDialog(fileName);
        if (selectedFile != null) {
            saveToDisk(selectedFile, text);
        }
    }

    /**
     * Save as.
     *
     * @param file
     *            the file
     * @param text
     *            the text
     */
    public void saveAs(final File file, final String text) {
        if (file != null) {
            saveToDisk(file, text);
        }
    }

    /**
     * Show file save dialog.
     *
     * @param fileName
     *            the file name
     * @return the file
     */
    public File showFileSaveDialog(final String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                fileExtensionDescription, "*" + fileEnding));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser
                .showSaveDialog(MainApplicationClass.getMainStage());
        return selectedFile;
    }

    /**
     * Show file load dialog.
     *
     * @param fileName
     *            the file name
     * @return the file
     */
    public File showFileLoadDialog(final String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                fileExtensionDescription, "*" + fileEnding));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser
                .showOpenDialog(MainApplicationClass.getMainStage());
        return selectedFile;
    }

    /**
     * Load.
     *
     * @return the string
     */
    public String load() {
        File selectedFile = showFileLoadDialog("");
        if (selectedFile != null) {
            return load(selectedFile);
        }
        return "";
    }

    /**
     * Load.
     *
     * @param toLoadFrom
     *            the to load from
     * @return the string
     */
    public String load(final File toLoadFrom) {
        checkToSaveChanges();
        try {
            return readFile(toLoadFrom, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Read file.
     *
     * @param file
     *            the file
     * @param encoding
     *            the encoding
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static String readFile(final File file, final Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(encoded, encoding);
    }

    /**
     * Check to save changes.
     */
    private void checkToSaveChanges() {
        if (hasChanges) {
            GUIController.getController().showSaveChangesDialog(owner);
            hasChanges = false;
        }
    }

    /**
     * Reset has save file.
     */
    public void resetHasSaveFile() {
        hasSaveFile = false;
    }

    /**
     * Checks for save file.
     *
     * @return true, if successful
     */
    public boolean hasSaveFile() {
        return hasSaveFile;
    }

    /**
     * Gets the save file.
     *
     * @return the save file
     */
    public File getSaveFile() {
        return saveFile;
    }

    /**
     * Sets the save file.
     *
     * @param file
     *            the new save file
     */
    public void setSaveFile(final File file) {
        if (file != null && file.exists()) {
            this.saveFile = file;
            this.hasSaveFile = true;
        }
    }

    /**
     * Checks for changed.
     */
    public void hasChanged() {
        this.hasChanges = true;
    }

    /**
     * Reset has changes.
     */
    public void resetHasChanges() {
        this.hasChanges = false;
    }
}
