package edu.pse.beast.codeareajavafx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.pse.beast.highlevel.MainApplicationClass;
import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.stage.FileChooser;

public class SaverLoader {
    public static final String PROP_LIST_FILE_ENDING  = ".propList";
    public static final String CHILD_PROP_FILE_ENDING = ".child";
    public static final String PROJECT_FILE_ENDING    = ".proj";
    public static final String ELEC_DESCR_FILE_ENDING = ".elec";
    public static final String OPT_FILE_ENDING        = ".opt";
    public static final String PROP_DESCR_FILE_ENDING = ".prop";

    private boolean hasSaveFile = false;
    private File saveFile = null;
    private final String initialDir;
    private final String fileEnding;
    private final String fileExtensionDescription;

    public SaverLoader(String fileEnding, String fileExtensionDescription) {
        this.initialDir = SuperFolderFinder.getSuperFolder() + "/projectFiles/";
        new File(initialDir).mkdirs(); // make sure, that the initial folder exists
        this.fileEnding = fileEnding;
        this.fileExtensionDescription = fileExtensionDescription;
    }

    public void save(String fileName, String text) {
        if (hasSaveFile) {
            if (saveFile != null) {
                saveToDisk(saveFile, text);
            }
        } else {
            saveAs(fileName, text);
        }
    }

    public void save(File file, String text) {
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
    public void saveToDisk(File toSaveIn, String text) {
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

    public void saveAs(String fileName, String text) {
        File selectedFile = showFileSaveDialog(fileName);
        if (selectedFile != null) {
            saveToDisk(selectedFile, text);
        }
    }

    public void saveAs(File file, String text) {
        if (file != null) {
            saveToDisk(file, text);
        }
    }

    public File showFileSaveDialog(String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(fileExtensionDescription, "*" + fileEnding));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All (*.*)", "*.*"));
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName(fileName + fileEnding);
        File selectedFile = fileChooser.showSaveDialog(MainApplicationClass.getMainStage());
        return selectedFile;
    }

    public File showFileLoadDialog(String fileName) {
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

    public String load(File toLoadFrom) {
        try {
            return readFile(toLoadFrom, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(encoded, encoding);
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

    public void setSaveFile(File saveFile) {
        if (saveFile != null && saveFile.exists()) {
            this.saveFile = saveFile;
            this.hasSaveFile = true;
        }
    }

    public void saveAs(File file) {
        // TODO Auto-generated method stub
    }
}