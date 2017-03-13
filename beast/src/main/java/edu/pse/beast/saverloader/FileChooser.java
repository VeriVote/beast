package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;

/**
 * Class that allows saving and loading files.
 * @author NikolaiLMS
 */
public class FileChooser {
    private JFileChooser fileChooser;
    private StringResourceLoader stringResourceLoader;
    private Component component;
    private SaverLoader saverLoader;
    private File lastLoadedFile;
    private boolean hasBeenSaved = false;
    private Object[] options = {"OK"};
    private String saveChanges;
    private String save;
    private String yesOption;
    private String noOption;
    private String cancelOption;

    /**
     * Constructor
     * @param stringResourceLoader the stringResourceLoader providing the Strings needed for displaying save and open
     *                             JFileChoosers
     * @param saverLoader a class implementing the SaverLoader interface for creating Strings from objects
     *                    and vice versa
     * @param component the component the JFileChoosers should be displayed in, currently not in use since components
     *                  might be too small for JFileChoosers and cause unaesthetic window placement
     */
    public FileChooser(StringResourceLoader stringResourceLoader, SaverLoader saverLoader, Component component) {
        this.fileChooser = new JFileChooser();
        this.stringResourceLoader = stringResourceLoader;
        this.component = component;
        this.saverLoader = saverLoader;
        updateStringRessourceLoader(stringResourceLoader);
    }

    /**
     * Method that obtains a file location either by JFileChooser or previously save-location and then calls
     * saveToFile to save the given object.
     * Calls itself recursively if saveToFile returns false.
     * @param object the object to be saved, has to correspond to the given SaverLoader class.
     * @param forceDialog true if a save Dialog should be forced (SaveAs UserAction), false otherwise (SaveAction)
     * @return true if saving was successful
     */
    public boolean saveObject(Object object, boolean forceDialog) {
        if (fileChooser.getSelectedFile() != null) {
            fileChooser.setSelectedFile(new File(
                    fileChooser.getSelectedFile().getParent() + "/" + ((NameInterface) object).getName()
                    + stringResourceLoader.getStringFromID(
                            "fileSuffix")));
        } else {
            fileChooser.setSelectedFile(new File(System.getProperty("user.home") + "/"
                    + ((NameInterface) object).getName()));
        }

        fileChooser.setApproveButtonText(stringResourceLoader.getStringFromID("saveApproveButtonText"));
        if (hasBeenSaved && !forceDialog) {
            return saveToFile(object, lastLoadedFile);
        } else {
            if (fileChooser.showDialog(component, stringResourceLoader.getStringFromID("saveDialogTitleText"))
                    == JFileChooser.APPROVE_OPTION) {
                if (!saveToFile(object, fileChooser.getSelectedFile())) {
                    return saveObject(object, forceDialog);
                } else {
                    lastLoadedFile = fileChooser.getSelectedFile();
                    hasBeenSaved = true;
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Opens the JFileChooser and when a valid input based on this class' SaverLoader objects is selected, creates
     * an object with it and returns it.
     * @return the loaded object upon success, null otherwise
     */
    public Object loadObject() {

        fileChooser.setApproveButtonText(stringResourceLoader.getStringFromID("openApproveButtonText"));
        if (fileChooser.showDialog(component, stringResourceLoader.getStringFromID("openDialogTitleText"))
                == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String content = "";
            BufferedReader inputReader = null;

            boolean caught = false;

            try {
                inputReader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(selectedFile), "UTF8"));
            } catch (UnsupportedEncodingException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "wrongEncodingError"), "",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                caught = true;
            } catch (FileNotFoundException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID("inputOutputErrorOpen"), "",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);

                caught = true;
            }
            if (caught) {
                if (inputReader != null) {
                    try {
                        inputReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return (loadObject());
            }

            String sCurrentLine;
            try {
                while ((sCurrentLine = inputReader.readLine()) != null) {
                    content += (sCurrentLine + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID("inputOutputErrorOpen"), "",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                return (loadObject());
            }
            Object outputObject = null;
            boolean closed = false;
            try {
                outputObject = saverLoader.createFromSaveString(content);
                lastLoadedFile = selectedFile;
                hasBeenSaved = true;
            } catch (Exception e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "invalidFileFormatErrorMessage"), "",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                return (loadObject());
            } finally {
                try {
                    inputReader.close();
                    closed = true;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    closed = false;
                }
            }
            if (closed) {
                return outputObject;
            } else {
                return loadObject();
            }

        } else {
            return null;
        }
    }

    /**
     * Method that saves the given
     * @param object to the given
     * @param file location.
     * @return true if saving was successful, false if not
     */
    private boolean saveToFile(Object object, File file) {
        File localfile = file;
        if (!localfile.getName().matches("[_a-zA-Z0-9\\-\\.\\s]+")) {
            JOptionPane.showOptionDialog(null,
                    stringResourceLoader.getStringFromID("wrongFileNameError"), "",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (!fileChooser.getSelectedFile().getName().endsWith(stringResourceLoader.getStringFromID(
                "fileSuffix"))) {
            localfile = new File(localfile.getAbsolutePath() + stringResourceLoader.getStringFromID("fileSuffix"));
        }
        fileChooser.setSelectedFile(localfile);
        String saveString;
        ((NameInterface) object).setNewName(localfile.getName().split(stringResourceLoader.getStringFromID(
                "fileSuffix"))[0]);
        saveString = saverLoader.createSaveString(object);
        Writer out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(localfile), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showOptionDialog(null,
                    stringResourceLoader.getStringFromID(
                            "wrongEncodingError"), "",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } catch (FileNotFoundException e) {
            JOptionPane.showOptionDialog(null,
                    stringResourceLoader.getStringFromID("inputOutputErrorSave"), "",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        }
        boolean closed = false;
        try {
            try {
                out.write(saveString);
            } catch (IOException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "objectCouldNotBeSavedError"), "",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                return false;
            }
        } finally {
            try {
                out.close();
                closed = true;
            } catch (IOException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "objectCouldNotBeSavedError"), "",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                closed = false;
            }
        }
        if (closed) {
            lastLoadedFile = localfile;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that calls showSaveOptionPane and based on its return saves the
     * given object, called when a new Object is loaded into one of the GUIs.
     *
     * @param object the object containing changes that might be saved, needs to
     * implement NameInterface
     * @return false if the user pressed "Cancel" on the dialog, thus cancelling
     * any previous load action true otherwise
    *
     */
    public boolean openSaveChangesDialog(Object object) {
        int option = showSaveOptionPane(((NameInterface) object).getName());
        if (option == JOptionPane.YES_OPTION) {
            return saveObject(object, false);
        } else  {
            return (!(option == JOptionPane.CANCEL_OPTION));
        }
    }

    /**
     * Method that opens pane that asks the user whether he wants to save his
     * changes or not.
     *
     * @return the option clicked by the user
     */
    private int showSaveOptionPane(String propertyName) {
        Object[] options = {yesOption,
            noOption,
            cancelOption};
        return JOptionPane.showOptionDialog(null,
                saveChanges + propertyName + save,
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    /**
     * Updates the StringResourceLoader and the language dependent fields of the JFileChooser.
     * @param stringResourceLoader the new StringResourceLoader object
     */
    public void updateStringRessourceLoader(StringResourceLoader stringResourceLoader) {
        this.stringResourceLoader = stringResourceLoader;
        fileChooser = new JFileChooser();
        //sets the text and language of all the components in JFileChooser
        UIManager.put("FileChooser.openDialogTitleText",
                stringResourceLoader.getStringFromID("openDialogTitleText"));
        UIManager.put("FileChooser.lookInLabelText", stringResourceLoader.getStringFromID("lookInLabelText"));
        UIManager.put("FileChooser.openButtonText", stringResourceLoader.getStringFromID("openButtonText"));
        UIManager.put("FileChooser.cancelButtonText", stringResourceLoader.getStringFromID("cancelButtonText"));
        UIManager.put("FileChooser.fileNameLabelText", stringResourceLoader.getStringFromID("fileNameLabelText"));
        UIManager.put("FileChooser.filesOfTypeLabelText",
                stringResourceLoader.getStringFromID("filesOfTypeLabelText"));
        UIManager.put("FileChooser.approveButtonToolTipText", "");
        UIManager.put("FileChooser.cancelButtonToolTipText", "");
        UIManager.put("FileChooser.openButtonToolTipText",
                stringResourceLoader.getStringFromID("openApproveButtonToolTipText"));
        UIManager.put("FileChooser.fileNameHeaderText",
                stringResourceLoader.getStringFromID("fileNameHeaderText"));
        UIManager.put("FileChooser.upFolderToolTipText",
                stringResourceLoader.getStringFromID("upFolderToolTipText"));
        UIManager.put("FileChooser.homeFolderToolTipText",
                stringResourceLoader.getStringFromID("homeFolderToolTipText"));
        UIManager.put("FileChooser.newFolderToolTipText",
                stringResourceLoader.getStringFromID("newFolderToolTipText"));
        UIManager.put("FileChooser.listViewButtonToolTipText",
                stringResourceLoader.getStringFromID("listViewButtonToolTipText"));
        UIManager.put("FileChooser.newFolderButtonText",
                stringResourceLoader.getStringFromID("newFolderButtonText"));
        UIManager.put("FileChooser.renameFileButtonText",
                stringResourceLoader.getStringFromID("renameFileButtonText"));
        UIManager.put("FileChooser.deleteFileButtonText",
                stringResourceLoader.getStringFromID("deleteFileButtonText"));
        UIManager.put("FileChooser.filterLabelText", stringResourceLoader.getStringFromID("filterLabelText"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText",
                stringResourceLoader.getStringFromID("detailsViewButtonToolTipText"));
        UIManager.put("FileChooser.fileSizeHeaderText",
                stringResourceLoader.getStringFromID("fileSizeHeaderText"));
        UIManager.put("FileChooser.fileDateHeaderText",
                stringResourceLoader.getStringFromID("fileDateHeaderText"));
        saveChanges = stringResourceLoader.getStringFromID("saveChanges");
        save = stringResourceLoader.getStringFromID("saveChangesSuffix");
        cancelOption = stringResourceLoader.getStringFromID("cancelOption");
        noOption = stringResourceLoader.getStringFromID("noOption");
        yesOption = stringResourceLoader.getStringFromID("yesOption");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.getName().matches(stringResourceLoader.getStringFromID("fileApproveRegex"))
                        || file.isDirectory());
            }

            @Override
            public String getDescription() {
                return stringResourceLoader.getStringFromID("fileDescription");
            }
        });
        fileChooser.setSelectedFile(new File(SuperFolderFinder.getSuperFolder() + "/projectFiles/ "));
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    /**
     * Setter for the hasBeenSaved boolean.
     * @param bool the new value
     */
    public void setHasBeenSaved(boolean bool) {
        this.hasBeenSaved = bool;
    }
}
