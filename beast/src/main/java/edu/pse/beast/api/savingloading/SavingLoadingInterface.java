package edu.pse.beast.api.savingloading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.gui.workspace.BeastWorkspace;

public class SavingLoadingInterface {

    public static String readStringFromFile(final File f) throws IOException {
        return Files.readString(f.toPath());
    }

    public static void writeStringToFile(final File f, final String s) throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write(s);
        writer.close();
    }

    public static void storeCElection(final CElectionDescription descr, final File f)
            throws IOException {
        CElectionSaverLoader.storeCElection(descr, f);
    }

    public static CElectionDescription loadCElection(final File f)
            throws NotImplementedException, IOException {
        return CElectionSaverLoader.loadCElection(f);
    }

    public static void
            storePreAndPostConditionDescription(final PreAndPostConditionsDescription propDescr,
                                                final File f) throws IOException {
        PreAndPostPropertySaverLoader.storePreAndPostConditionDescription(propDescr, f);
    }

    public static PreAndPostConditionsDescription loadPreAndPostConditionDescription(final File f)
            throws JSONException, IOException {
        return PreAndPostPropertySaverLoader.loadPreAndPostConditionDescription(f);
    }

    public static void storeBeastWorkspace(final BeastWorkspace beastWorkspace,
                                           final File f,
                                           final RelativePathConverter relativePathConverter)
            throws IOException {
        WorkspaceSaverLoader.storeWorkspace(beastWorkspace, f, relativePathConverter);
    }

    public static BeastWorkspace
            loadBeastWorkspace(final File f,
                               final RelativePathConverter relativePathConverter)
                                       throws IOException {
        return WorkspaceSaverLoader.loadBeastWorkspaceFromFile(f, relativePathConverter);
    }
}
