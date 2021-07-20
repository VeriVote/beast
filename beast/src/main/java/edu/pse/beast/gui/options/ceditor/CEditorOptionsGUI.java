package edu.pse.beast.gui.options.ceditor;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.gui.ceditor.CElectionEditor;
import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsCategoryType;

public class CEditorOptionsGUI extends OptionsCategoryGUI {
    @FXML
    private AnchorPane topLevelAnchorpane;
    @FXML
    private TextField fontSizeTextField;
    @FXML
    private Slider fontSizeSlider;

    private String fxml = "/edu/pse/beast/cEditorOptions.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource(fxml));

    private CEditorOptions options;

    private CElectionEditor cElectionEditor;

    public CEditorOptionsGUI(final CEditorOptions opts) throws IOException {
        super(OptionsCategoryType.C_DESCR_EDITOR);
        this.options = opts;
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public CEditorOptions getOptions() {
        return options;
    }

    public void setcElectionEditor(final CElectionEditor editor) {
        this.cElectionEditor = editor;
        editor.applyOptions(options);
    }

    @FXML
    public void initialize() {
        fontSizeTextField.setText(String.valueOf(options.getFontSize()));
        fontSizeSlider.setMin(4.0);
        fontSizeSlider.setMax(50.0);
        fontSizeSlider.setValue(options.getFontSize());
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.valueProperty().addListener((ob, o, n) -> {
            final double newVal = Math.round((double) n * 100) / 100;
            fontSizeTextField.setText(String.valueOf(newVal));
            fontSizeSlider.setValue(newVal);
            options.setFontSize(newVal);
            applyOptions();
        });
    }

    private void applyOptions() {
        getOptionsGUIController().saveOptions();
        cElectionEditor.applyOptions(options);
    }

    @Override
    public void displayOptions(final AnchorPane currentOptionDisplayAnchorpane) {
        displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);
    }

}
