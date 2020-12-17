package edu.pse.beast.api;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.pse.beast.api.fileio.FileIOInterface;
import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.saverloader.MinimalSaverInterface;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.toolbox.Project;

class ElectionTest {
	
	FileIOInterface fileIOInterface = new FileIOInterface();

    private SaverLoader projectSaverLoader = new SaverLoader(
            SaverLoader.PROJECT_FILE_ENDING, "BEAST project file",
            new MinimalSaverInterface() {
                @Override
                public void saveAs() {
                    GUIController.getController().saveAsProject(null);
                }

                @Override
                public void save() {
                    GUIController.getController().saveProject(null);
                }
            });
    
	@Test
	void testBlack() throws Exception {		
		File elecFile = new File("./projectFiles/Black/description.elec");
		ElectionDescription descr = fileIOInterface.loadFromFile(elecFile);
		
	}

}
