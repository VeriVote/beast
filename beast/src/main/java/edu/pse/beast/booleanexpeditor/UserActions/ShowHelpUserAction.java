package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.UserAction;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by holger on 14.03.17.
 */
public class ShowHelpUserAction extends UserAction implements DisplaysStringsToUser {
    private String pathToHelpFile;

    /**
     * @param id the identification
     */
    public ShowHelpUserAction() {
        super("showHelpToUser");
    }

    @Override
    public void perform() {
        File f = new File(SuperFolderFinder.getSuperFolder() + "./" + pathToHelpFile);
        try {
            Desktop.getDesktop().browse(f.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        this.pathToHelpFile = stringResIF.getBooleanExpEditorStringResProvider().
                getMenuStringRes().getStringFromID("pathToLangHelp");
    }
}
