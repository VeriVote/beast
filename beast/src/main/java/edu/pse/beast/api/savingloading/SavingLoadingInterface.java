package edu.pse.beast.api.savingloading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class SavingLoadingInterface {

	public static String readStringFromFile(File f) throws IOException {
		return Files.readString(f.toPath());
	}

	public static void writeStringToFile(File f, String s) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		writer.write(s);
		writer.close();
	}

	public static void storeCElection(CElectionDescription descr, File f)
			throws IOException {
		CElectionSaverLoader.storeCElection(descr, f);
	}

	public static CElectionDescription loadCElection(File f)
			throws NotImplementedException, IOException {
		return CElectionSaverLoader.loadCElection(f);
	}

	public static void storePreAndPostConditionDescription(
			PreAndPostConditionsDescription propDescr, File f)
			throws IOException {
		PreAndPostPropertySaverLoader
				.storePreAndPostConditionDescription(propDescr, f);
	}

	public static PreAndPostConditionsDescription loadPreAndPostConditionDescription(
			File f) throws JSONException, IOException {
		return PreAndPostPropertySaverLoader
				.loadPreAndPostConditionDescription(f);
	}
}
