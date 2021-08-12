package edu.pse.beast.api.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.gui.workspace.BeastWorkspace;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class InputOutputInterface {

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
        CElectionInputOutput.storeCElection(descr, f);
    }

    public static CElectionDescription loadCElection(final File f)
            throws NotImplementedException, IOException {
        return CElectionInputOutput.loadCElection(f);
    }

    public static void storePropertyDescription(final PropertyDescription propDescr, final File f)
                    throws IOException {
        PropertyInputOutput.storePropertyDescription(propDescr, f);
    }

    public static PropertyDescription loadPropertyDescription(final File f)
            throws JSONException, IOException {
        return PropertyInputOutput.loadPropertyDescription(f);
    }

    public static void storeBeastWorkspace(final BeastWorkspace beastWorkspace,
                                           final File f,
                                           final RelativePathConverter relativePathConverter)
            throws IOException {
        WorkspaceInputOutput.storeWorkspace(beastWorkspace, f, relativePathConverter);
    }

    public static BeastWorkspace
            loadBeastWorkspace(final File f,
                               final RelativePathConverter relativePathConverter)
                                       throws IOException {
        return WorkspaceInputOutput.loadBeastWorkspaceFromFile(f, relativePathConverter);
    }
}
