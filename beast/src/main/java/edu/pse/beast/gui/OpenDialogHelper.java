package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class OpenDialogHelper {

	public static CElectionDescription letUserLoadElectionDescription(
			File baseDir, Stage primaryStage)
			throws NotImplementedException, IOException {
		File f = letUserOpenFile("beast election description", "belec",
				"choose election description", baseDir, primaryStage);
		if (f != null) {
			CElectionDescription descr = SavingLoadingInterface
					.loadCElection(f);
			return descr;
		}
		return null;
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
}
