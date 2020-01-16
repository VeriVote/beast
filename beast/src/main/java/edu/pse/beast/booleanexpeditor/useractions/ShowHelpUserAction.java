package edu.pse.beast.booleanexpeditor.useractions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.UserAction;

/**
 * The Class ShowHelpUserAction.
 *
 * @author Holger Klein
 */
public class ShowHelpUserAction extends UserAction implements DisplaysStringsToUser {

    /** The path to help file. */
    private String pathToHelpFile;

    /**
     * Instantiates a new show help user action.
     */
    public ShowHelpUserAction() {
        super("showHelpToUser");
    }

    @Override
    public void perform() {
        File f = new File(SuperFolderFinder.getSuperFolder() + "/" + pathToHelpFile);
        try {
            Desktop.getDesktop().browse(f.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStringRes(final StringLoaderInterface stringResIF) {
        this.pathToHelpFile =
                stringResIF.getBooleanExpEditorStringResProvider().getMenuStringRes()
                .getStringFromID("pathToLangHelp");
    }
}
