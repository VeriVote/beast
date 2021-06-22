package edu.pse.beast.gui;

import java.io.File;
import java.util.Optional;

import edu.pse.beast.api.os.OS;
import edu.pse.beast.api.os.OSHelper;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerLinux;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerWindows;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class CBMCProcessHandlerGetter {

	private static String vsDevCmdExplanationString = "It seems like you are using windows. "
			+ "To run cbmc on windows, a file called vsDevCmd.bat is needed. "
			+ "this file is usually bundled with visual studio, usually in the relative path "
			+ "Visual studio\\Common7\\Tools\\VsDevCmd.bat. "
			+ "Please navigate to and choose this file by clicking ok."
			+ "Otherwise, you can still create test runs but not start them";

	private static File getVsDevCmdFromUser() {
		Alert needVsDevCmdAlert = new Alert(AlertType.INFORMATION);
		needVsDevCmdAlert.setContentText(vsDevCmdExplanationString);
		needVsDevCmdAlert.getButtonTypes().add(ButtonType.CANCEL);

		Optional<ButtonType> result = needVsDevCmdAlert.showAndWait();

		if (result.isPresent()
				&& !result.get().getButtonData().isCancelButton()) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("please navigate to vsDevCmd file");
			File chosenFile = fileChooser.showOpenDialog(null);
			if (chosenFile == null)
				return null;
			String fileName = chosenFile.getName();
			return chosenFile;
		}

		return null;
	}

	public static CBMCProcessHandler askUserForCBMCProcessHandler() {
		OS osType = OSHelper.getOS();

		switch (osType) {
			case LINUX :
				return new CBMCProcessHandlerLinux();
			case WINDOWS :
				File vsDevCmd = getVsDevCmdFromUser();
				if (vsDevCmd != null) {
					CBMCProcessHandlerWindows processHandlerWindows = new CBMCProcessHandlerWindows(
							vsDevCmd.getAbsolutePath());
					return processHandlerWindows;
				}
				break;
			case UNKNOWN :
				break;
		}

		return null;
	}
}
