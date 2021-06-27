package edu.pse.beast.gui.options.ceditor;

import java.io.IOException;

import edu.pse.beast.gui.ceditor.CElectionEditor;
import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsCategoryType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
	
	public CEditorOptionsGUI(CEditorOptions options) throws IOException {
		super(OptionsCategoryType.C_DESCR_EDITOR);
		this.options = options;
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}
	
	public CEditorOptions getOptions() {
		return options;
	}
	
	public void setcElectionEditor(CElectionEditor cElectionEditor) {
		this.cElectionEditor = cElectionEditor;
		cElectionEditor.applyOptions(options);
	}
	
	@FXML
	public void initialize() {
		fontSizeTextField.setText(String.valueOf(options.getFontSize()));
		fontSizeSlider.setMin(4.0);
		fontSizeSlider.setMax(50.0);
		fontSizeSlider.setValue(options.getFontSize());
		fontSizeSlider.setShowTickLabels(true);
		fontSizeSlider.valueProperty().addListener((ob, o, n) -> {
			double newVal = Math.round((double) n * 100) / 100;
			fontSizeTextField.setText(String.valueOf(newVal));
			fontSizeSlider.setValue(newVal);
			options.setFontSize(newVal);
			applyOptions();
		});
	}
	
	private void applyOptions() {		
		optionsGUIController.optionUpdated();	
		cElectionEditor.applyOptions(options);
	}

	@Override
	public void displayOptions(AnchorPane currentOptionDisplayAnchorpane) {
		displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);		
	}
	
}
