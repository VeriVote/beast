package edu.pse.beast.api.fileio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.saverloader.PropertyDescriptionSaverLoader;
import edu.pse.beast.toolbox.Project;

public class FileIOInterface {

	private ElectionDescriptionSaverLoader electionDescriptionSaverLoader = new ElectionDescriptionSaverLoader();
	private PropertyDescriptionSaverLoader propSaverLoader = new PropertyDescriptionSaverLoader();
	private ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();

	public ElectionDescription loadElecDescrFromFile(File f) throws IOException {
		List<String> lines = Files.readAllLines(f.toPath(), Charset.defaultCharset());
		ElectionDescription created = electionDescriptionSaverLoader.createFromSaveString(String.join("\n", lines));
		return created;
	}

	public PreAndPostConditionsDescription loadPropDescrFromFile(File f) throws IOException {
		List<String> lines = Files.readAllLines(f.toPath(), Charset.defaultCharset());
		PreAndPostConditionsDescription created = propSaverLoader.createFromSaveString(String.join("\n", lines));
		return created;
	}

	public Project loadProjectFromFile(File f) throws Exception {
		List<String> lines = Files.readAllLines(f.toPath(), Charset.defaultCharset());
		Project created = projectSaverLoader.createFromSaveString(String.join("\n", lines));
		return created;
	}
}
