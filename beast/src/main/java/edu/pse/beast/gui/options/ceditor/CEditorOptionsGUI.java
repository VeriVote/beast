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

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CEditorOptionsGUI extends OptionsCategoryGUI {
    private static final int ONE_HUNDRED = 100;

    private static final double MIN_SLIDE = 4.0;
    private static final double MAX_SLIDE = 50.0;

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

    public final CEditorOptions getOptions() {
        return options;
    }

    public final void setcElectionEditor(final CElectionEditor editor) {
        this.cElectionEditor = editor;
        editor.applyOptions(options);
    }

    @FXML
    public final void initialize() {
        fontSizeTextField.setText(String.valueOf(options.getFontSize()));
        fontSizeSlider.setMin(MIN_SLIDE);
        fontSizeSlider.setMax(MAX_SLIDE);
        fontSizeSlider.setValue(options.getFontSize());
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.valueProperty().addListener((ob, o, n) -> {
            final double newVal = Math.round((double) n * ONE_HUNDRED) / ONE_HUNDRED;
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
    public final void displayOptions(final AnchorPane currentOptionDisplayAnchorpane) {
        displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);
    }

}
