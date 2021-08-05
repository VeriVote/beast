package edu.pse.beast.gui.errors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ErrorDialogHelper {
    public static void showErrorDialog(final ErrorMessage msg) {
        final Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(msg.getTitle());
        errorAlert.setContentText(msg.getMsg());
        errorAlert.showAndWait();
    }
}
