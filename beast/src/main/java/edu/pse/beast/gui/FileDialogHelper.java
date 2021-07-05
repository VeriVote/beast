package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.toolbox.Tuple;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileDialogHelper {

	public static Tuple<CElectionDescription, File> letUserLoadElectionDescription(
			File baseDir, Stage primaryStage) throws IOException {
		File f = letUserOpenFile("beast election description", "belec",
				"choose election description", baseDir, primaryStage);
		if (f != null) {
			CElectionDescription descr = SavingLoadingInterface
					.loadCElection(f);
			return new Tuple<CElectionDescription, File>(descr, f);
		}
		return new Tuple<CElectionDescription, File>(null, f);
	}

	public static Tuple<PreAndPostConditionsDescription, File> letUserLoadPropDescr(
			File propDescrDir, Stage primaryStage) throws IOException {
		File f = letUserOpenFile("property description", "bprp",
				"choose property description", propDescrDir, primaryStage);
		if (f != null) {
			PreAndPostConditionsDescription propDescr = SavingLoadingInterface
					.loadPreAndPostConditionDescription(f);
			return new Tuple<PreAndPostConditionsDescription, File>(propDescr, f);
		}
		return new Tuple<PreAndPostConditionsDescription, File>(null, f);
	}

	public static File letUserOpenFile(String extDescr, String ext,
			String title, File initDir, Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(initDir);
		fileChooser.setSelectedExtensionFilter(
				new ExtensionFilter(extDescr, List.of(ext)));
		return fileChooser.showOpenDialog(stage);
	}

	public static File letUserSaveFile(File initDir, String title,
			String name) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(initDir);
		fileChooser.setInitialFileName(name);
		return fileChooser.showSaveDialog(null);
	}

}
