package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.toolbox.Tuple;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class FileDialogHelper {
    private static final String ELECTION_DESCRIPTION_STRING = "Beast Election Description";
    private static final String PROPERTY_DESCRIPTION_STRING = "Property Description";
    private static final String ELECTION_FILE_ENDING = "belec";
    private static final String PROPERTY_FILE_ENDING = "bprp";
    private static final String CHOOSE_ELECTION_DIALOG_TITLE = "Choose Election Description";
    private static final String CHOOSE_PROPERTY_DIALOG_TITLE = "Choose Property Description";

    public static Tuple<CElectionDescription, File>
            letUserLoadElectionDescription(final File baseDir,
                                           final Stage primaryStage) throws IOException {
        final File f =
                letUserOpenFile(ELECTION_DESCRIPTION_STRING, ELECTION_FILE_ENDING,
                                CHOOSE_ELECTION_DIALOG_TITLE, baseDir,
                                primaryStage);
        if (f != null) {
            final CElectionDescription descr = SavingLoadingInterface.loadCElection(f);
            return new Tuple<CElectionDescription, File>(descr, f);
        }
        return new Tuple<CElectionDescription, File>(null, f);
    }

    public static Tuple<PreAndPostConditionsDescription, File>
            letUserLoadPropDescr(final File propDescrDir,
                                 final Stage primaryStage) throws IOException {
        final File f = letUserOpenFile(PROPERTY_DESCRIPTION_STRING, PROPERTY_FILE_ENDING,
                                       CHOOSE_PROPERTY_DIALOG_TITLE,
                                       propDescrDir, primaryStage);
        if (f != null) {
            final PreAndPostConditionsDescription propDescr =
                    SavingLoadingInterface.loadPreAndPostConditionDescription(f);
            return new Tuple<PreAndPostConditionsDescription, File>(propDescr, f);
        }
        return new Tuple<PreAndPostConditionsDescription, File>(null, f);
    }

    public static File letUserOpenFile(final String extDescr,
                                       final String ext,
                                       final String title,
                                       final File initDir,
                                       final Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(initDir);
        fileChooser.setSelectedExtensionFilter(new ExtensionFilter(extDescr, List.of(ext)));
        return fileChooser.showOpenDialog(stage);
    }

    public static File letUserSaveFile(final File initDir,
                                       final String title,
                                       final String name) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(initDir);
        fileChooser.setInitialFileName(name);
        return fileChooser.showSaveDialog(null);
    }
}
