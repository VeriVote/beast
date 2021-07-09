package edu.pse.beast.gui.errors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorDialogHelper {
    public static void showErrorDialog(ErrorMessage msg) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(msg.getTitle());
        errorAlert.setContentText(msg.getMsg());
        errorAlert.showAndWait();
    }
}
