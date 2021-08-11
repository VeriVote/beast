package edu.pse.beast.gui.options;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import edu.pse.beast.api.io.options.OptionsInputOutput;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class OptionsGUIController {
    @FXML
    private AnchorPane topLevelAnchorpane;
    @FXML
    private ListView<OptionsCategoryGUI> optionsCategoryListview;
    @FXML
    private AnchorPane currentOptionDisplayAnchorpane;

    private Stage optionStage;

    private List<OptionsCategoryGUI> categories;

    private File optionsSaveFile;

    public OptionsGUIController(final List<OptionsCategoryGUI> categoryList,
                                final File optSaveFile) {
        this.categories = categoryList;
        this.optionsSaveFile = optSaveFile;
        for (final OptionsCategoryGUI cat : categoryList) {
            cat.setOptionsGUIController(this);
        }
    }

    public final List<OptionsCategoryGUI> getCategories() {
        return categories;
    }

    @FXML
    public final void initialize() {
        for (final OptionsCategoryGUI cat : categories) {
            optionsCategoryListview.getItems().add(cat);
        }
        optionsCategoryListview.getSelectionModel().selectedItemProperty()
                .addListener((e, o, n) -> {
                    displayCategory(n);
                });
        optionStage = new Stage();
        optionStage.setScene(new Scene(topLevelAnchorpane));
    }

    private void displayCategory(final OptionsCategoryGUI category) {
        currentOptionDisplayAnchorpane.getChildren().clear();
        category.displayOptions(currentOptionDisplayAnchorpane);
    }

    public final void display() {
        optionStage.showAndWait();
    }

    public final void saveOptions() {
        try {
            OptionsInputOutput.saveOptions(optionsSaveFile, categories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
