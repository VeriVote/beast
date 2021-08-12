package edu.pse.beast.gui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class DialogHelper {
    private static final int LEFT_COLUMN = 0;
    private static final int RIGHT_COLUMN = 1;
    private static final int DEFAULT_DIALOG_SIZE = 10;
    private static final String OK = "OK";

    public static Dialog<ButtonType> generateDialog(final String title,
                                                    final BooleanBinding binding,
                                                    final List<String> inputNames,
                                                    final List<Node> inputs) {
        final Point position = MouseInfo.getPointerInfo().getLocation();
        final Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle(title);
        dialog.setX(position.getX());
        dialog.setY(position.getY());
        final ButtonType buttonType = new ButtonType(OK, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(buttonType).disableProperty().bind(binding);

        final GridPane grid = new GridPane();
        grid.setHgap(DEFAULT_DIALOG_SIZE);
        grid.setVgap(DEFAULT_DIALOG_SIZE);
        int i = 0;
        for (; i < inputNames.size(); ++i) {
            grid.add(new Label(inputNames.get(i)), LEFT_COLUMN, i);
            grid.add(inputs.get(i), RIGHT_COLUMN, i);
        }
        for (; i < inputs.size(); ++i) {
            grid.add(inputs.get(i), LEFT_COLUMN, i);
        }
        dialog.getDialogPane().setContent(grid);
        return dialog;
    }
}
