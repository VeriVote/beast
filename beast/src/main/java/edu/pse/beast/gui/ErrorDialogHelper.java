package edu.pse.beast.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorDialogHelper {
	public static void showErrorDialog(String title, String msg) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(title);
		errorAlert.setContentText(msg);
		errorAlert.showAndWait();
	}
}
