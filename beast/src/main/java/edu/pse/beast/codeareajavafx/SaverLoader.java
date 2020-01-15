package edu.pse.beast.codeareajavafx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.pse.beast.highlevel.MainApplicationClass;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.saverloader.MinimalSaverInterface;
import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.stage.FileChooser;

public class SaverLoader {
    public static final String PROP_LIST_FILE_ENDING  = ".propList";
    public static final String CHILD_PROP_FILE_ENDING = ".child";
    public static final String PROJECT_FILE_ENDING    = ".proj";
    public static final String ELEC_DESCR_FILE_ENDING = ".elec";
    public static final String OPT_FILE_ENDING        = ".opt";
    public static final String PROP_DESCR_FILE_ENDING = ".prop";

    private final MinimalSaverInterface owner;

    private boolean hasChanges = false; //small hack, update values later on with listeners
    private boolean hasSaveFile = false;
    private File saveFile = null;
    private final String initialDir;
    private final String fileEnding;
    private final String fileExtensionDescription;

    public SaverLoader(final String fileEnd, final String fileExtensionDescr,
                       final MinimalSaverInterface ownerInterface) {
        this.initialDir = SuperFolderFinder.getSuperFolder() + "/projectFiles/";
        new File(initialDir).mkdirs(); // make sure, that the initial folder exists
        this.fileEnding = fileEnd;
        this.fileExtensionDescription = fileExtensionDescr;
        this.owner = ownerInterface;
    }

    public void save(final String fileName, final String text) {
        if (hasSaveFile) {
            if (saveFile != null) {
                saveToDisk(saveFile, text);
            }
        } else {
            saveAs(fileName, text);
        }
    }

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
     * @param toSaveIn given file
     * @param text given text
     */
    public void saveToDisk(final File toSaveIn, final String text) {
        //hasChanges = false; //TODO enable again when it is updated with listeners
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

    public void saveAs(final String fileName, final String text) {
        File selectedFile = showFileSaveDialog(fileName);
        if (selectedFile != null) {
            saveToDisk(selectedFile, text);
        }
    }

    public void saveAs(final File file, final String text) {
        if (file != null) {
            saveToDisk(file, text);
        }
    }

    public File showFileSaveDialog(final String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser.showSaveDialog(MainApplicationClass.getMainStage());
        return selectedFile;
    }

    public File showFileLoadDialog(final String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser.showOpenDialog(MainApplicationClass.getMainStage());
        return selectedFile;
    }

    public String load() {
        File selectedFile = showFileLoadDialog("");
        if (selectedFile != null) {
            return load(selectedFile);
        }
        return "";
    }

    public String load(final File toLoadFrom) {
        checkToSaveChanges();
        try {
            return readFile(toLoadFrom, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String readFile(final File file,
                                   final Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(encoded, encoding);
    }

    private void checkToSaveChanges() {
        if (hasChanges) {
            GUIController.getController().showSaveChangesDialog(owner);
            hasChanges = false;
        }
    }

    public void resetHasSaveFile() {
        hasSaveFile = false;
    }

    public boolean hasSaveFile() {
        return hasSaveFile;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(final File file) {
        if (file != null && file.exists()) {
            this.saveFile = file;
            this.hasSaveFile = true;
        }
    }

    public void hasChanged() {
        this.hasChanges = true;
    }

    public void resetHasChanges() {
        this.hasChanges = false;
    }
}
