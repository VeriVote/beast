package edu.pse.beast.gui.errors;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ErrorGuiController {
    @FXML
    private AnchorPane topLevelAnchorPane;
    @FXML
    private ListView<BeastError> errorListView;
    @FXML
    private TextArea moreTextArea;
    @FXML
    private TextArea esceptionTextArea;

    private String fxml = "/edu/pse/beast/errorGUI.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource(fxml));

    public ErrorGuiController(final AnchorPane logAnchorPane,
                              final ErrorHandler errorhandler) throws IOException {
        errorhandler.setListener(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        logAnchorPane.getChildren().add(topLevelAnchorPane);
        AnchorPane.setTopAnchor(topLevelAnchorPane, 0.0d);
        AnchorPane.setLeftAnchor(topLevelAnchorPane, 0.0d);
        AnchorPane.setRightAnchor(topLevelAnchorPane, 0.0d);
        AnchorPane.setBottomAnchor(topLevelAnchorPane, 0.0d);

        errorListView.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    moreTextArea.setText(n.getErrorMessage().getMsg());
                    esceptionTextArea.clear();
                    if (n.getException() != null) {
                        for (int i = 0;
                                i < n.getException().getStackTrace().length; ++i) {
                            esceptionTextArea.appendText(
                                    n.getException().getStackTrace()[i].toString());
                        }
                    }
                });

    }

    public final void handleAddedError(final BeastError beastError) {
        errorListView.getItems().add(beastError);
    }
}
