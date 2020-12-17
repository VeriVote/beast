package edu.pse.beast.api.fileio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;

public class FileIOInterface {

	private ElectionDescriptionSaverLoader electionDescriptionSaverLoader = new ElectionDescriptionSaverLoader();

	public ElectionDescription loadFromFile(File f) throws IOException {
		List<String> lines = Files.readAllLines(f.toPath(), Charset.defaultCharset());
		ElectionDescription created = electionDescriptionSaverLoader.createFromSaveString(String.join("\n", lines));
		return created;
	}

}
