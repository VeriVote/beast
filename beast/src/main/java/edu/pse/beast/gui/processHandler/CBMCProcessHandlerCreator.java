package edu.pse.beast.gui.processHandler;

import java.io.File;
import java.util.Optional;

import edu.pse.beast.api.os.OS;
import edu.pse.beast.api.os.OSHelper;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerLinux;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerSource;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerWindows;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

public class CBMCProcessHandlerCreator implements CBMCProcessHandlerSource {

	private String vsDevCmdExplanationString = "It seems like you are using windows. "
			+ "To run cbmc on windows, a file called vsDevCmd.bat is needed. "
			+ "this file is usually bundled with visual studio, usually in the relative path "
			+ "Visual studio\\Common7\\Tools\\VsDevCmd.bat. "
			+ "Please navigate to and choose this file by clicking ok."
			+ "Otherwise, you can still create test runs but not start them";

	private CBMCProcessHandler processHandler;
	private String vsDevCmdPath;

	private File getVsDevCmdFromUser() {
		Alert needVsDevCmdAlert = new Alert(AlertType.INFORMATION);
		needVsDevCmdAlert.setContentText(vsDevCmdExplanationString);
		needVsDevCmdAlert.getButtonTypes().add(ButtonType.CANCEL);

		Optional<ButtonType> result = needVsDevCmdAlert.showAndWait();

		if (result.isPresent()
				&& !result.get().getButtonData().isCancelButton()) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("please navigate to vsDevCmd file");
			if (vsDevCmdPath != null)
				fileChooser.setInitialDirectory(new File(vsDevCmdPath).getParentFile());
			File chosenFile = fileChooser.showOpenDialog(null);
			if (chosenFile == null)
				return null;
			return chosenFile;
		}

		return null;
	}

	public boolean testIsVsDevCmd(File vsDevCmd) {
		if(vsDevCmd == null) return false;
		String name = vsDevCmd.getName();
		if(!name.toLowerCase().equals("vsdevcmd.bat")) return false;
		return true;
	}

	public void askUserForCBMCProcessHandler() {
		OS osType = OSHelper.getOS();

		switch (osType) {
		case LINUX:
			processHandler = new CBMCProcessHandlerLinux();
		case WINDOWS:
			File vsDevCmd = getVsDevCmdFromUser();
			if (testIsVsDevCmd(vsDevCmd)) {
				CBMCProcessHandlerWindows processHandlerWindows = new CBMCProcessHandlerWindows(
						vsDevCmd.getAbsolutePath());
				vsDevCmdPath = vsDevCmd.getAbsolutePath();
			}
			break;
		case UNKNOWN:
			break;
		}
	}

	public CBMCProcessHandler getProcessHandler() {
		return processHandler;
	}

	public boolean hasProcessHandler() {
		return processHandler != null;
	}

	public String getVsDevCmdPath() {
		return vsDevCmdPath;
	}
	
	public void setVsDevCmdPath(String vsDevCmdPath) {
		this.vsDevCmdPath = vsDevCmdPath;
	}
}
