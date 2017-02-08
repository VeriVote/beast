package edu.pse.beast.toolbox;

import edu.pse.beast.saverloader.SaverLoader;
import edu.pse.beast.stringresource.StringResourceLoader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author NikolaiLMS
 */
public class FileChooser {
    private JFileChooser fileChooser;
    private StringResourceLoader stringResourceLoader;
    private Component component;
    private SaverLoader saverLoader;
    private String lastLoadedPath;
    boolean hasBeenSaved = false;
    String fileSuffix;
    Object[] options = {"OK"};

    public FileChooser (StringResourceLoader stringResourceLoader, SaverLoader saverLoader, Component component) {
        this.fileChooser = new JFileChooser();
        this.stringResourceLoader = stringResourceLoader;
        this.component = component;
        this.saverLoader = saverLoader;
        updateStringRessourceLoader(stringResourceLoader);
    }

    public boolean saveObject(Object object, boolean forceDialog) {
        fileChooser.setApproveButtonText("saveApproveButtonText");
        if (hasBeenSaved && !forceDialog) {
            return saveToFile(object, lastLoadedPath);
        } else {
            if (fileChooser.showDialog(component, stringResourceLoader.getStringFromID("saveDialogTitleText")) == JFileChooser.APPROVE_OPTION) {
                if (!saveToFile(object, fileChooser.getSelectedFile().getAbsolutePath())) {
                    return saveObject(object, forceDialog);
                } else {
                    lastLoadedPath = fileChooser.getSelectedFile().getAbsolutePath();
                    hasBeenSaved = true;
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public Object showOpenDialog() {
        fileChooser.setApproveButtonText("openApproveButtonText");
        if (fileChooser.showDialog(component, stringResourceLoader.getStringFromID("openDialogTitleText")) == JFileChooser.APPROVE_OPTION) {
            File file  = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            String content = "";
            try {
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID( "inputOutputErrorOpen"),"",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                return (showOpenDialog());
            }
            try {
                Object outputObject = saverLoader.createFromSaveString(content);
                lastLoadedPath = path;
                hasBeenSaved = true;
                return outputObject;
            } catch (Exception e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "invalidFileFormatErrorMessage"),"",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                return (showOpenDialog());
            }
        } else {
            return null;
        }
    }

    private boolean saveToFile(Object object, String path) {
        if (!fileChooser.getSelectedFile().getName().matches("[_a-zA-Z0-9\\-\\.]+")) {
            JOptionPane.showOptionDialog(null,
                    stringResourceLoader.getStringFromID("wrongFileNameError"),"",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (!fileChooser.getSelectedFile().getName().endsWith(fileSuffix)) {
            path += stringResourceLoader.getStringFromID(
                    "fileSuffix");
        }

        String saveString = "";
        try {
            saveString = saverLoader.createSaveString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Writer out = null;
        try {
            File file = new File(path);
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showOptionDialog(null,
                    stringResourceLoader.getStringFromID(
                            "wrongEncodingError"),"",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } catch (FileNotFoundException e) {
            JOptionPane.showOptionDialog(null,
                    stringResourceLoader.getStringFromID(
                            "inputOutputErrorSave"),"",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        }

        try {
            try {
                out.write(saveString);
            } catch (IOException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "objectCouldNotBeSavedError"),"",
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
                lastLoadedPath = path;
                return true;
            } catch (IOException e) {
                JOptionPane.showOptionDialog(null,
                        stringResourceLoader.getStringFromID(
                                "objectCouldNotBeSavedError"),"",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                return false;
            }
        }
    }

    public void updateStringRessourceLoader(StringResourceLoader stringResourceLoader) {
        //sets the text and language of all the components in JFileChooser
        UIManager.put("FileChooser.openDialogTitleText", stringResourceLoader.getStringFromID("openDialogTitleText"));
        UIManager.put("FileChooser.lookInLabelText", stringResourceLoader.getStringFromID("lookInLabelText"));
        UIManager.put("FileChooser.openButtonText", stringResourceLoader.getStringFromID("openButtonText"));
        UIManager.put("FileChooser.cancelButtonText", stringResourceLoader.getStringFromID("cancelButtonText"));
        UIManager.put("FileChooser.fileNameLabelText", stringResourceLoader.getStringFromID("fileNameLabelText"));
        UIManager.put("FileChooser.filesOfTypeLabelText", stringResourceLoader.getStringFromID("filesOfTypeLabelText"));
        UIManager.put("FileChooser.approveButtonToolTipText", stringResourceLoader.getStringFromID(""));
        UIManager.put("FileChooser.cancelButtonToolTipText", stringResourceLoader.getStringFromID(""));
        UIManager.put("FileChooser.openButtonToolTipText", stringResourceLoader.getStringFromID("openButtonToolTipText"));
        UIManager.put("FileChooser.cancelButtonToolTipText",stringResourceLoader.getStringFromID("cancelButtonToolTipText"));
        UIManager.put("FileChooser.fileNameHeaderText",stringResourceLoader.getStringFromID("fileNameHeaderText"));
        UIManager.put("FileChooser.upFolderToolTipText", stringResourceLoader.getStringFromID("upFolderToolTipText"));
        UIManager.put("FileChooser.homeFolderToolTipText",stringResourceLoader.getStringFromID("homeFolderToolTipText"));
        UIManager.put("FileChooser.newFolderToolTipText",stringResourceLoader.getStringFromID("newFolderToolTipText"));
        UIManager.put("FileChooser.listViewButtonToolTipText",stringResourceLoader.getStringFromID("listViewButtonToolTipText"));
        UIManager.put("FileChooser.newFolderButtonText",stringResourceLoader.getStringFromID("newFolderButtonText"));
        UIManager.put("FileChooser.renameFileButtonText", stringResourceLoader.getStringFromID("renameFileButtonText"));
        UIManager.put("FileChooser.deleteFileButtonText", stringResourceLoader.getStringFromID("deleteFileButtonText"));
        UIManager.put("FileChooser.filterLabelText", stringResourceLoader.getStringFromID("filterLabelText"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", stringResourceLoader.getStringFromID("detailsViewButtonToolTipText"));
        UIManager.put("FileChooser.fileSizeHeaderText",stringResourceLoader.getStringFromID("fileSizeHeaderText"));
        UIManager.put("FileChooser.fileDateHeaderText", stringResourceLoader.getStringFromID("fileDateHeaderText"));
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
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileSuffix = stringResourceLoader.getStringFromID("fileSuffix");
    }

    public void setHasBeenSaved(boolean bool) {
        this.hasBeenSaved = bool;
    }
}
