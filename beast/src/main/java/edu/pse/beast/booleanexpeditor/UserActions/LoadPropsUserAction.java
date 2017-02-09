package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import edu.pse.beast.saverloader.SaverLoader;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.FileChooser;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * @author NikolaiLMS
 */
public class LoadPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;

    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        if (booleanExpEditor.getSaveBeforeChangeHandler().
                ifHasChangedOpenSaveDialog(booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName())) {
            PostAndPrePropertiesDescription loadedPostAndPrePropertiesDescription =
                    (PostAndPrePropertiesDescription) booleanExpEditor.getFileChooser().loadObject();
            if (loadedPostAndPrePropertiesDescription != null) {
                booleanExpEditor.loadNewProperties(loadedPostAndPrePropertiesDescription);
                booleanExpEditor.getFileChooser().setHasBeenSaved(true);
            }
        }
    }
}
